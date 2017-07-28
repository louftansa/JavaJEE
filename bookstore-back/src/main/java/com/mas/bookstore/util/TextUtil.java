package com.mas.bookstore.util;

public class TextUtil {

    public String sanitize(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
