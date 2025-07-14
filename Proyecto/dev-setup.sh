#!/bin/bash

# Script de Inicialización en Modo Desarrollo - Scalia
# Music Theory Desktop Application
# Autor: Equipo de Desarrollo Scalia
# Versión: 1.0

# Configuración
MYSQL_PASSWORD=${1:-"1234"}
SKIP_DB_SETUP=${2:-false}
VERBOSE=${3:-false}

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Función para imprimir mensajes con timestamp y colores
print_status() {
    local message="$1"
    local type="${2:-Info}"
    local timestamp=$(date '+%H:%M:%S')
    
    case $type in
        "Error")
            echo -e "[$timestamp] ❌ $message" >&2
            ;;
        "Success")
            echo -e "[$timestamp] ✅ $message"
            ;;
        "Warning")
            echo -e "[$timestamp] ⚠️  $message"
            ;;
        "Info")
            echo -e "[$timestamp] ℹ️  $message"
            ;;
    esac
}

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verificar versión de Java
check_java_version() {
    print_status "Verificando versión de Java..." "Info"
    
    if ! command_exists java; then
        print_status "Java no está instalado o no está en el PATH" "Error"
        print_status "Por favor instala Java 17 o superior desde: https://adoptium.net/" "Info"
        return 1
    fi
    
    # Obtener versión de Java
    local java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    local major_version=$(echo $java_version | cut -d'.' -f1)
    
    if [ "$major_version" -ge 17 ]; then
        print_status "Java $java_version encontrado" "Success"
        return 0
    else
        print_status "Se requiere Java 17 o superior. Versión actual: $java_version" "Error"
        return 1
    fi
}

# Verificar versión de Maven
check_maven_version() {
    print_status "Verificando versión de Maven..." "Info"
    
    if ! command_exists mvn; then
        print_status "Maven no está instalado o no está en el PATH" "Error"
        print_status "Por favor instala Maven desde: https://maven.apache.org/download.cgi" "Info"
        return 1
    fi
    
    local maven_version=$(mvn -version 2>&1 | grep "Apache Maven" | awk '{print $3}')
    print_status "Maven $maven_version encontrado" "Success"
    return 0
}

# Verificar conexión a MySQL
check_mysql_connection() {
    print_status "Verificando conexión a MySQL..." "Info"
    
    if ! command_exists mysql; then
        print_status "MySQL Client no está instalado o no está en el PATH" "Error"
        print_status "Por favor instala MySQL desde: https://dev.mysql.com/downloads/mysql/" "Info"
        return 1
    fi
    
    # Intentar conectar con la contraseña proporcionada
    if echo "SELECT 1;" | mysql -u root -p"$MYSQL_PASSWORD" >/dev/null 2>&1; then
        print_status "Conexión a MySQL exitosa" "Success"
        return 0
    else
        print_status "No se pudo conectar a MySQL con la contraseña proporcionada" "Error"
        print_status "Verifica que MySQL esté ejecutándose y la contraseña sea correcta" "Warning"
        return 1
    fi
}

# Instalar dependencias del proyecto
install_dependencies() {
    print_status "Instalando dependencias del proyecto..." "Info"
    
    # Descargar dependencias
    print_status "Descargando dependencias de Maven..." "Info"
    if mvn dependency:resolve -q; then
        print_status "Dependencias descargadas correctamente" "Success"
    else
        print_status "Error al descargar dependencias" "Error"
        return 1
    fi
    
    # Compilar el proyecto
    print_status "Compilando proyecto..." "Info"
    if mvn clean compile -q; then
        print_status "Proyecto compilado correctamente" "Success"
        return 0
    else
        print_status "Error al compilar el proyecto" "Error"
        return 1
    fi
}

# Configurar base de datos
setup_database() {
    print_status "Configurando base de datos..." "Info"
    
    local schema_file="src/main/resources/database/scalia_schema.sql"
    
    # Verificar que el archivo de esquema existe
    if [ ! -f "$schema_file" ]; then
        print_status "Archivo de esquema no encontrado: $schema_file" "Error"
        return 1
    fi
    
    print_status "Creando base de datos y tablas..." "Info"
    
    # Ejecutar el script SQL
    if mysql -u root -p"$MYSQL_PASSWORD" < "$schema_file"; then
        print_status "Base de datos configurada correctamente" "Success"
        return 0
    else
        print_status "Error al configurar la base de datos" "Error"
        return 1
    fi
}

# Verificar conexión a la base de datos del proyecto
test_database_connection() {
    print_status "Verificando conexión a la base de datos del proyecto..." "Info"
    
    local test_script="USE scalia_db; SELECT COUNT(*) as user_count FROM users;"
    
    if echo "$test_script" | mysql -u root -p"$MYSQL_PASSWORD" >/dev/null 2>&1; then
        print_status "Conexión a la base de datos del proyecto exitosa" "Success"
        return 0
    else
        print_status "Error al conectar con la base de datos del proyecto" "Error"
        return 1
    fi
}

# Iniciar aplicación
start_application() {
    print_status "Iniciando aplicación en modo desarrollo..." "Info"
    
    print_status "Ejecutando Scalia - Music Theory Application..." "Info"
    mvn javafx:run
}

# Mostrar ayuda
show_usage() {
    echo -e "${CYAN}
Script de Inicialización en Modo Desarrollo - Scalia
==================================================

Uso: ./dev-setup.sh [mysql_password] [skip_db_setup] [verbose]

Parámetros:
    mysql_password    Contraseña de MySQL (default: 1234)
    skip_db_setup     Saltar configuración de BD (true/false, default: false)
    verbose           Mostrar información detallada (true/false, default: false)

Ejemplos:
    ./dev-setup.sh
    ./dev-setup.sh \"mi_contraseña\"
    ./dev-setup.sh \"1234\" true
    ./dev-setup.sh \"1234\" false true

${NC}"
}

# Función principal
main() {
    echo -e "${CYAN}
╔══════════════════════════════════════════════════════════════╗
║                    SCALIA - DEV SETUP                        ║
║              Music Theory Desktop Application                ║
╚══════════════════════════════════════════════════════════════╝
${NC}"
    
    print_status "Iniciando verificación de dependencias..." "Info"
    
    # Verificar dependencias mínimas
    if ! check_java_version; then
        print_status "No se cumplen las dependencias mínimas. Revisa los errores anteriores." "Error"
        exit 1
    fi
    
    if ! check_maven_version; then
        print_status "No se cumplen las dependencias mínimas. Revisa los errores anteriores." "Error"
        exit 1
    fi
    
    if ! check_mysql_connection; then
        print_status "No se cumplen las dependencias mínimas. Revisa los errores anteriores." "Error"
        exit 1
    fi
    
    print_status "Todas las dependencias mínimas están disponibles" "Success"
    
    # Instalar dependencias del proyecto
    if ! install_dependencies; then
        print_status "Error al instalar dependencias del proyecto" "Error"
        exit 1
    fi
    
    # Configurar base de datos (si no se omite)
    if [ "$SKIP_DB_SETUP" != "true" ]; then
        if ! setup_database; then
            print_status "Error al configurar la base de datos" "Error"
            exit 1
        fi
        
        # Verificar conexión a la base de datos del proyecto
        if ! test_database_connection; then
            print_status "Error al verificar conexión a la base de datos del proyecto" "Error"
            exit 1
        fi
    else
        print_status "Configuración de base de datos omitida" "Warning"
    fi
    
    print_status "Configuración completada exitosamente!" "Success"
    print_status "Iniciando aplicación..." "Info"
    
    # Iniciar la aplicación
    start_application
}

# Manejo de parámetros de ayuda
if [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    show_usage
    exit 0
fi

# Ejecutar función principal
main 