package org.skypro.skyshop;

import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.FixPriceProduct;
import org.skypro.skyshop.content.Article;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.search.BestResultNotFound;
import org.skypro.skyshop.search.SearchEngine;
import org.skypro.skyshop.search.Searchable;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Создаём товары
        SimpleProduct milk = new SimpleProduct("1", "Молоко", 89.0);
        DiscountedProduct bread = new DiscountedProduct("2", "Хлеб", 50.0, 20);
        FixPriceProduct sugar = new FixPriceProduct("3", "Сахар");
        DiscountedProduct juice = new DiscountedProduct("4", "Сок", 120.0, 15);
        FixPriceProduct salt = new FixPriceProduct("5", "Соль");

        // Демонстрация работы корзины
        ProductBasket basket = new ProductBasket();
        basket.addProduct(milk);
        basket.addProduct(bread);
        basket.addProduct(sugar);
        basket.addProduct(juice);
        basket.addProduct(salt);
        basket.printBasket();

        System.out.println();

        // Создаём статьи
        Article article1 = new Article("О молоке", "Молоко — полезный напиток, богатый кальцием.");
        Article article2 = new Article("Как выбрать хлеб", "Свежий хлеб должен иметь ароматную корочку.");
        Article article3 = new Article("Сахар и соль", "Сахар и соль — самые популярные приправы на кухне.");

        // Создаём поисковый движок
        SearchEngine engine = new SearchEngine();
        engine.add(milk);
        engine.add(bread);
        engine.add(sugar);
        engine.add(juice);
        engine.add(salt);
        engine.add(article1);
        engine.add(article2);
        engine.add(article3);

        // Поиск по разным строкам
        System.out.println("Поиск «Молоко»:");
        List<Searchable> results1 = engine.search("Молоко");
        for (Searchable result : results1) {
            System.out.println(result.getStringRepresentation());
        }

        System.out.println("\nПоиск «Са»:");
        List<Searchable> results2 = engine.search("Са");
        for (Searchable result : results2) {
            System.out.println(result.getStringRepresentation());
        }

        System.out.println("\nПоиск «хлеб»:");
        List<Searchable> results3 = engine.search("хлеб");
        for (Searchable result : results3) {
            System.out.println(result.getStringRepresentation());
        }

        System.out.println("\nПоиск «О»:");
        List<Searchable> results4 = engine.search("О");
        for (Searchable result : results4) {
            System.out.println(result.getStringRepresentation());
        }
        System.out.println("\n----------------------------------------------------\n");

// Товары с явно неправильными данными — демонстрация обработки исключений
        System.out.println("Создание товаров с некорректными данными и обработка ошибок:");

        // Отрицательная цена
        try {
            new SimpleProduct("6", "Йогурт", -10.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        // Скидка больше 100%
        try {
            new DiscountedProduct("7", "Печенье", 100.0, 120);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        // Пустое название для FixPriceProduct
        try {
            new FixPriceProduct("8", "");
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        // --- Поиск с найденными результатами ---
        try {
            Searchable best = engine.findBestMatch("Молоко");  // Используем findBestMatch
            System.out.println("Лучший результат для 'Молоко': " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println(e.getMessage());
        }

        // --- Поиск без результатов ---
        try {
            Searchable best = engine.findBestMatch("ТакойТоварНеСуществует");
            System.out.println("Лучший результат: " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println(e.getMessage());
        }

        // --- Поиск по пустой строке ---
        try {
            Searchable best = engine.findBestMatch("");
            System.out.println("Лучший результат: " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println(e.getMessage());
        }

        // --- Поиск по null ---
        try {
            Searchable best = engine.findBestMatch(null);
            System.out.println("Лучший результат: " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nПрограмма завершена без аварийного падения благодаря обработке исключений.");

        // --- Демонстрация removeAllByName ---

// 1. Удалить существующий продукт из корзины
        System.out.println("\n----------------------------------------------------\n");
        System.out.println("Удаление продукта «Сахар» из корзины:");
        List<Product> removed = basket.removeAllByName("Сахар");

// 2. Вывести удалённые продукты на экран
        System.out.println("Удалённые продукты:");
        for (Product p : removed) {
            System.out.println("- " + p.getName());
        }

// 3. Вывести содержимое корзины
        System.out.println();
        basket.printBasket();

// 4. Удалить несуществующий продукт
        System.out.println("\nУдаление продукта «Кофе» (которого нет в корзине):");
        List<Product> removedNotFound = basket.removeAllByName("Кофе");

// 5. Проверить, что список пустой, и вывести сообщение
        if (removedNotFound.isEmpty()) {
            System.out.println("Список пуст");
        }

// 6. Вывести содержимое корзины на экран
        System.out.println();
        basket.printBasket();
    }
}