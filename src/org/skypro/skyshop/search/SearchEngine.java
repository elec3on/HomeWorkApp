package org.skypro.skyshop.search;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Comparator;

public class SearchEngine {
    // HashSet для хранения — быстрое добавление и проверка наличия
    private final Set<Searchable> items;

    public SearchEngine() {
        this.items = new HashSet<>();
    }

    public void add(Searchable item) {
        if (item == null) {
            throw new IllegalArgumentException("Нельзя добавить null в поисковый движок");
        }
        items.add(item);
    }

    /**
     * Ищет все элементы, текст которых содержит искомую строку.
     * Возвращает TreeSet, отсортированный по имени (getStringRepresentation) в алфавитном порядке.
     * Компаратор передан лямбдой.
     */
    public Set<Searchable> search(String query) {
        // Компаратор сортирует по строковому представлению (имени) в алфавитном порядке
        Set<Searchable> results = new TreeSet<>(
                Comparator.comparing(Searchable::getSearchTerm)
        );

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        for (Searchable item : items) {
            String searchTerm = item.getSearchTerm().toLowerCase();
            if (searchTerm.contains(query)) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Ищет лучший результат по количеству вхождений.
     * Бросает BestResultNotFound, если ничего не найдено.
     */
    public Searchable findBestMatch(String query) throws BestResultNotFound {
        if (query == null || query.trim().isEmpty()) {
            throw new BestResultNotFound("Пустой или null запрос");
        }

        Searchable best = null;
        int bestCount = 0;

        for (Searchable item : items) {
            String searchTerm = item.getSearchTerm();
            int count = 0;
            int index = 0;
            while ((index = searchTerm.indexOf(query, index)) != -1) {
                count++;
                index += query.length();
            }

            if (count > bestCount) {
                bestCount = count;
                best = item;
            }
        }

        if (best == null) {
            throw new BestResultNotFound("Не найдено результатов для запроса: " + query);
        }

        return best;
    }
}
