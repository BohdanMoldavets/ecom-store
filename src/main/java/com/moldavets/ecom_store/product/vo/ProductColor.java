package com.moldavets.ecom_store.product.vo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record ProductColor(String value) {
    public ProductColor {
        if(value == null || value.length() <= 2) {
            throw new RuntimeException();
            //TODO EXCEPTION
        }
        Pattern pattern = Pattern.compile("#[0-9a-f]{3}]|[0-9a-f]{6}]|[0-9a-f]{8}]");
        Matcher matcher = pattern.matcher(value);
        if(!matcher.matches()) {
            //TODO THROW NOT A COLOR EXCEPTION
        }
    }
}
