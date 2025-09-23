package uz.pdp.education.enums;

import lombok.Getter;

@Getter
public enum Months {
    JANUARY(1, "Yanvar"),
    FEBRUARY(2, "Fevral"),
    MARCH(3, "Mart"),
    APRIL(4, "Aprel"),
    MAY(5, "May"),
    JUNE(6, "Iyun"),
    JULY(7, "Iyul"),
    AUGUST(8, "Avgust"),
    SEPTEMBER(9, "Sentyabr"),
    OCTOBER(10, "Oktabr"),
    NOVEMBER(11, "Noyabr"),
    DECEMBER(12, "Dekabr");

    private final int number;
    private final String uzName;

    Months(int number, String uzName) {
        this.number = number;
        this.uzName = uzName;
    }

    public static Months fromNumber(int number) {
        for (Months month : Months.values()) {
            if (month.getNumber() == number) {
                return month;
            }
        }
        throw new IllegalArgumentException("Invalid month number: " + number);
    }
}