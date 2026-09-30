import java.util.*;

public class Stage1RegressionTest {
  private static int failures = 0;

  private static void check(boolean condition, String message) {
    if (condition) {
      System.out.println("PASS: " + message);
    } else {
      failures++;
      System.out.println("FAIL: " + message);
    }
  }

  private static Client findClient(Warehouse warehouse, String clientID) {
    Iterator<Client> iterator = warehouse.getClients();
    while (iterator.hasNext()) {
      Client client = iterator.next();
      if (client.equals(clientID)) {
        return client;
      }
    }
    return null;
  }

  private static Product findProduct(Warehouse warehouse, String productID) {
    Iterator<Product> iterator = warehouse.getProducts();
    while (iterator.hasNext()) {
      Product product = iterator.next();
      if (product.equals(productID)) {
        return product;
      }
    }
    return null;
  }

  private static Map<String, Integer> wishlistMap(Warehouse warehouse, String clientID) {
    Map<String, Integer> result = new LinkedHashMap<String, Integer>();
    Iterator<WishlistItem> items = warehouse.getWishlist(clientID);
    if (items == null) {
      return null;
    }
    while (items.hasNext()) {
      WishlistItem item = items.next();
      result.put(item.getProduct().getProductID(), Integer.valueOf(item.getQuantity()));
    }
    return result;
  }

  private static void checkWishlist(Warehouse warehouse, String clientID,
      String[] productIDs, int[] quantities, String label) {
    Map<String, Integer> items = wishlistMap(warehouse, clientID);
    check(items != null, label + " wishlist exists");
    if (items == null) {
      return;
    }
    check(items.size() == productIDs.length, label + " wishlist size is " + productIDs.length
        + " (actual " + items.size() + ")");
    for (int i = 0; i < productIDs.length; i++) {
      Integer quantity = items.get(productIDs[i]);
      check(quantity != null && quantity.intValue() == quantities[i],
          label + " has " + productIDs[i] + " -> " + quantities[i]
              + " (actual " + quantity + ")");
    }
  }

  public static void main(String[] args) {
    Warehouse warehouse = Warehouse.instance();

    Client c1 = warehouse.addClient("Client One", "1 First St");
    Client c2 = warehouse.addClient("Client Two", "2 Second St");
    Client c3 = warehouse.addClient("Client Three", "3 Third St");
    Client c4 = warehouse.addClient("Client Four", "4 Fourth St");
    Client c5 = warehouse.addClient("Client Five", "5 Fifth St");

    check(c1 != null && "C1".equals(c1.getClientID()), "first client ID is C1");
    check(c2 != null && "C2".equals(c2.getClientID()), "second client ID is C2");
    check(c3 != null && "C3".equals(c3.getClientID()), "third client ID is C3");
    check(c4 != null && "C4".equals(c4.getClientID()), "fourth client ID is C4");
    check(c5 != null && "C5".equals(c5.getClientID()), "fifth client ID is C5");

    String[] clientIDs = {"C1", "C2", "C3", "C4", "C5"};
    for (int i = 0; i < clientIDs.length; i++) {
      Client client = findClient(warehouse, clientIDs[i]);
      check(client != null, clientIDs[i] + " is stored");
      check(client != null && client.getAccountBalance() == 0.00,
          clientIDs[i] + " balance is 0.00");
    }

    Product p1 = warehouse.addProduct("Product 1", 10, 1.00);
    Product p2 = warehouse.addProduct("Product 2", 20, 2.00);
    Product p3 = warehouse.addProduct("Product 3", 30, 3.00);
    Product p4 = warehouse.addProduct("Product 4", 40, 4.00);
    Product p5 = warehouse.addProduct("Product 5", 50, 5.00);

    check(p1 != null && "P1".equals(p1.getProductID()), "first product ID is P1");
    check(p2 != null && "P2".equals(p2.getProductID()), "second product ID is P2");
    check(p3 != null && "P3".equals(p3.getProductID()), "third product ID is P3");
    check(p4 != null && "P4".equals(p4.getProductID()), "fourth product ID is P4");
    check(p5 != null && "P5".equals(p5.getProductID()), "fifth product ID is P5");

    check(p1 != null && p1.getAmountInStock() == 10 && p1.getSalePrice() == 1.00,
        "P1 stock 10 price $1");
    check(p2 != null && p2.getAmountInStock() == 20 && p2.getSalePrice() == 2.00,
        "P2 stock 20 price $2");
    check(p3 != null && p3.getAmountInStock() == 30 && p3.getSalePrice() == 3.00,
        "P3 stock 30 price $3");
    check(p4 != null && p4.getAmountInStock() == 40 && p4.getSalePrice() == 4.00,
        "P4 stock 40 price $4");
    check(p5 != null && p5.getAmountInStock() == 50 && p5.getSalePrice() == 5.00,
        "P5 stock 50 price $5");

    check(warehouse.addToWishlist("C1", "P1", 5), "add P1 qty 5 to C1");
    check(warehouse.addToWishlist("C1", "P3", 5), "add P3 qty 5 to C1");
    check(warehouse.addToWishlist("C1", "P5", 5), "add P5 qty 5 to C1");
    checkWishlist(warehouse, "C1", new String[] {"P1", "P3", "P5"},
        new int[] {5, 5, 5}, "C1");

    check(warehouse.addToWishlist("C2", "P1", 7), "add P1 qty 7 to C2");
    check(warehouse.addToWishlist("C2", "P2", 7), "add P2 qty 7 to C2");
    check(warehouse.addToWishlist("C2", "P4", 7), "add P4 qty 7 to C2");
    checkWishlist(warehouse, "C2", new String[] {"P1", "P2", "P4"},
        new int[] {7, 7, 7}, "C2 after first add");

    check(warehouse.addToWishlist("C3", "P1", 6), "add P1 qty 6 to C3");
    check(warehouse.addToWishlist("C3", "P2", 6), "add P2 qty 6 to C3");
    check(warehouse.addToWishlist("C3", "P5", 6), "add P5 qty 6 to C3");
    checkWishlist(warehouse, "C3", new String[] {"P1", "P2", "P5"},
        new int[] {6, 6, 6}, "C3");

    check(warehouse.addToWishlist("C2", "P3", 7), "add P3 qty 7 to C2");
    check(warehouse.addToWishlist("C2", "P5", 7), "add P5 qty 7 to C2");
    checkWishlist(warehouse, "C2", new String[] {"P1", "P2", "P4", "P3", "P5"},
        new int[] {7, 7, 7, 7, 7}, "C2 after second add");
    checkWishlist(warehouse, "C3", new String[] {"P1", "P2", "P5"},
        new int[] {6, 6, 6}, "C3 unchanged after C2 update");

    check(warehouse.searchClient("C999") == null, "invalid client C999 is not found");
    check(!warehouse.addToWishlist("C999", "P1", 1), "wishlist for C999 fails");
    check(warehouse.getWishlist("C999") == null, "wishlist query for C999 fails");
    check(!warehouse.addToWishlist("C1", "P999", 1), "add P999 to C1 fails");

    check(warehouse.addToWishlist("C4", "P1", 5), "add P1 qty 5 to C4");
    check(warehouse.addToWishlist("C4", "P1", 8), "replace C4 P1 quantity with 8");
    Map<String, Integer> c4Wishlist = wishlistMap(warehouse, "C4");
    check(c4Wishlist != null && c4Wishlist.size() == 1, "C4 wishlist has one P1 entry");
    check(c4Wishlist != null && c4Wishlist.get("P1") != null
        && c4Wishlist.get("P1").intValue() == 8,
        "C4 P1 quantity is 8, not 13");

    System.out.println();
    if (failures == 0) {
      System.out.println("ALL STAGE 1 REGRESSION TESTS PASSED");
    } else {
      System.out.println(failures + " STAGE 1 REGRESSION TESTS FAILED");
    }
  }
}
