package org.skypro.skyshop.product;

public class Product {
    private String ProductName;
    private int Price;

    public Product(String productName, int price) {
        this.ProductName = productName;
        this.Price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public int getPrice() {
        return Price;
    }
}
