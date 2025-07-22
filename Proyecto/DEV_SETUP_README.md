# Script de Inicialización en Modo Desarrollo - Scalia

Este directorio contiene scripts para inicializar automáticamente el proyecto Scalia en modo desarrollo.

## 📋 Requisitos Previos

Antes de ejecutar los scripts, asegúrate de tener instalado:

### 1. Java 17 o superior
- **Descarga**: https://adoptium.net/
- **Verificación**: `java -version`

### 2. Maven 3.6 o superior
- **Descarga**: https://maven.apache.org/download.cgi
- **Verificación**: `mvn -version`

### 3. MySQL Server 8.0 o superior
- **Descarga**: https://dev.mysql.com/downloads/mysql/
- **Verificación**: `mysql --version`

## 🚀 Scripts Disponibles

### Para Windows (PowerShell)
```powershell
.\dev-setup.ps1
```

### Para Linux/macOS (Bash)
```bash
./dev-setup.sh
```

## ⚙️ Opciones de Configuración

### PowerShell (Windows)
```powershell
# Uso básico
.\dev-setup.ps1

# Con contraseña personalizada de MySQL
.\dev-setup.ps1 -MySQLPassword "mi_contraseña"

# Saltar configuración de base de datos
.\dev-setup.ps1 -SkipDatabaseSetup

# Modo verbose
.\dev-setup.ps1 -Verbose

# Combinar opciones
.\dev-setup.ps1 -MySQLPassword "mi_contraseña" -SkipDatabaseSetup
```

### Bash (Linux/macOS)
```bash
# Uso básico
./dev-setup.sh

# Con contraseña personalizada de MySQL
./dev-setup.sh "mi_contraseña"

# Saltar configuración de base de datos
./dev-setup.sh "1234" true

# Modo verbose
./dev-setup.sh "1234" false true

# Ver ayuda
./dev-setup.sh -h
```

## 🔧 Qué hace el Script

### 1. Verificación de Dependencias
- ✅ Verifica que Java 17+ esté instalado
- ✅ Verifica que Maven esté instalado
- ✅ Verifica que MySQL esté ejecutándose y sea accesible

### 2. Instalación de Dependencias
- 📦 Descarga todas las dependencias de Maven (JavaFX, MySQL Connector, JUnit)
- 🔨 Compila el proyecto
- ✅ Verifica que la compilación sea exitosa

### 3. Configuración de Base de Datos
- 🗄️ Crea la base de datos `scalia_db`
- 📋 Ejecuta el script de esquema (`scalia_schema.sql`)
- 📊 Inserta datos de ejemplo (instrumentos, acordes, conceptos teóricos)
- ✅ Verifica que la conexión a la base de datos funcione

### 4. Ejecución de la Aplicación
- 🎵 Inicia Scalia en modo desarrollo
- 🖥️ Abre la interfaz gráfica de usuario
- 🔍 Ejecuta en modo debug/verbose

## 📊 Estructura de la Base de Datos

El script crea las siguientes tablas con datos de ejemplo:

### Tablas Principales
- **users**: Usuarios del sistema
- **instruments**: Instrumentos musicales (Guitarra, Piano, Bajo)
- **tunings**: Afinaciones para cada instrumento
- **chords**: Acordes musicales con digitaciones
- **theory_concepts**: Conceptos de teoría musical
- **user_progress**: Progreso del usuario en cada concepto

### Datos de Ejemplo Incluidos
- 🎸 4 instrumentos (Guitarra Acústica, Eléctrica, Piano, Bajo)
- 🎵 7 acordes mayores (Do, Re, Mi, Fa, Sol, La, Si)
- 🎼 6 conceptos teóricos (Notas, Escalas, Acordes, etc.)
- 🎯 Múltiples afinaciones por instrumento

## 🛠️ Solución de Problemas

### Error: "Java no está instalado"
```bash
# Instalar Java 17 desde Adoptium
# https://adoptium.net/
```

### Error: "Maven no está instalado"
```bash
# Instalar Maven
# https://maven.apache.org/download.cgi
```

### Error: "No se pudo conectar a MySQL"
1. Verifica que MySQL esté ejecutándose
2. Confirma la contraseña del usuario root
3. Asegúrate de que el puerto 3306 esté disponible

### Error: "Error al configurar la base de datos"
1. Verifica que el usuario MySQL tenga permisos de creación de BD
2. Confirma que no exista una base de datos `scalia_db` previa
3. Revisa los logs de MySQL para más detalles

### Error: "Error al compilar el proyecto"
1. Verifica que Java 17+ esté instalado
2. Confirma que Maven esté en el PATH
3. Ejecuta manualmente: `mvn clean compile`

## 🔄 Comandos Manuales

Si prefieres ejecutar los pasos manualmente:

```bash
# 1. Verificar dependencias
java -version
mvn -version
mysql --version

# 2. Instalar dependencias
mvn dependency:resolve
mvn clean compile

# 3. Configurar base de datos
mysql -u root -p < src/main/resources/database/scalia_schema.sql

# 4. Ejecutar aplicación
mvn javafx:run
```

## 📝 Configuración de Desarrollo

### Variables de Entorno (Opcional)
```bash
# Configurar contraseña de MySQL
export MYSQL_PASSWORD="tu_contraseña"

# Configurar Java home (si es necesario)
export JAVA_HOME="/path/to/java17"
```

### Configuración de IDE
- **IntelliJ IDEA**: Importar como proyecto Maven
- **Eclipse**: Importar proyecto Maven existente
- **VS Code**: Instalar extensiones Java y Maven

## 🎯 Próximos Pasos

Después de ejecutar el script exitosamente:

1. **Explorar la aplicación**: Registra un usuario y navega por las funcionalidades
2. **Revisar el código**: Familiarízate con la estructura del proyecto
3. **Modificar configuración**: Edita `DatabaseConnection.java` si necesitas cambiar la BD
4. **Desarrollar nuevas funcionalidades**: Sigue el patrón MVC establecido

## 📞 Soporte

Si encuentras problemas:

1. Revisa los logs de error en la consola
2. Verifica que todas las dependencias estén instaladas correctamente
3. Confirma que MySQL esté ejecutándose
4. Consulta la documentación del proyecto en `DATABASE_SETUP.md`

---

**¡Listo para desarrollar! 🚀** 