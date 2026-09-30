public class Product {
  private static int nextID = 1;
  private String productID;
  private String name;
  private int amountInStock;
  private double salePrice;

  public Product(String name, int amountInStock, double salePrice) {
    this.productID = "P" + nextID;
    nextID++;
    this.name = name;
    this.amountInStock = amountInStock;
    this.salePrice = salePrice;
  }

  public String getProductID() {
    return productID;
  }

  public String getName() {
    return name;
  }

  public int getAmountInStock() {
    return amountInStock;
  }

  public double getSalePrice() {
    return salePrice;
  }

  public boolean equals(String productID) {
    return this.productID.equals(productID);
  }

  public String toString() {
    return "Product ID: " + productID + " | Name: " + name
        + " | Stock: " + amountInStock + " | Price: $"
        + String.format("%.2f", salePrice);
  }
}
