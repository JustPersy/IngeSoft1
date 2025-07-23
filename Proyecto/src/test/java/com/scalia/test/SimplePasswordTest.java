package com.scalia.test;

import com.scalia.utils.ValidationUtils;

public class SimplePasswordTest {
    public static void main(String[] args) {
        System.out.println("Testing password validation:");
        
        String[] passwords = {"123456", "12345", "", null, "password123"};
        
        for (String pwd : passwords) {
            boolean result = ValidationUtils.isValidPassword(pwd);
            System.out.println("Password: '" + pwd + "' -> " + result);
            if (pwd != null) {
                System.out.println("  Length: " + pwd.length());
                System.out.println("  >= 6: " + (pwd.length() >= 6));
            }
        }
    }
}
