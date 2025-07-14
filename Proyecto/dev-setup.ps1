# Script de Inicializacion en Modo Desarrollo - Scalia
# Music Theory Desktop Application
# Autor: Equipo de Desarrollo Scalia
# Version: 1.0

param(
    [string]$MySQLPassword = "1234",
    [switch]$SkipDatabaseSetup,
    [switch]$Verbose
)

$ErrorColor = "Red"
$SuccessColor = "Green"
$WarningColor = "Yellow"
$InfoColor = "Cyan"

function Write-Status {
    param(
        [string]$Message,
        [string]$Type = "Info"
    )
    $timestamp = Get-Date -Format "HH:mm:ss"
    $color = switch ($Type) {
        "Error" { $ErrorColor }
        "Success" { $SuccessColor }
        "Warning" { $WarningColor }
        "Info" { $InfoColor }
    }
    Write-Host "[$timestamp] $Message" -ForegroundColor $color
}

function Test-Command {
    param([string]$Command)
    try {
        Get-Command $Command -ErrorAction Stop | Out-Null
        return $true
    } catch {
        return $false
    }
}

function Test-JavaVersion {
    Write-Status "Verificando version de Java..." "Info"
    if (-not (Test-Command "java")) {
        Write-Status "Java no esta instalado o no esta en el PATH" "Error"
        Write-Status "Por favor instala Java 17 o superior desde: https://adoptium.net/" "Info"
        return $false
    }
    try {
        $javaVersion = java -version 2>&1 | Select-String "version" | ForEach-Object { $_.ToString().Split('"')[1] }
        $versionNumber = $javaVersion.Split('.')[0]
        if ($versionNumber -ge 17) {
            Write-Status "Java $javaVersion encontrado" "Success"
            return $true
        } else {
            Write-Status "Se requiere Java 17 o superior. Version actual: $javaVersion" "Error"
            return $false
        }
    } catch {
        Write-Status "Error al verificar version de Java" "Error"
        return $false
    }
}

function Test-MavenVersion {
    Write-Status "Verificando version de Maven..." "Info"
    if (-not (Test-Command "mvn")) {
        Write-Status "Maven no esta instalado o no esta en el PATH" "Error"
        Write-Status "Por favor instala Maven desde: https://maven.apache.org/download.cgi" "Info"
        return $false
    }
    try {
        $mavenVersion = mvn -version | Select-String "Apache Maven" | ForEach-Object { $_.ToString().Split(' ')[2] }
        Write-Status "Maven $mavenVersion encontrado" "Success"
        return $true
    } catch {
        Write-Status "Error al verificar version de Maven" "Error"
        return $false
    }
}

function Test-MySQLConnection {
    Write-Status "Verificando conexion a MySQL..." "Info"
    if (-not (Test-Command "mysql")) {
        Write-Status "MySQL Client no esta instalado o no esta en el PATH" "Error"
        Write-Status "Por favor instala MySQL desde: https://dev.mysql.com/downloads/mysql/" "Info"
        return $false
    }
    try {
        $testQuery = "SELECT 1;" | mysql -u root -p$MySQLPassword 2>$null
        if ($LASTEXITCODE -eq 0) {
            Write-Status "Conexion a MySQL exitosa" "Success"
            return $true
        } else {
            Write-Status "No se pudo conectar a MySQL con la contraseña proporcionada" "Error"
            Write-Status "Verifica que MySQL este ejecutandose y la contraseña sea correcta" "Warning"
            return $false
        }
    } catch {
        Write-Status "Error al verificar conexion a MySQL" "Error"
        return $false
    }
}

function Install-Dependencies {
    Write-Status "Instalando dependencias del proyecto..." "Info"
    try {
        Write-Status "Descargando dependencias de Maven..." "Info"
        mvn dependency:resolve -q
        if ($LASTEXITCODE -eq 0) {
            Write-Status "Dependencias descargadas correctamente" "Success"
        } else {
            Write-Status "Error al descargar dependencias" "Error"
            return $false
        }
        Write-Status "Compilando proyecto..." "Info"
        mvn clean compile -q
        if ($LASTEXITCODE -eq 0) {
            Write-Status "Proyecto compilado correctamente" "Success"
            return $true
        } else {
            Write-Status "Error al compilar el proyecto" "Error"
            return $false
        }
    } catch {
        Write-Status "Error durante la instalacion de dependencias" "Error"
        return $false
    }
}

function Setup-Database {
    Write-Status "Configurando base de datos..." "Info"
    try {
        $schemaFile = "src/main/resources/database/scalia_schema.sql"
        if (-not (Test-Path $schemaFile)) {
            Write-Status "Archivo de esquema no encontrado: $schemaFile" "Error"
            return $false
        }
        Write-Status "Creando base de datos y tablas..." "Info"
        Get-Content $schemaFile | mysql -u root -p$MySQLPassword
        if ($LASTEXITCODE -eq 0) {
            Write-Status "Base de datos configurada correctamente" "Success"
            return $true
        } else {
            Write-Status "Error al configurar la base de datos" "Error"
            return $false
        }
    } catch {
        Write-Status "Error durante la configuracion de la base de datos" "Error"
        return $false
    }
}

function Test-DatabaseConnection {
    Write-Status "Verificando conexion a la base de datos del proyecto..." "Info"
    try {
        $testScript = @"
USE scalia_db;
SELECT COUNT(*) as user_count FROM users;
"@
        $testScript | mysql -u root -p$MySQLPassword 2>$null
        if ($LASTEXITCODE -eq 0) {
            Write-Status "Conexion a la base de datos del proyecto exitosa" "Success"
            return $true
        } else {
            Write-Status "Error al conectar con la base de datos del proyecto" "Error"
            return $false
        }
    } catch {
        Write-Status "Error al verificar conexion a la base de datos" "Error"
        return $false
    }
}

function Start-Application {
    Write-Status "Iniciando aplicacion en modo desarrollo..." "Info"
    try {
        Write-Status "Ejecutando Scalia - Music Theory Application..." "Info"
        mvn javafx:run
    } catch {
        Write-Status "Error al iniciar la aplicacion" "Error"
        Write-Status "Puedes intentar ejecutar manualmente con: mvn javafx:run" "Info"
    }
}

function Show-Usage {
    Write-Host @"

Script de Inicializacion en Modo Desarrollo - Scalia
==================================================

Uso: .\dev-setup.ps1 [opciones]

Opciones:
    -MySQLPassword <password>    Contraseña de MySQL (default: 1234)
    -SkipDatabaseSetup          Saltar configuracion de base de datos
    -Verbose                    Mostrar informacion detallada

Ejemplos:
    .\dev-setup.ps1
    .\dev-setup.ps1 -MySQLPassword "mi_contraseña"
    .\dev-setup.ps1 -SkipDatabaseSetup -Verbose

"@ -ForegroundColor $InfoColor
}

function Main {
    Write-Host @"
===============================================
           SCALIA - DEV SETUP
       Music Theory Desktop Application
===============================================
"@ -ForegroundColor $InfoColor
    Write-Status "Iniciando verificacion de dependencias..." "Info"
    $javaOk = Test-JavaVersion
    $mavenOk = Test-MavenVersion
    $mysqlOk = Test-MySQLConnection
    if (-not ($javaOk -and $mavenOk -and $mysqlOk)) {
        Write-Status "No se cumplen las dependencias minimas. Revisa los errores anteriores." "Error"
        exit 1
    }
    Write-Status "Todas las dependencias minimas estan disponibles" "Success"
    if (-not (Install-Dependencies)) {
        Write-Status "Error al instalar dependencias del proyecto" "Error"
        exit 1
    }
    if (-not $SkipDatabaseSetup) {
        if (-not (Setup-Database)) {
            Write-Status "Error al configurar la base de datos" "Error"
            exit 1
        }
        if (-not (Test-DatabaseConnection)) {
            Write-Status "Error al verificar conexion a la base de datos del proyecto" "Error"
            exit 1
        }
    } else {
        Write-Status "Configuracion de base de datos omitida" "Warning"
    }
    Write-Status "Configuracion completada exitosamente!" "Success"
    Write-Status "Iniciando aplicacion..." "Info"
    Start-Application
}

if ($args -contains "-h" -or $args -contains "--help") {
    Show-Usage
    exit 0
}

Main 