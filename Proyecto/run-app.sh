#!/bin/bash

# Scalia - Music Theory Application
# Simple execution script

echo "🎵 Iniciando Scalia - Music Theory Application..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado. Por favor instala Java 17 o superior."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado. Por favor instala Apache Maven."
    exit 1
fi

# Navigate to project directory
cd "$(dirname "$0")"

echo "🔧 Compilando proyecto..."

# Clean and compile
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Error al compilar el proyecto."
    exit 1
fi

echo "✅ Compilación exitosa."
echo "🚀 Iniciando aplicación..."

# Run the application
mvn javafx:run

echo "👋 Scalia cerrado."
