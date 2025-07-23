package com.scalia.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para utilidades de validación de negocio.
 * Valida las reglas de negocio y validaciones de datos de la aplicación.
 * 
 * Estas pruebas cubren funcionalidades centrales de validación
 * que son esenciales para la integridad de los datos.
 */
public class ValidationUtilsTest {
    
    @Test
    @DisplayName("Test: Validación de email debería aceptar formatos válidos")
    void testValidEmailFormat() {
        // Emails válidos
        assertTrue(ValidationUtils.isValidEmail("test@example.com"), 
            "Email con formato estándar debería ser válido");
        assertTrue(ValidationUtils.isValidEmail("user.name@domain.co.uk"), 
            "Email con subdominio debería ser válido");
        assertTrue(ValidationUtils.isValidEmail("test123@test.org"), 
            "Email con números debería ser válido");
        
        // Emails inválidos
        assertFalse(ValidationUtils.isValidEmail("invalid-email"), 
            "Email sin @ debería ser inválido");
        assertFalse(ValidationUtils.isValidEmail("@domain.com"), 
            "Email sin usuario debería ser inválido");
        assertFalse(ValidationUtils.isValidEmail("user@"), 
            "Email sin dominio debería ser inválido");
        assertFalse(ValidationUtils.isValidEmail(""), 
            "Email vacío debería ser inválido");
        assertFalse(ValidationUtils.isValidEmail(null), 
            "Email nulo debería ser inválido");
    }
    
    @Test
    @DisplayName("Test: Validación de contraseña debería verificar requisitos mínimos")
    void testPasswordValidation() {
        // Contraseñas válidas
        assertTrue(ValidationUtils.isValidPassword("Password123"), 
            "Contraseña con mayúscula, minúscula y número debería ser válida");
        assertTrue(ValidationUtils.isValidPassword("SecurePass1!"), 
            "Contraseña con caracteres especiales debería ser válida");
        assertTrue(ValidationUtils.isValidPassword("MyPass123"), 
            "Contraseña de 8+ caracteres debería ser válida");
        assertTrue(ValidationUtils.isValidPassword("123456"), 
            "Contraseña de 6 caracteres debería ser válida");
        
        // Contraseñas inválidas
        assertFalse(ValidationUtils.isValidPassword("12345"), 
            "Contraseña de menos de 6 caracteres debería ser inválida");
        assertFalse(ValidationUtils.isValidPassword(""), 
            "Contraseña vacía debería ser inválida");
        assertFalse(ValidationUtils.isValidPassword(null), 
            "Contraseña nula debería ser inválida");
    }
    
    @Test
    @DisplayName("Test: Validación de nombre de usuario debería verificar formato")
    void testUsernameValidation() {
        // Nombres de usuario válidos
        assertTrue(ValidationUtils.isValidUsername("testuser"), 
            "Nombre de usuario alfanumérico debería ser válido");
        assertTrue(ValidationUtils.isValidUsername("user123"), 
            "Nombre de usuario con números debería ser válido");
        assertTrue(ValidationUtils.isValidUsername("test_user"), 
            "Nombre de usuario con guión bajo debería ser válido");
        assertTrue(ValidationUtils.isValidUsername("user-name"), 
            "Nombre de usuario con guión debería ser válido");
        
        // Nombres de usuario inválidos
        assertFalse(ValidationUtils.isValidUsername(""), 
            "Nombre de usuario vacío debería ser inválido");
        assertFalse(ValidationUtils.isValidUsername("a"), 
            "Nombre de usuario muy corto debería ser inválido");
        assertFalse(ValidationUtils.isValidUsername("user@name"), 
            "Nombre de usuario con @ debería ser inválido");
        assertFalse(ValidationUtils.isValidUsername("user name"), 
            "Nombre de usuario con espacios debería ser inválido");
        assertFalse(ValidationUtils.isValidUsername("123456789012345678901"), 
            "Nombre de usuario muy largo debería ser inválido");
        assertFalse(ValidationUtils.isValidUsername(null), 
            "Nombre de usuario nulo debería ser inválido");
    }
}