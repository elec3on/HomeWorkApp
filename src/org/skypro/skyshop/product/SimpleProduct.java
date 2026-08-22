package org.skypro.skyshop.product;

public class SimpleProduct extends Product {
    private final double price;

    public SimpleProduct(String id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice();
    }
}
