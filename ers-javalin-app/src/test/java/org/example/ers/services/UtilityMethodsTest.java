package org.example.ers.services;

import com.revature.ers.utilities.UtilityMethods;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilityMethodsTest {

    @Test
    public void test_generateRandomPw_generatesValidPasswords() {
        for (int i = 0; i < 100; i++) {
            assertTrue(UtilityMethods.validPassword(UtilityMethods.generateRandomPw()));
        }
    }

    @Test
    public void test_validUsername_returnsTrueWithValidUserNames() {
        String[] testNames = {
                "charlie10",
                "kevin",
                "matt",
                "sabrina",
                "millie_sullivan",
                "carlos",
        };
        for (String name : testNames) {
            assertTrue(UtilityMethods.validUsername(name));
        }
    }

    @Test
    public void test_validUsername_returnsFalseWithInvalidUserNames() {
        String[] testNames = {
                "charlie10!",
                "__kevin",
                "ma._tt",
                "sab_rina_",
                ".millie",
                "ca",
        };
        for (String name : testNames) {
            assertFalse(UtilityMethods.validUsername(name));
        }
    }
}
