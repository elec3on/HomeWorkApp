package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;


public class ProductBasket {
    private final List<Product> items;

    public ProductBasket() {
        this.items = new LinkedList<>();
    }

    // Добавление товара в корзину
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Нельзя добавить null в корзину");
        }
        items.add(product);
    }

    // Удаление товара из корзины (по ссылке)
    public boolean removeProduct(Product product) {
        return items.remove(product);
    }

    // Проверка, пуста ли корзина
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Получение количества товаров
    public int countProduct() {
        return items.size();
    }

    public Iterable<Product> getItems() {
        return items;
    }

    /**
     * Считает количество товаров, у которых установлен флаг isSpecial().
     */
    public int countSpecial() {
        int count = 0;
        for (Product product : items) {
            if (product.isSpecial()) {
                count++;
            }
        }
        return count;
    }

    public double getTotalCost() {
        double total = 0.0;
        for (Product product : items) {
            total += product.getPrice();
        }
        return total;
    }

    /**
     * Проверяет, есть ли в корзине продукт с указанным именем.
     * Сравнение без учёта регистра и с обрезкой пробелов.
     */
    public boolean containsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        String normalizedName = name.trim().toLowerCase();

        for (Product product : items) {
            String productName = product.getName();
            if (productName != null && productName.trim().toLowerCase().equals(normalizedName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет все продукты с указанным именем из корзины.
     * Возвращает список удалённых продуктов (может быть пустым).
     * Сравнение имени — без учёта регистра и лишних пробелов.
     */
    public List<Product> removeAllByName(String name) {
        List<Product> removed = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            return removed;
        }

        String normalizedName = name.trim().toLowerCase();
        Iterator<Product> iterator = items.iterator();

        while (iterator.hasNext()) {
            Product product = iterator.next();
            String productName = product.getName();

            if (productName != null
                    && productName.trim().toLowerCase().equals(normalizedName)) {
                iterator.remove();          // безопасно удаляет текущий элемент
                removed.add(product);       // добавляем в список удалённых
            }
        }

        return removed;
    }

    /**
     * Полностью очищает корзину.
     */
    public void clear() {
        items.clear();
    }

    public void printBasket() {
        if (items.isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }

        System.out.println("Содержимое корзины:");
        int index = 1;
        for (Product product : items) {
            System.out.printf("%d. %s%n", index, product);
            index++;
        }
        System.out.printf("Всего товаров: %d%n", countProduct());
        System.out.printf("Специальных товаров (по флагу isSpecial): %d%n", countSpecial());
        System.out.printf("Общая стоимость: %.2f руб.%n", getTotalCost());
    }
}