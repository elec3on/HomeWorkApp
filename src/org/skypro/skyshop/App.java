package org.skypro.skyshop;

import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.FixPriceProduct;
import org.skypro.skyshop.product.Article;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.product.SearchEngine;

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
    }
}