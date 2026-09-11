package com.niels.referall.enumerate;

public enum BloodType {

    OPLUS("0+"),
    OMINUX("0-"),
    APLUS("A+"),
    AMINUS("A-"),
    BPLUS("B+"),
    BMINUX("B-"),
    ABPLUS("AB+"),
    ABMINUS("AB-");

    private final String value;

    BloodType(String value) {
    this.value = value;
}

    public String getValue() {
        return value;
    }

    public static BloodType fromValue(String value) {
        return switch (value) {
            case "0+" -> BloodType.OPLUS;
            case "0-" -> BloodType.OMINUX;
            case "A+" -> BloodType.APLUS;
            case "A-" -> BloodType.AMINUS;
            case "B+" -> BloodType.BPLUS;
            case "B-" -> BloodType.BMINUX;
            case "AB+" -> BloodType.ABPLUS;
            case "AB-" -> BloodType.ABMINUS;
            default -> throw new IllegalArgumentException("Value [" + value
                    + "] not supported.");
        };
    }

}
