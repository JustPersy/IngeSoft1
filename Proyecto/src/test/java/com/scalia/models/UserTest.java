package com.scalia.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase modelo User.
 * Valida la correcta inicialización, acceso y manipulación de datos de usuario
 * a través de sus constructores y métodos getter/setter.
 */
public class UserTest {
    
    private User testUser;
    private LocalDateTime testDateTime;
    
    @BeforeEach
    void setUp() {
        testDateTime = LocalDateTime.now();
        testUser = new User("testuser", "test@example.com", "password123", "John", "Doe");
    }
    
    @Test
    @DisplayName("Test: Constructor con parámetros inicializa correctamente todos los campos")
    void testUserConstructorWithParameters() {
        User user = new User("johndoe", "john@example.com", "securepass", "John", "Smith");
        
        assertEquals("johndoe", user.getUsername(), "El username debe coincidir con el valor proporcionado");
        assertEquals("john@example.com", user.getEmail(), "El email debe coincidir con el valor proporcionado");
        assertEquals("securepass", user.getPassword(), "La contraseña debe coincidir con el valor proporcionado");
        assertEquals("John", user.getFirstName(), "El nombre debe coincidir con el valor proporcionado");
        assertEquals("Smith", user.getLastName(), "El apellido debe coincidir con el valor proporcionado");
    }
    
    @Test
    @DisplayName("Test: Constructor vacío crea un objeto User válido")
    void testUserEmptyConstructor() {
        User user = new User();
        
        assertNotNull(user, "El objeto User no debe ser nulo después del constructor vacío");
        assertEquals(0, user.getId(), "El ID debe ser 0 por defecto");
        assertNull(user.getUsername(), "El username debe ser nulo por defecto");
        assertNull(user.getEmail(), "El email debe ser nulo por defecto");
        assertNull(user.getPassword(), "La contraseña debe ser nula por defecto");
        assertNull(user.getFirstName(), "El nombre debe ser nulo por defecto");
        assertNull(user.getLastName(), "El apellido debe ser nulo por defecto");
    }
    
    @Test
    @DisplayName("Test: Métodos setters y getters funcionan correctamente")
    void testUserSettersAndGetters() {
        User user = new User();
        
        // Establecer valores
        user.setId(1);
        user.setUsername("newuser");
        user.setEmail("new@example.com");
        user.setPassword("newpassword");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setCreatedAt(testDateTime);
        user.setUpdatedAt(testDateTime);
        
        // Verificar que los valores se establecieron correctamente
        assertEquals(1, user.getId(), "El ID debe ser 1 después de setearlo");
        assertEquals("newuser", user.getUsername(), "El username debe coincidir después de setearlo");
        assertEquals("new@example.com", user.getEmail(), "El email debe coincidir después de setearlo");
        assertEquals("newpassword", user.getPassword(), "La contraseña debe coincidir después de setearla");
        assertEquals("Jane", user.getFirstName(), "El nombre debe coincidir después de setearlo");
        assertEquals("Doe", user.getLastName(), "El apellido debe coincidir después de setearlo");
        assertEquals(testDateTime, user.getCreatedAt(), "La fecha de creación debe coincidir después de setearla");
        assertEquals(testDateTime, user.getUpdatedAt(), "La fecha de actualización debe coincidir después de setearla");
    }
    
    @Test
    @DisplayName("Test: Método toString devuelve representación correcta del usuario")
    void testUserToString() {
        testUser.setId(123);
        testUser.setCreatedAt(testDateTime);
        testUser.setUpdatedAt(testDateTime);
        
        String userString = testUser.toString();
        
        assertNotNull(userString, "El toString no debe devolver null");
        assertTrue(userString.contains("123"), "El toString debe contener el ID del usuario");
        assertTrue(userString.contains("testuser"), "El toString debe contener el username");
        assertTrue(userString.contains("test@example.com"), "El toString debe contener el email");
        assertTrue(userString.contains("John"), "El toString debe contener el nombre");
        assertTrue(userString.contains("Doe"), "El toString debe contener el apellido");
        assertFalse(userString.contains("password123"), "El toString NO debe contener la contraseña por seguridad");
    }
    
    @Test
    @DisplayName("Test: Manejo de valores nulos en setters")
    void testUserNullValues() {
        User user = new User();
        
        // Establecer valores nulos
        user.setUsername(null);
        user.setEmail(null);
        user.setPassword(null);
        user.setFirstName(null);
        user.setLastName(null);
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
        
        // Verificar que los valores nulos se manejan correctamente
        assertNull(user.getUsername(), "El username debe ser nulo después de setearlo como null");
        assertNull(user.getEmail(), "El email debe ser nulo después de setearlo como null");
        assertNull(user.getPassword(), "La contraseña debe ser nula después de setearla como null");
        assertNull(user.getFirstName(), "El nombre debe ser nulo después de setearlo como null");
        assertNull(user.getLastName(), "El apellido debe ser nulo después de setearlo como null");
        assertNull(user.getCreatedAt(), "La fecha de creación debe ser nula después de setearla como null");
        assertNull(user.getUpdatedAt(), "La fecha de actualización debe ser nula después de setearla como null");
    }
} 