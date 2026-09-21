package org.skypro.skyshop.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SearchEngine {
    private final List<Searchable> items;

    public SearchEngine() {
        this.items = new LinkedList<>();
    }

    public void add(Searchable item) {
        if (item == null) {
            throw new IllegalArgumentException("Нельзя добавить null в поисковый движок");
        }
        items.add(item);
    }

    /**
     * Ищет все элементы, текст которых содержит искомую строку.
     * Возвращает TreeMap: ключ — имя (строковое представление) объекта,
     * значение — сам объект. Мапа отсортирована по ключу (по имени).
     */
    public Map<String, Searchable> search(String query) {
        // TreeMap обеспечивает сортировку по ключу (по имени объекта)
        Map<String, Searchable> results = new TreeMap<>();

        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String lowerQuery = query.toLowerCase();

        for (Searchable item : items) {
            String text = item.getStringRepresentation().toLowerCase();
            if (text.contains(lowerQuery)) {
                // Ключ — строковое представление, значение — сам объект
                results.put(item.getStringRepresentation(), item);
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

        String lowerQuery = query.toLowerCase();
        Searchable best = null;
        int bestCount = 0;

        for (Searchable item : items) {
            String text = item.getStringRepresentation().toLowerCase();
            int count = countOccurrences(text, lowerQuery);

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

    private int countOccurrences(String text, String query) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(query, index)) != -1) {
            count++;
            index += query.length();
        }
        return count;
    }
}
