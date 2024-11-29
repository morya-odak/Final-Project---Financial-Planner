package src;

public enum Category {
    FOOD,
    TRANSPORTATION,
    ENTERTAINMENT,
    UTILITIES,
    MISCELLANEOUS;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(); // Example: FOOD -> "Food"
    }

    public static Category fromString(String category) {
        return Category.valueOf(category.toUpperCase());
    }
}
