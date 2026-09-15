package org.skypro.skyshop;

import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.FixPriceProduct;
import org.skypro.skyshop.content.Article;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.search.BestResultNotFound;
import org.skypro.skyshop.search.SearchEngine;
import org.skypro.skyshop.search.Searchable;

import java.util.Arrays;

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
        SearchEngine engine = new SearchEngine(10);
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
        System.out.println(Arrays.toString(engine.search("Молоко")));

        System.out.println("\nПоиск «Са»:");
        System.out.println(Arrays.toString(engine.search("Са")));

        System.out.println("\nПоиск «хлеб»:");
        System.out.println(Arrays.toString(engine.search("хлеб")));

        System.out.println("\nПоиск «О»:");
        System.out.println(Arrays.toString(engine.search("О")));
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
    }
}