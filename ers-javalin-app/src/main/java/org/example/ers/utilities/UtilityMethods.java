package org.example.ers.utilities;

import java.util.UUID;

public class UtilityMethods {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
