import java.util.*;

public class Client {
  private static int nextID = 1;
  private String name;
  private String address;
  private String clientID;
  private double accountBalance;
  private List<WishlistItem> wishlist;

  public Client(String name, String address) {
    this.name = name;
    this.address = address;
    this.clientID = "C" + nextID;
    nextID++;
    this.accountBalance = 0.0;
    this.wishlist = new LinkedList<WishlistItem>();
  }

  public String getClientID() {
    return clientID;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public double getAccountBalance() {
    return accountBalance;
  }

  public boolean equals(String clientID) {
    return this.clientID.equals(clientID);
  }

  public void addOrUpdateWishlist(Product product, int quantity) {
    Iterator<WishlistItem> iterator = wishlist.iterator();
    while (iterator.hasNext()) {
      WishlistItem item = iterator.next();
      if (item.getProduct().equals(product.getProductID())) {
        item.setQuantity(quantity);
        return;
      }
    }
    wishlist.add(new WishlistItem(product, quantity));
  }

  public Iterator<WishlistItem> getWishlistItems() {
    return wishlist.iterator();
  }

  public String toString() {
    return "Client ID: " + clientID + " | Name: " + name
        + " | Address: " + address + " | Balance: $"
        + String.format("%.2f", accountBalance);
  }
}
