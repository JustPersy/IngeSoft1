# Configuración de Base de Datos - Scalia

## Requisitos Previos

1. **MySQL Server** instalado y ejecutándose
2. **MySQL Workbench** o **phpMyAdmin** (opcional, para administración)
3. **Java 17+** instalado
4. **Maven** instalado

## Configuración de la Base de Datos

### 1. Instalar MySQL Server

Si no tienes MySQL instalado:
- Descarga MySQL Community Server desde: https://dev.mysql.com/downloads/mysql/
- Durante la instalación, configura una contraseña para el usuario `root`

### 2. Crear la Base de Datos

#### Opción A: Usando MySQL Command Line
```bash
mysql -u root -p
```

Una vez dentro de MySQL:
```sql
source src/main/resources/database/scalia_schema.sql;
```

#### Opción B: Usando MySQL Workbench
1. Abre MySQL Workbench
2. Conéctate a tu servidor MySQL
3. Abre el archivo `src/main/resources/database/scalia_schema.sql`
4. Ejecuta el script completo

### 3. Configurar Conexión

Edita el archivo `src/main/java/com/scalia/utils/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/scalia_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "tu_contraseña_aqui"; // Cambia esto por tu contraseña
```

## Ejecutar la Aplicación

### 1. Compilar el Proyecto
```bash
mvn clean compile
```

### 2. Ejecutar la Aplicación
```bash
mvn javafx:run
```

## Funcionalidades Implementadas

### ✅ Registro de Usuarios
- Formulario completo de registro
- Validación de campos
- Verificación de usuario/email duplicados
- Almacenamiento en base de datos

### ✅ Inicio de Sesión
- Autenticación con base de datos
- Validación de credenciales
- Navegación a vista principal

### ✅ Base de Datos
- Tabla de usuarios completa
- Tablas para instrumentos, afinaciones, acordes
- Datos de ejemplo incluidos
- Conexión MySQL funcional

## Estructura de la Base de Datos

### Tabla `users`
- `id`: Identificador único
- `username`: Nombre de usuario (único)
- `email`: Correo electrónico (único)
- `password`: Contraseña
- `first_name`: Nombre
- `last_name`: Apellido
- `created_at`: Fecha de creación
- `updated_at`: Fecha de actualización

### Otras Tablas
- `instruments`: Instrumentos musicales
- `tunings`: Afinaciones
- `chords`: Acordes
- `theory_concepts`: Conceptos de teoría musical
- `user_progress`: Progreso del usuario

## Solución de Problemas

### Error de Conexión a MySQL
1. Verifica que MySQL esté ejecutándose
2. Confirma las credenciales en `DatabaseConnection.java`
3. Asegúrate de que la base de datos `scalia_db` exista

### Error de Compilación
1. Verifica que Java 17+ esté instalado
2. Confirma que Maven esté en el PATH
3. Ejecuta `mvn clean compile` para limpiar y recompilar

### Error de JavaFX
1. La aplicación debe ejecutarse con `mvn javafx:run`
2. No ejecutes directamente con `java`

## Próximos Pasos

1. **Implementar encriptación de contraseñas**
2. **Agregar funcionalidad de recuperación de contraseña**
3. **Desarrollar módulos de teoría musical**
4. **Implementar visualizador de acordes**
5. **Agregar afinador de instrumentos** 