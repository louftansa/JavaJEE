package com.mas.bookstore.util;

import java.util.Random;

public class IsbnGenerator implements NumberGenerator {
    @Override
    public String generateIsbnNumber() {
        return "13-567" + Math.abs(new Random().nextInt());
    }
}
