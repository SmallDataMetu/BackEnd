package com.smalldata.backend.utils;

import java.util.UUID;

public class CommonUtils {

    /**
     * @return random unique id : String
     */
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
