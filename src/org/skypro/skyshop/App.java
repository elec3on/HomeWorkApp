package org.skypro.skyshop;

import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.basket.ProductBasket;

public class App {
    public static void main(String[] args) {
        ProductBasket basket = new ProductBasket();

        // Создаём несколько продуктов
        Product apple = new Product("Яблоко", 100);
        Product bread = new Product("Хлеб", 80);
        Product milk = new Product("Молоко", 120);
        Product eggs = new Product("Яйца", 150);
        Product cheese = new Product("Сыр", 800);
        Product cake = new Product("Торт", 600); // лишний, чтобы проверить переполнение

        System.out.println("--- Добавление продуктов ---");
        basket.addProduct(apple);
        basket.addProduct(bread);
        basket.addProduct(milk);
        basket.addProduct(eggs);
        basket.addProduct(cheese);

        System.out.println("\n--- Попытка добавить 6-й продукт (переполнение) ---");
        basket.addProduct(cake);

        System.out.println("\n--- Печать корзины с товарами ---");
        basket.printBasket();

        System.out.println("\n--- Получение общей стоимости ---");
        System.out.println("Общая стоимость: " + basket.getTotalCost());

        System.out.println("\n--- Поиск товара, который есть в корзине ---");
        System.out.println("Есть ли 'Молоко': " + basket.containsByName("Молоко"));

        System.out.println("\n--- Поиск товара, которого нет в корзине ---");
        System.out.println("Есть ли 'Торт': " + basket.containsByName("Торт"));

        System.out.println("\n--- Очистка корзины ---");
        basket.clear();

        System.out.println("\n--- Печать пустой корзины ---");
        basket.printBasket();

        System.out.println("\n--- Стоимость пустой корзины ---");
        System.out.println("Общая стоимость пустой корзины: " + basket.getTotalCost());

        System.out.println("\n--- Поиск в пустой корзине ---");
        System.out.println("Есть ли 'Яблоко' в пустой корзине: " + basket.containsByName("Яблоко"));
    }
}