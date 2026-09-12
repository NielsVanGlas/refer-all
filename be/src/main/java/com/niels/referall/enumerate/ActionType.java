package com.niels.referall.enumerate;

public enum ActionType {

    UPLOAD("Upload"),
    DOWNLOAD("Download"),
    INSPECT("Inspect"),
    DELETE("Delete");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public static ActionType fromValue(String value) {
        return switch (value) {
            case "Upload" -> ActionType.UPLOAD;
            case "Download" -> ActionType.DOWNLOAD;
            case "Inspect" -> ActionType.INSPECT;
            case "Delete" -> ActionType.DELETE;
            default -> throw new IllegalArgumentException("Value [" + value
                    + "] not supported.");
        };
    }

    public String getValue() {
        return value;
    }

}
