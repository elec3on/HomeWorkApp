package org.skypro.skyshop.product;

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
}
