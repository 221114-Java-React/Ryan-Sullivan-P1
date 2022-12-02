package org.example.ers.utilities;

import java.util.UUID;

public class UtilityMethods {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static boolean validUsername(String username) {
        //        https://stackoverflow.com/questions/12018245/regular-expression-to-validate-username
        //        Only contains alphanumeric characters, underscore and dot.
        //        Underscore and dot can't be at the end or start of a username (e.g _username / username_ / .username / username.).
        //        Underscore and dot can't be next to each other (e.g. user_.name).
        //        Underscore or dot can't be used multiple times in a row (e.g. user__name / user..name).
        //        Number of characters between 8 and 20.
        return username != null && username.matches("^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    public static boolean validEmail(String email) {
        // https://emailregex.com/
        return email != null &&
                email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    public static boolean validPassword(String password) {
        // https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
        // Minimum eight characters, at least one letter and one number:

        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}
