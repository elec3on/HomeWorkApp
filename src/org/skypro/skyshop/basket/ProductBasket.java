package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProductBasket {
    /** Храним продукты в Map: ключ — нормализованное имя (trim().toLowerCase()),
    * значение — список продуктов с таким именем (поддерживаем дубликаты).
    * HashMap даёт быстрый поиск по имени (O(1)).
    */
    private final Map<String, List<Product>> items;

    public ProductBasket() {
        this.items = new HashMap<>();
    }

    /**
     * Добавляет продукт в корзину.
     * Ключ для Map вычисляется по нормализованному имени продукта.
     * Если для этого имени ещё нет списка — создаём его через computeIfAbsent.
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Нельзя добавить null в корзину");
        }
        String key = normalizeName(product.getName());
        // computeIfAbsent создаёт новый LinkedList, если ключа ещё нет, и сразу возвращает его
        items.computeIfAbsent(key, k -> new LinkedList<>()).add(product);
    }

    /**
     * Удаляет конкретный экземпляр продукта из корзины.
     * Находит список по нормализованному имени, удаляет из него продукт.
     * Если список становится пустым — удаляем и сам ключ из Map, чтобы isEmpty() работал корректно.
     */
    public boolean removeProduct(Product product) {
        if (product == null) {
            return false;
        }
        String key = normalizeName(product.getName());
        List<Product> list = items.get(key);
        if (list != null) {
            boolean removed = list.remove(product);
            if (list.isEmpty()) {
                // если продуктов с этим именем не осталось — убираем ключ из Map
                items.remove(key);
            }
            return removed;
        }
        return false;
    }

    /**
     * Проверяет, пуста ли корзина.
     * Корзина пуста, если в Map нет ни одного ключа (ни одной группы продуктов).
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Возвращает общее количество товаров в корзине.
     * flatMap превращает Stream<List<Product>> в Stream<Product>, затем считаем все элементы.
     */
    public int countProduct() {
        return (int) items.values().stream()
                .flatMap(List::stream)
                .count();
    }

    /**
     * Возвращает все продукты как Iterable.
     * Собираем все продукты из всех списков в один LinkedList.
     */
    public Iterable<Product> getItems() {
        List<Product> all = items.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedList::new));
        return all;
    }

    /**
     * Публичный метод countSpecial просто делегирует подсчёт в приватный getSpecialCount.
     */
    public int countSpecial() {
        return (int) getSpecialCount();
    }

    /**
     * Приватный метод для подсчёта специальных товаров.
     * Использует filter и count поверх плоского потока продуктов.
     */
    private long getSpecialCount() {
        return items.values().stream()
                .flatMap(List::stream)
                .filter(Product::isSpecial)
                .count();
    }

    /**
     * Считаем сумму в копейках через mapToInt и sum, затем возвращаем как double (рубли).
     */
    public double getTotalCost() {
        long sumInCents = items.values().stream()
                .flatMap(List::stream)
                .mapToInt(p -> (int) Math.round(p.getPrice() * 100))
                .sum();

        return sumInCents / 100.0;
    }

    /**
     * Проверяет наличие хотя бы одного продукта с указанным именем.
     * Используем быстрый поиск по ключу в Map (O(1)), без перебора всех элементов.
     */
    public boolean containsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return items.containsKey(normalizeName(name));
    }

    /**
     * Удаляет все продукты с указанным именем и возвращает их список.
     * Так как все продукты с одинаковым именем хранятся в одном списке под одним ключом,
     * достаточно удалить ключ из Map — весь список вернётся сразу.
     */
    public List<Product> removeAllByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Product> removed = items.remove(normalizeName(name));
        return removed != null ? removed : new ArrayList<>();
    }

    /**
     * Полностью очищает корзину: удаляет все ключи и значения из Map.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Печатает содержимое корзины.
     * Товары выводятся через forEach поверх плоского потока.
     * Нумерация сохранена с помощью массива int[1], чтобы менять индекс внутри лямбды.
     * Итоговые строки используют новые методы countProduct(), countSpecial(), getTotalCost().
     */
    public void printBasket() {
        if (items.isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }

        System.out.println("Содержимое корзины:");

        int[] index = {1}; // массив, чтобы значение можно было менять из лямбды
        items.values().stream()
                .flatMap(List::stream)
                .forEach(product -> {
                    System.out.printf("%d. %s%n", index[0], product);
                    index[0]++;
                });

        System.out.printf("Всего товаров: %d%n", countProduct());
        System.out.printf("Специальных товаров (по флагу isSpecial): %d%n", countSpecial());
        System.out.printf("Общая стоимость: %.2f руб.%n", getTotalCost());
    }

    private static String normalizeName(String name) {
        return name == null ? "" : name.trim().toLowerCase();
    }
}
