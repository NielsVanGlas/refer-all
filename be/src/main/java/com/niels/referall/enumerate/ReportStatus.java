package com.niels.referall.enumerate;

public enum ReportStatus {

    NEW("New"),
    READ("Read");

    private final String value;

    ReportStatus(String value) {
        this.value = value;
    }

    public static ReportStatus fromValue(String value) {
        return switch (value) {
            case "New" -> ReportStatus.NEW;
            case "Read" -> ReportStatus.READ;
            default -> throw new IllegalArgumentException("Value [" + value
                    + "] not supported.");
        };
    }

    public String getValue() {
        return value;
    }

}
