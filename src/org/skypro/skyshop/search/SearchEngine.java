package org.skypro.skyshop.search;

public class SearchEngine {
    private final Searchable[] items;

    public SearchEngine(int capacity) {
        this.items = new Searchable[capacity];
    }

    public void add(Searchable searchable) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = searchable;
                return;
            }
        }
        // Массив заполнен — элемент не добавляется
    }

    public Searchable[] search(String query) {
        Searchable[] results = new Searchable[5];
        int found = 0;
        for (int i = 0; i < items.length && found < 5; i++) {
            if (items[i] != null && items[i].getSearchTerm().contains(query)) {
                results[found] = items[i];
                found++;
            }
        }
        return results;
    }

    public Searchable findBestMatch(String search) throws BestResultNotFound {
        // 1) Некорректный запрос → сразу выбрасываем исключение
        if (search == null || search.isEmpty()) {
            throw new BestResultNotFound(search);
        }

        Searchable bestMatch = null;
        int maxCount = 0;

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) continue;

            String term = items[i].getSearchTerm();
            if (term == null || term.isEmpty()) continue;

            int count = countOccurrences(term, search);

            if (count > maxCount) {
                maxCount = count;
                bestMatch = items[i];
            }
        }

        // 2) Если ничего не найдено (ни одного вхождения) → исключение
        if (bestMatch == null || maxCount == 0) {
            throw new BestResultNotFound(search);
        }

        return bestMatch;
    }

    // Подсчёт непересекающихся вхождений подстроки (безопасно даже для пустой подстроки,
    // но мы вызываем только с непустой)
    private int countOccurrences(String text, String substring) {
        int count = 0;
        int index = 0;
        int foundIndex = text.indexOf(substring, index);

        while (foundIndex != -1) {
            count++;
            index = foundIndex + substring.length();
            foundIndex = text.indexOf(substring, index);
        }
        return count;
    }
}
