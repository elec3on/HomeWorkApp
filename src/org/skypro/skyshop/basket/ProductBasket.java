package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import java.util.Arrays;

public class ProductBasket {
    private final Product[] items;

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
                total += (int) item.getPrice();
            }
        }
        return total;
    }

    public int countSpecial() {
        int count = 0;
        for (Product item : items) {
            if (item != null && item.isSpecial()) {
                count++;
            }
        }
        return count;
    }

    // Печать содержимого корзины
    public void printBasket() {
        boolean isEmpty = true;
        for (Product item : items) {
            if (item != null) {
                System.out.println(item);
                isEmpty = false;
            }
        }

        if (isEmpty) {
            System.out.println("в корзине пусто");
        } else {
            System.out.println("Итого: " + getTotalCost());
            System.out.println("Специальных товаров: " + countSpecial());
        }
    }

    // Проверка наличия продукта по имени
    public boolean containsByName(String ProductName) {
        for (Product item : items) {
            if (item != null && item.getName().equals(ProductName)) {
                return true;
            }
        }
        return false;
    }

    // Очистка корзины
    public void clear() {
        Arrays.fill(items, null);
    }
}
