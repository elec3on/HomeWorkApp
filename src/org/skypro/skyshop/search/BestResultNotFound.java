package org.skypro.skyshop.search;

public class BestResultNotFound extends Exception {
    private final String searchTerm;

    public BestResultNotFound(String searchTerm) {
        super("Не найдено ни одного результата для поиска: " + searchTerm);
        this.searchTerm = searchTerm;
    }

    public String getSearch() {
        return searchTerm;
    }
}