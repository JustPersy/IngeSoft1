# Script para ejecutar pruebas y analisis estatico del proyecto Scalia
# Autor: Equipo Scalia
# Fecha: Julio 2025

Write-Host "=== MODULO 3: TESTING - PROYECTO SCALIA ===" -ForegroundColor Green
Write-Host ""

# Verificar que estamos en el directorio correcto
if (-not (Test-Path "pom.xml")) {
    Write-Host "Error: No se encontro pom.xml. Ejecuta este script desde el directorio del proyecto." -ForegroundColor Red
    exit 1
}

Write-Host "1. Ejecutando pruebas unitarias..." -ForegroundColor Yellow
Write-Host "==========================================" -ForegroundColor Yellow

# Ejecutar todas las pruebas
try {
    .\mvnw.cmd test
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Pruebas unitarias ejecutadas exitosamente" -ForegroundColor Green
    } else {
        Write-Host "Algunas pruebas fallaron" -ForegroundColor Red
    }
} catch {
    Write-Host "Error ejecutando pruebas: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "2. Ejecutando analisis estatico (Checkstyle)..." -ForegroundColor Yellow
Write-Host "================================================" -ForegroundColor Yellow

# Ejecutar analisis estatico
try {
    .\mvnw.cmd checkstyle:check
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Analisis estatico completado sin errores" -ForegroundColor Green
    } else {
        Write-Host "Analisis estatico completado con advertencias" -ForegroundColor Yellow
    }
} catch {
    Write-Host "Error ejecutando analisis estatico: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "3. Generando reporte detallado..." -ForegroundColor Yellow
Write-Host "=================================" -ForegroundColor Yellow

# Generar reporte detallado de Checkstyle
try {
    .\mvnw.cmd checkstyle:checkstyle
    Write-Host "Reporte generado en target/site/checkstyle.html" -ForegroundColor Green
} catch {
    Write-Host "Error generando reporte: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== RESUMEN ===" -ForegroundColor Cyan
Write-Host "Pruebas implementadas:" -ForegroundColor White
Write-Host "  - UserTest.java: 5 pruebas" -ForegroundColor White
Write-Host "  - UserDAOTest.java: 10 pruebas" -ForegroundColor White
Write-Host "  - DatabaseConnectionTest.java: 7 pruebas" -ForegroundColor White
Write-Host "  - InstrumentTest.java: 3 pruebas" -ForegroundColor White
Write-Host "  - InstrumentDAOTest.java: 1 prueba" -ForegroundColor White
Write-Host "  Total: 26 pruebas unitarias" -ForegroundColor Green

Write-Host ""
Write-Host "Herramientas utilizadas:" -ForegroundColor White
Write-Host "  - JUnit 5 (Jupiter) para pruebas unitarias" -ForegroundColor White
Write-Host "  - Checkstyle para analisis estatico" -ForegroundColor White
Write-Host "  - Maven para gestion de dependencias" -ForegroundColor White

Write-Host ""
Write-Host "Archivos generados:" -ForegroundColor White
Write-Host "  - target/surefire-reports/ (reportes de pruebas)" -ForegroundColor White
Write-Host "  - target/checkstyle-result.xml (resultados del linter)" -ForegroundColor White
Write-Host "  - target/site/checkstyle.html (reporte visual)" -ForegroundColor White

Write-Host ""
Write-Host "=== FINALIZADO ===" -ForegroundColor Green 