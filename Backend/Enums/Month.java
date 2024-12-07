package Backend.Enums;

public enum Month {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY,
    JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER,
    NOVEMBER, DECEMBER;


    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(); // Example: FOOD -> "Food"
    }

    public static Month fromString(String category) {
        return Month.valueOf(category.toUpperCase());
    }
}