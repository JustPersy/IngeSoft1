package com.scalia.utils;

/**
 * Utility class for input validation.
 * Provides methods to validate user input.
 */
public class ValidationUtils {
    
    /**
     * Validate email format.
     * @param email The email to validate
     * @return true if email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                           "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
        return email.matches(emailRegex);
    }
    
    /**
     * Validate username format.
     * @param username The username to validate
     * @return true if username is valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        // Username should be 3-20 characters, alphanumeric, underscores and hyphens
        String usernameRegex = "^[a-zA-Z0-9_-]{3,20}$";
        return username.matches(usernameRegex);
    }
    
    /**
     * Validate password strength.
     * @param password The password to validate
     * @return true if password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        
        // Password should have at least 6 characters
        return password.length() >= 6;
    }
    
    /**
     * Validate that a string is not null or empty.
     * @param value The string to validate
     * @return true if string is valid, false otherwise
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validate name format (first name, last name).
     * @param name The name to validate
     * @return true if name is valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        // Name should be 2-50 characters, letters and spaces only
        String nameRegex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]{2,50}$";
        return name.matches(nameRegex);
    }
    
    /**
     * Get error message for invalid email.
     * @return Error message
     */
    public static String getEmailErrorMessage() {
        return "El email debe tener un formato válido (ejemplo@dominio.com)";
    }
    
    /**
     * Get error message for invalid username.
     * @return Error message
     */
    public static String getUsernameErrorMessage() {
        return "El nombre de usuario debe tener 3-20 caracteres (solo letras, números y guiones bajos)";
    }
    
    /**
     * Get error message for invalid password.
     * @return Error message
     */
    public static String getPasswordErrorMessage() {
        return "La contraseña debe tener al menos 6 caracteres";
    }
    
    /**
     * Get error message for empty field.
     * @param fieldName The name of the field
     * @return Error message
     */
    public static String getEmptyFieldErrorMessage(String fieldName) {
        return "El campo " + fieldName + " es obligatorio";
    }
    
    /**
     * Get error message for invalid name.
     * @return Error message
     */
    public static String getNameErrorMessage() {
        return "El nombre debe tener 2-50 caracteres (solo letras y espacios)";
    }
}
