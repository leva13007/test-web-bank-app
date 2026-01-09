package org.zloyleva.utils;

public enum AppURL {
    HOME("http://localhost:5678/"),
    REGISTRATION("http://localhost:5678/registration"),
    LOGIN("http://localhost:5678/login");

    private String description;

    AppURL(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}