package org.skypro.skyshop.product;

public class DiscountedProduct extends Product {
    private final double basePrice;
    private final int discountPercent;

    public DiscountedProduct(String id, String name, double basePrice, int discountPercent) {
        super(id, name);

        // Проверка базовой цены (по аналогии с SimpleProduct: цена должна быть положительной)
        if (basePrice <= 0) {
            throw new IllegalArgumentException(
                    "Ошибка базовой цены: " + basePrice + ". Базовая цена должна быть больше 0."
            );
        }
        // Проверка процента скидки: от 0 до 100 включительно
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException(
                    "Ошибка процента скидки: " + discountPercent + ". Процент скидки должен быть между 0 и 100 (включительно)."
            );
        }

        this.basePrice = basePrice;
        this.discountPercent = discountPercent;
    }

    @Override
    public double getPrice() {
        return basePrice * (1 - discountPercent / 100.0);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPrice() + " (" + discountPercent + "%)";
    }
}