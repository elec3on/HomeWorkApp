package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

public class ProductBasket {
    private Product[] items;

    public void setItems(Product[] items) {
        this.items = items;
    }
    public ProductBasket() {
        items = new Product[5];
    }

    // Добавление продукта
    public void addProduct(Product product) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = product;
                return;
            }
        }
        System.out.println("Невозможно добавить продукт");
    }

    // Общая стоимость корзины
    public int getTotalCost() {
        int total = 0;
        for (Product item : items) {
            if (item != null) {
                total += item.getPrice();
            }
        }
        return total;
    }

    // Печать содержимого корзины
    public void printBasket() {
        boolean isEmpty = true;
        for (Product item : items) {
            if (item != null) {
                System.out.println(item.getProductName() + ": " + item.getPrice());
                isEmpty = false;
            }
        }

        if (isEmpty) {
            System.out.println("в корзине пусто");
        } else {
            System.out.println("Итого: " + getTotalCost());
        }
    }

    // Проверка наличия продукта по имени
    public boolean containsByName(String ProductName) {
        for (Product item : items) {
            if (item != null && item.getProductName().equals(ProductName)) {
                return true;
            }
        }
        return false;
    }

    // Очистка корзины
    public void clear() {
        for (int i = 0; i < items.length; i++) {
            items[i] = null;
        }
    }
}
