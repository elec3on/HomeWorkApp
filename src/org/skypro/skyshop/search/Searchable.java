package org.skypro.skyshop.search;

public interface Searchable {
    String getContentType();

    String getName();

    default String getSearchTerm() {
        return getName() + " — " + getContentType();
    }
}
