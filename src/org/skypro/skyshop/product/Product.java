package org.skypro.skyshop.product;

import org.skypro.skyshop.search.Searchable;
import java.util.Objects;

public abstract class Product implements Searchable {
    private String id;
    private String name;

    public Product(String id, String name) {

        // Проверка названия продукта: не null и не пустая/пробельная строка
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустым (в том числе состоять только из пробелов).");
        }

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустым (в том числе состоять только из пробелов).");
        }
        this.name = name;
    }

    public abstract double getPrice();

    public abstract boolean isSpecial();

    @Override
    public String getSearchTerm() {
        return name;
    }

    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    /**
     * Сравнение продуктов только по имени.
     * Продукты с одинаковым именем считаются одинаковыми.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    /**
     * hashCode вычисляется только по имени продукта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
