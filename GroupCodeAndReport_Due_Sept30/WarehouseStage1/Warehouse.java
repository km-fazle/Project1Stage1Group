import java.util.*;

public class Warehouse {
  private ClientList clientList;
  private ProductList productList;
  private static Warehouse warehouse;

  private Warehouse() {
    clientList = ClientList.instance();
    productList = ProductList.instance();
  }

  public static Warehouse instance() {
    if (warehouse == null) {
      return (warehouse = new Warehouse());
    } else {
      return warehouse;
    }
  }

  public Client addClient(String name, String address) {
    Client client = new Client(name, address);
    if (clientList.insertClient(client)) {
      return client;
    }
    return null;
  }

  public Product addProduct(String name, int amountInStock, double salePrice) {
    Product product = new Product(name, amountInStock, salePrice);
    if (productList.insertProduct(product)) {
      return product;
    }
    return null;
  }

  public Client searchClient(String clientID) {
    return clientList.searchClient(clientID);
  }

  public boolean addToWishlist(String clientID, String productID, int quantity) {
    Client client = clientList.searchClient(clientID);
    if (client == null) {
      return false;
    }
    Product product = productList.searchProduct(productID);
    if (product == null) {
      return false;
    }
    client.addOrUpdateWishlist(product, quantity);
    return true;
  }

  public Iterator<Client> getClients() {
    return clientList.getClients();
  }

  public Iterator<Product> getProducts() {
    return productList.getProducts();
  }

  public Iterator<WishlistItem> getWishlist(String clientID) {
    Client client = clientList.searchClient(clientID);
    if (client == null) {
      return null;
    }
    return client.getWishlistItems();
  }

  public String toString() {
    return clientList + "\n" + productList;
  }
}
