import java.util.*;
import java.io.*;

public class UserInterface {
  private static UserInterface userInterface;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int ADD_PRODUCTS = 2;
  private static final int ADD_PRODUCTS_TO_WISHLIST = 3;
  private static final int SHOW_CLIENTS = 4;
  private static final int SHOW_PRODUCTS = 5;
  private static final int SHOW_WISHLIST = 6;
  private static final int HELP = 7;

  private UserInterface() {
    warehouse = Warehouse.instance();
  }

  public static UserInterface instance() {
    if (userInterface == null) {
      return userInterface = new UserInterface();
    } else {
      return userInterface;
    }
  }

  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }

  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

  public double getDouble(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Double value = Double.valueOf(item);
        return value.doubleValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between 0 and 7 as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(ADD_PRODUCTS + " to add products");
    System.out.println(ADD_PRODUCTS_TO_WISHLIST + " to add products to a client wishlist");
    System.out.println(SHOW_CLIENTS + " to display all clients");
    System.out.println(SHOW_PRODUCTS + " to display all products");
    System.out.println(SHOW_WISHLIST + " to display a client wishlist");
    System.out.println(HELP + " for help");
  }

  public void addClient() {
    String name = getToken("Enter client name");
    String address = getToken("Enter address");
    Client result = warehouse.addClient(name, address);
    if (result == null) {
      System.out.println("Could not add client");
    } else {
      System.out.println(result);
    }
  }

  public void addProducts() {
    Product result;
    do {
      String name = getToken("Enter product name");
      int amountInStock = getNumber("Enter amount in stock");
      double salePrice = getDouble("Enter sale price");
      result = warehouse.addProduct(name, amountInStock, salePrice);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Product could not be added");
      }
      if (!yesOrNo("Add more products?")) {
        break;
      }
    } while (true);
  }

  public void addProductsToWishlist() {
    String clientID = getToken("Enter client ID");
    Client client = warehouse.searchClient(clientID);
    if (client == null) {
      System.out.println("Client not found / invalid client");
      return;
    }
    do {
      String productID = getToken("Enter product ID");
      int quantity = getNumber("Enter quantity");
      if (warehouse.addToWishlist(clientID, productID, quantity)) {
        System.out.println("Product added to wishlist");
      } else {
        System.out.println("Product not found / invalid product");
      }
      if (!yesOrNo("Add another product to this wishlist?")) {
        break;
      }
    } while (true);
  }

  public void showClients() {
    Iterator<Client> allClients = warehouse.getClients();
    while (allClients.hasNext()) {
      Client client = allClients.next();
      System.out.println(client.toString());
    }
  }

  public void showProducts() {
    Iterator<Product> allProducts = warehouse.getProducts();
    while (allProducts.hasNext()) {
      Product product = allProducts.next();
      System.out.println(product.toString());
    }
  }

  public void showWishlist() {
    String clientID = getToken("Enter client ID");
    Iterator<WishlistItem> items = warehouse.getWishlist(clientID);
    if (items == null) {
      System.out.println("Client not found / invalid client");
      return;
    }
    boolean empty = true;
    while (items.hasNext()) {
      WishlistItem item = items.next();
      System.out.println(item.toString());
      empty = false;
    }
    if (empty) {
      System.out.println("Wishlist is empty");
    }
  }

  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:
          addClient();
          break;
        case ADD_PRODUCTS:
          addProducts();
          break;
        case ADD_PRODUCTS_TO_WISHLIST:
          addProductsToWishlist();
          break;
        case SHOW_CLIENTS:
          showClients();
          break;
        case SHOW_PRODUCTS:
          showProducts();
          break;
        case SHOW_WISHLIST:
          showWishlist();
          break;
        case HELP:
          help();
          break;
      }
    }
  }

  public static void main(String[] args) {
    UserInterface.instance().process();
  }
}
