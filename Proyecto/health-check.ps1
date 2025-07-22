# Script de Verificación de Salud - Scalia
# Music Theory Desktop Application
# Autor: Equipo de Desarrollo Scalia
# Versión: 1.0

param(
    [string]$MySQLPassword = "1234",
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

function Test-JavaHealth {
    Write-Status "Verificando salud de Java..." "Info"
    if (-not (Test-Command "java")) {
        Write-Status "Java no está disponible" "Error"
        return $false
    }
    try {
        $javaVersion = java -version 2>&1 | Select-String "version" | ForEach-Object { $_.ToString().Split('"')[1] }
        $versionNumber = $javaVersion.Split('.')[0]
        if ($versionNumber -ge 17) {
            Write-Status "Java $javaVersion - Version compatible" "Success"
            return $true
        } else {
            Write-Status "Java $javaVersion - Se requiere version 17+" "Error"
            return $false
        }
    } catch {
        Write-Status "Error al verificar Java" "Error"
        return $false
    }
}

function Test-MavenHealth {
    Write-Status "Verificando salud de Maven..." "Info"
    if (-not (Test-Command "mvn")) {
        Write-Status "Maven no está disponible" "Error"
        return $false
    }
    try {
        $mavenVersion = mvn -version | Select-String "Apache Maven" | ForEach-Object { $_.ToString().Split(' ')[2] }
        Write-Status "Maven $mavenVersion - Funcionando correctamente" "Success"
        return $true
    } catch {
        Write-Status "Error al verificar Maven" "Error"
        return $false
    }
}

function Test-MySQLHealth {
    Write-Status "Verificando salud de MySQL..." "Info"
    if (-not (Test-Command "mysql")) {
        Write-Status "MySQL Client no está disponible" "Error"
        return $false
    }
    try {
        $testQuery = "SELECT 1;" | mysql -u root -p$MySQLPassword 2>$null
        if ($LASTEXITCODE -eq 0) {
            Write-Status "MySQL - Conexion exitosa" "Success"
            return $true
        } else {
            Write-Status "MySQL - Error de conexion" "Error"
            return $false
        }
    } catch {
        Write-Status "Error al verificar MySQL" "Error"
        return $false
    }
}

function Test-ProjectStructure {
    Write-Status "Verificando estructura del proyecto..." "Info"
    $requiredFiles = @(
        "pom.xml",
        "src/main/java/com/scalia/Main.java",
        "src/main/resources/database/scalia_schema.sql",
        "src/main/java/com/scalia/utils/DatabaseConnection.java"
    )
    $missingFiles = @()
    foreach ($file in $requiredFiles) {
        if (-not (Test-Path $file)) {
            $missingFiles += $file
        }
    }
    if ($missingFiles.Count -eq 0) {
        Write-Status "Estructura del proyecto - Completa" "Success"
        return $true
    } else {
        Write-Status "Archivos faltantes: $($missingFiles -join ', ')" "Error"
        return $false
    }
}

function Test-ProjectCompilation {
    Write-Status "Verificando compilacion del proyecto..." "Info"
    try {
        mvn clean compile -q
        if ($LASTEXITCODE -eq 0) {
            Write-Status "Compilacion del proyecto - Exitosa" "Success"
            return $true
        } else {
            Write-Status "Error en la compilacion del proyecto" "Error"
            return $false
        }
    } catch {
        Write-Status "Error durante la compilacion" "Error"
        return $false
    }
}

function Test-DatabaseSchema {
    Write-Status "Verificando esquema de base de datos..." "Info"
    try {
        $testQuery = "USE scalia_db; SHOW TABLES;" | mysql -u root -p$MySQLPassword 2>$null
        if ($LASTEXITCODE -eq 0) {
            Write-Status "Base de datos scalia_db - Existe" "Success"
            $tables = @("users", "instruments", "chords", "theory_concepts")
            $missingTables = @()
            foreach ($table in $tables) {
                $tableQuery = "USE scalia_db; DESCRIBE $table;" | mysql -u root -p$MySQLPassword 2>$null
                if ($LASTEXITCODE -ne 0) {
                    $missingTables += $table
                }
            }
            if ($missingTables.Count -eq 0) {
                Write-Status "Esquema de base de datos - Completo" "Success"
                return $true
            } else {
                Write-Status "Tablas faltantes: $($missingTables -join ', ')" "Error"
                return $false
            }
        } else {
            Write-Status "Base de datos scalia_db no existe" "Error"
            return $false
        }
    } catch {
        Write-Status "Error al verificar esquema de base de datos" "Error"
        return $false
    }
}

function Test-DatabaseData {
    Write-Status "Verificando datos de ejemplo..." "Info"
    try {
        $queries = @(
            "USE scalia_db; SELECT COUNT(*) as user_count FROM users;",
            "USE scalia_db; SELECT COUNT(*) as instrument_count FROM instruments;",
            "USE scalia_db; SELECT COUNT(*) as chord_count FROM chords;",
            "USE scalia_db; SELECT COUNT(*) as concept_count FROM theory_concepts;"
        )
        $results = @()
        foreach ($query in $queries) {
            $result = $query | mysql -u root -p$MySQLPassword 2>$null
            if ($LASTEXITCODE -eq 0) {
                $results += $result
            }
        }
        if ($results.Count -eq $queries.Count) {
            Write-Status "Datos de ejemplo - Presentes" "Success"
            return $true
        } else {
            Write-Status "Datos de ejemplo incompletos" "Error"
            return $false
        }
    } catch {
        Write-Status "Error al verificar datos de ejemplo" "Error"
        return $false
    }
}

function Test-ApplicationStartup {
    Write-Status "Verificando inicio de aplicacion..." "Info"
    try {
        $mainClass = "src/main/java/com/scalia/Main.java"
        if (Test-Path $mainClass) {
            Write-Status "Clase principal - Accesible" "Success"
            $pomContent = Get-Content "pom.xml" -Raw
            if ($pomContent -match "javafx-maven-plugin") {
                Write-Status "Plugin JavaFX - Configurado" "Success"
                return $true
            } else {
                Write-Status "Plugin JavaFX no encontrado en pom.xml" "Error"
                return $false
            }
        } else {
            Write-Status "Clase principal no encontrada" "Error"
            return $false
        }
    } catch {
        Write-Status "Error al verificar inicio de aplicacion" "Error"
        return $false
    }
}

function Show-HealthReport {
    param(
        [hashtable]$Results
    )
    Write-Host @"
===============================================
           REPORTE DE SALUD
           SCALIA PROJECT
===============================================
"@ -ForegroundColor $InfoColor
    $totalChecks = $Results.Count
    $passedChecks = ($Results.Values | Where-Object { $_ -eq $true }).Count
    $failedChecks = $totalChecks - $passedChecks
    Write-Host "`nResumen de Verificaciones:" -ForegroundColor $InfoColor
    Write-Host "   Total de verificaciones: $totalChecks" -ForegroundColor $InfoColor
    Write-Host "   Exitosas: $passedChecks" -ForegroundColor $SuccessColor
    Write-Host "   Fallidas: $failedChecks" -ForegroundColor $ErrorColor
    Write-Host "`nDetalles:" -ForegroundColor $InfoColor
    foreach ($check in $Results.Keys) {
        $status = if ($Results[$check]) { "OK" } else { "ERROR" }
        $color = if ($Results[$check]) { $SuccessColor } else { $ErrorColor }
        Write-Host "   $status $check" -ForegroundColor $color
    }
    if ($failedChecks -eq 0) {
        Write-Host "`nTodo esta funcionando correctamente!" -ForegroundColor $SuccessColor
        Write-Host "   Puedes ejecutar: .\dev-setup.ps1" -ForegroundColor $InfoColor
    } else {
        Write-Host "`nSe encontraron problemas. Revisa los errores anteriores." -ForegroundColor $WarningColor
        Write-Host "   Ejecuta: .\dev-setup.ps1 para configurar el entorno" -ForegroundColor $InfoColor
    }
}

function Main {
    Write-Host @"
===============================================
           SCALIA - HEALTH CHECK
       Music Theory Desktop Application
===============================================
"@ -ForegroundColor $InfoColor
    Write-Status "Iniciando verificacion de salud del proyecto..." "Info"
    $healthResults = @{
        "Java" = Test-JavaHealth
        "Maven" = Test-MavenHealth
        "MySQL" = Test-MySQLHealth
        "Estructura del Proyecto" = Test-ProjectStructure
        "Compilacion" = Test-ProjectCompilation
        "Esquema de Base de Datos" = Test-DatabaseSchema
        "Datos de Ejemplo" = Test-DatabaseData
        "Inicio de Aplicacion" = Test-ApplicationStartup
    }
    Show-HealthReport -Results $healthResults
}

Main 