package com.scalia.dao;

import com.scalia.models.User;
import com.scalia.utils.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase UserDAO.
 * Valida la funcionalidad de acceso a datos para los usuarios, incluyendo
 * autenticación, registro y gestión de usuarios.
 *
 * Estas pruebas interactúan directamente con la base de datos MySQL.
 * Es crucial que la base de datos esté accesible y configurada correctamente
 * en DatabaseConnection para que estas pruebas pasen.
 */
public class UserDAOTest {
    
    private UserDAO userDAO;
    private User testUser;
    
    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        testUser = new User("testuser", "test@example.com", "password123", "John", "Doe");
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Limpiar tabla de usuarios
            stmt.execute("DELETE FROM users");
            stmt.execute("ALTER TABLE users AUTO_INCREMENT = 1");
            
        } catch (SQLException e) {
            fail("Error al preparar la base de datos para la prueba: " + e.getMessage());
        }
    }
    
    @AfterEach
    void tearDown() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
            stmt.execute("ALTER TABLE users AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            System.err.println("Advertencia: Error durante la limpieza post-prueba: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Test: createUser debería crear un usuario exitosamente")
    void testCreateUser() {
        boolean result = userDAO.createUser(testUser);
        
        assertTrue(result, "La creación del usuario debe ser exitosa");
        assertTrue(testUser.getId() > 0, "El usuario debe tener un ID asignado después de la creación");
        assertNotNull(testUser.getCreatedAt(), "La fecha de creación debe estar establecida");
        assertNotNull(testUser.getUpdatedAt(), "La fecha de actualización debe estar establecida");
    }
    
    @Test
    @DisplayName("Test: findByUsername debería encontrar un usuario existente")
    void testFindByUsername() {
        // Primero crear el usuario
        userDAO.createUser(testUser);
        
        // Buscar el usuario por username
        User foundUser = userDAO.findByUsername("testuser");
        
        assertNotNull(foundUser, "El usuario debe ser encontrado por username");
        assertEquals("testuser", foundUser.getUsername(), "El username debe coincidir");
        assertEquals("test@example.com", foundUser.getEmail(), "El email debe coincidir");
        assertEquals("John", foundUser.getFirstName(), "El nombre debe coincidir");
        assertEquals("Doe", foundUser.getLastName(), "El apellido debe coincidir");
    }
    
    @Test
    @DisplayName("Test: findByUsername debería devolver null para usuario inexistente")
    void testFindByUsernameNonExistent() {
        User foundUser = userDAO.findByUsername("nonexistent");
        
        assertNull(foundUser, "Debería devolver null para un usuario que no existe");
    }
    
    @Test
    @DisplayName("Test: authenticateUser debería autenticar credenciales válidas")
    void testAuthenticateUserValidCredentials() {
        // Primero crear el usuario
        userDAO.createUser(testUser);
        
        // Intentar autenticar con credenciales válidas
        User authenticatedUser = userDAO.authenticateUser("testuser", "password123");
        
        assertNotNull(authenticatedUser, "La autenticación debe ser exitosa con credenciales válidas");
        assertEquals("testuser", authenticatedUser.getUsername(), "El username debe coincidir");
        assertEquals("test@example.com", authenticatedUser.getEmail(), "El email debe coincidir");
    }
    
    @Test
    @DisplayName("Test: authenticateUser debería fallar con credenciales inválidas")
    void testAuthenticateUserInvalidCredentials() {
        // Primero crear el usuario
        userDAO.createUser(testUser);
        
        // Intentar autenticar con credenciales inválidas
        User authenticatedUser = userDAO.authenticateUser("testuser", "wrongpassword");
        
        assertNull(authenticatedUser, "La autenticación debe fallar con credenciales inválidas");
    }
    
    @Test
    @DisplayName("Test: usernameExists debería detectar usuarios existentes")
    void testUsernameExists() {
        // Verificar que el usuario no existe inicialmente
        assertFalse(userDAO.usernameExists("testuser"), "El usuario no debe existir inicialmente");
        
        // Crear el usuario
        userDAO.createUser(testUser);
        
        // Verificar que ahora existe
        assertTrue(userDAO.usernameExists("testuser"), "El usuario debe existir después de crearlo");
    }
    
    @Test
    @DisplayName("Test: emailExists debería detectar emails existentes")
    void testEmailExists() {
        // Verificar que el email no existe inicialmente
        assertFalse(userDAO.emailExists("test@example.com"), "El email no debe existir inicialmente");
        
        // Crear el usuario
        userDAO.createUser(testUser);
        
        // Verificar que ahora existe
        assertTrue(userDAO.emailExists("test@example.com"), "El email debe existir después de crear el usuario");
    }
    
    @Test
    @DisplayName("Test: getAllUsers debería devolver todos los usuarios")
    void testGetAllUsers() {
        // Crear varios usuarios
        User user1 = new User("user1", "user1@example.com", "pass1", "User", "One");
        User user2 = new User("user2", "user2@example.com", "pass2", "User", "Two");
        
        userDAO.createUser(user1);
        userDAO.createUser(user2);
        
        // Obtener todos los usuarios
        List<User> allUsers = userDAO.getAllUsers();
        
        assertNotNull(allUsers, "La lista de usuarios no debe ser nula");
        assertEquals(2, allUsers.size(), "Debe devolver 2 usuarios");
        
        // Verificar que ambos usuarios están en la lista
        boolean user1Found = allUsers.stream().anyMatch(u -> "user1".equals(u.getUsername()));
        boolean user2Found = allUsers.stream().anyMatch(u -> "user2".equals(u.getUsername()));
        
        assertTrue(user1Found, "El primer usuario debe estar en la lista");
        assertTrue(user2Found, "El segundo usuario debe estar en la lista");
    }
    
    @Test
    @DisplayName("Test: updateUser debería actualizar información del usuario")
    void testUpdateUser() {
        // Crear el usuario
        userDAO.createUser(testUser);
        
        // Modificar información del usuario
        testUser.setFirstName("Jane");
        testUser.setLastName("Smith");
        testUser.setEmail("jane@example.com");
        
        // Actualizar el usuario
        boolean updateResult = userDAO.updateUser(testUser);
        
        assertTrue(updateResult, "La actualización debe ser exitosa");
        
        // Verificar que los cambios se guardaron
        User updatedUser = userDAO.findByUsername("testuser");
        assertNotNull(updatedUser, "El usuario debe seguir existiendo después de la actualización");
        assertEquals("Jane", updatedUser.getFirstName(), "El nombre debe haberse actualizado");
        assertEquals("Smith", updatedUser.getLastName(), "El apellido debe haberse actualizado");
        assertEquals("jane@example.com", updatedUser.getEmail(), "El email debe haberse actualizado");
    }
    
    @Test
    @DisplayName("Test: deleteUser debería eliminar un usuario existente")
    void testDeleteUser() {
        // Crear el usuario
        userDAO.createUser(testUser);
        int userId = testUser.getId();
        
        // Verificar que el usuario existe
        assertTrue(userDAO.usernameExists("testuser"), "El usuario debe existir antes de eliminarlo");
        
        // Eliminar el usuario
        boolean deleteResult = userDAO.deleteUser(userId);
        
        assertTrue(deleteResult, "La eliminación debe ser exitosa");
        
        // Verificar que el usuario ya no existe
        assertFalse(userDAO.usernameExists("testuser"), "El usuario no debe existir después de eliminarlo");
        assertNull(userDAO.findByUsername("testuser"), "findByUsername debe devolver null para un usuario eliminado");
    }
}