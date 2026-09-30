import java.util.*;

public class ProductList {
  private List<Product> products;
  private static ProductList productList;

  private ProductList() {
    products = new LinkedList<Product>();
  }

  public static ProductList instance() {
    if (productList == null) {
      return (productList = new ProductList());
    } else {
      return productList;
    }
  }

  public boolean insertProduct(Product product) {
    products.add(product);
    return true;
  }

  public Product searchProduct(String productID) {
    Iterator<Product> iterator = products.iterator();
    while (iterator.hasNext()) {
      Product product = iterator.next();
      if (product.equals(productID)) {
        return product;
      }
    }
    return null;
  }

  public Iterator<Product> getProducts() {
    return products.iterator();
  }

  public String toString() {
    return products.toString();
  }
}
