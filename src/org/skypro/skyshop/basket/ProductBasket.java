package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ProductBasket {
    // Храним продукты в Map: ключ — нормализованное имя (trim().toLowerCase()),
    // значение — список продуктов с таким именем (поддерживаем дубликаты).
    // HashMap даёт быстрый поиск по имени (O(1)).
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
            boolean removed = list.remove(product); // удаляем конкретный объект из списка
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
     * Суммируем размеры всех списков внутри Map.
     */
    public int countProduct() {
        int count = 0;
        for (List<Product> list : items.values()) {
            count += list.size();
        }
        return count;
    }

    /**
     * Возвращает все продукты как Iterable для удобного перебора.
     * Собираем все продукты из всех списков Map в один общий список.
     */
    public Iterable<Product> getItems() {
        List<Product> all = new LinkedList<>();
        for (List<Product> list : items.values()) {
            all.addAll(list);
        }
        return all;
    }

    /**
     * Считает количество «специальных» товаров (у которых isSpecial() == true).
     * Проходим по всем спискам в Map, затем по каждому продукту внутри списка.
     */
    public int countSpecial() {
        int count = 0;
        for (List<Product> list : items.values()) {
            for (Product product : list) {
                if (product.isSpecial()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Вычисляет общую стоимость всех товаров в корзине.
     * Перебираем все списки в Map и суммируем цены продуктов.
     */
    public double getTotalCost() {
        double total = 0.0;
        for (List<Product> list : items.values()) {
            for (Product product : list) {
                total += product.getPrice();
            }
        }
        return total;
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
        // remove возвращает удалённый список или null, если ключа не было
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
     * Печатает содержимое корзины в консоль.
     * Для вывода используем двойной цикл: сначала по спискам (значениям Map),
     * затем по каждому продукту в списке. Нумерация идёт подряд.
     */
    public void printBasket() {
        if (items.isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }

        System.out.println("Содержимое корзины:");
        int index = 1;
        // items.values() даёт коллекцию всех списков продуктов
        for (List<Product> list : items.values()) {
            for (Product product : list) {
                System.out.printf("%d. %s%n", index, product);
                index++;
            }
        }
        System.out.printf("Всего товаров: %d%n", countProduct());
        System.out.printf("Специальных товаров (по флагу isSpecial): %d%n", countSpecial());
        System.out.printf("Общая стоимость: %.2f руб.%n", getTotalCost());
    }

    /**
     * Нормализует имя для использования в качестве ключа Map.
     * Обрезает пробелы и приводит к нижнему регистру.
     * null превращается в пустую строку, чтобы избежать ошибок.
     */
    private static String normalizeName(String name) {
        return name == null ? "" : name.trim().toLowerCase();
    }
}
