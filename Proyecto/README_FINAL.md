# 🎵 Scalia - Music Theory Application

## Ejecutable Final y Entregable

Este es el estado final y entregable del proyecto **Scalia**, una aplicación de escritorio para teoría musical e instrumentos desarrollada con JavaFX y MySQL.

## 🎯 Funcionalidades Implementadas

### ✅ Módulos Completos

1. **🎼 Teoría Musical**
   - Guías interactivas de escalas, acordes e intervalos
   - Navegación por categorías y dificultad
   - Contenido educativo estructurado

2. **🎹 Biblioteca de Instrumentos**
   - Visualización de instrumentos por categoría
   - Detalles completos de cada instrumento
   - Navegación intuitiva y búsqueda

3. **🔊 Visualizador de Acordes**
   - Representación visual de acordes por instrumento
   - Soporte para guitarra y piano
   - Información detallada de acordes

4. **🎧 Afinador**
   - Simulación de afinación por instrumento
   - Múltiples presets de afinación
   - Interfaz visual intuitiva

5. **🎵 Biblioteca de Afinaciones**
   - Catálogo completo de afinaciones
   - Filtros por instrumento y tipo
   - Integración con el afinador

6. **👤 Sistema de Usuarios**
   - Registro e inicio de sesión
   - Autenticación completa
   - Gestión de sesiones

### 🛠 Arquitectura y Tecnologías

- **Patrón MVC** - Separación clara de responsabilidades
- **DAO Pattern** - Acceso estructurado a datos
- **JavaFX** - Interfaz gráfica moderna y responsiva
- **MySQL** - Base de datos relacional robusta
- **JUnit 5** - Suite completa de pruebas unitarias
- **Maven** - Gestión de dependencias y build

## 🚀 Ejecución Rápida

### Opción 1: Script automatizado
```bash
./run-app.sh
```

### Opción 2: Maven directo
```bash
mvn clean compile javafx:run
```

### Opción 3: Desde IDE
- Abrir en IntelliJ IDEA/Eclipse/NetBeans
- Ejecutar clase `com.scalia.Main`

## 📊 Base de Datos

### Configuración Automática
El proyecto incluye:
- ✅ Script SQL completo (`database/scalia_db.sql`)
- ✅ Datos de prueba incluidos
- ✅ Configuración de conexión en `DatabaseConnection.java`

### Credenciales por Defecto
- **Host:** localhost:3306
- **Base de datos:** scalia_db
- **Usuario:** root
- **Contraseña:** 1234

*Para cambiar la contraseña, edita `DatabaseConnection.java`*

## 🧪 Testing

### Ejecutar Todas las Pruebas
```bash
mvn test
```

### Cobertura de Pruebas
- ✅ Modelos de datos
- ✅ Validaciones de negocio
- ✅ Acceso a datos (DAO)
- ✅ Servicios
- ✅ Controladores principales

## 📁 Estructura del Proyecto

```
Proyecto/
├── src/main/java/com/scalia/
│   ├── controllers/          # Controladores JavaFX
│   ├── dao/                  # Acceso a datos
│   ├── models/               # Modelos de datos
│   ├── services/             # Lógica de negocio
│   ├── utils/                # Utilidades
│   └── Main.java             # Punto de entrada
├── src/main/resources/
│   ├── fxml/                 # Archivos de vista
│   ├── css/                  # Estilos
│   ├── assets/               # Recursos gráficos
│   └── fonts/                # Tipografías
├── src/test/java/            # Pruebas unitarias
├── database/
│   └── scalia_db.sql         # Schema y datos
├── pom.xml                   # Configuración Maven
└── run-app.sh               # Script de ejecución
```

## 🎨 Diseño Visual

### Tema Oscuro Profesional
- 🎨 Esquema de colores verde neón sobre fondo oscuro
- 🖼 Interfaz moderna y limpia
- 📱 Componentes responsivos
- ⚡ Navegación fluida entre módulos

### Tipografía Personalizada
- Fuente principal: Florence Sans
- Fallbacks: Segoe UI, Arial
- Iconos emoji integrados

## 🔧 Configuración de Desarrollo

### Requisitos
- ☕ Java 17+
- 🔨 Apache Maven 3.6+
- 🐬 MySQL 8.0+
- 💻 IDE con soporte JavaFX (recomendado: IntelliJ IDEA)

### Setup Inicial
1. Clonar/extraer el proyecto
2. Configurar MySQL y ejecutar `database/scalia_db.sql`
3. Ajustar credenciales en `DatabaseConnection.java` si es necesario
4. Ejecutar `mvn clean compile javafx:run`

## 📝 Características Técnicas

### Validaciones Robustas
- ✅ Validación de emails
- ✅ Validación de contraseñas
- ✅ Validación de nombres de usuario
- ✅ Validación de datos de instrumentos

### Manejo de Errores
- ✅ Try-catch comprehensivos
- ✅ Mensajes de error amigables
- ✅ Logging de errores para debugging
- ✅ Recuperación graceful de fallos

### Seguridad
- ✅ Validación de entrada de usuario
- ✅ Prepared statements para SQL
- ✅ Manejo seguro de conexiones
- ✅ Validación de sesiones

## 📚 Documentación

### Javadoc
Todos los métodos públicos están documentados con Javadoc estándar, incluyendo:
- Descripción de funcionalidad
- Parámetros de entrada
- Valores de retorno
- Excepciones posibles

### Comentarios de Código
- Comentarios explicativos en lógica compleja
- TODO markers para futuras mejoras
- Documentación de decisiones de diseño

## 🎓 Casos de Uso Implementados

### CU01: Gestión de Usuarios
- ✅ Registro de nuevos usuarios
- ✅ Inicio de sesión
- ✅ Validación de credenciales
- ✅ Gestión de sesiones

### CU02: Exploración de Instrumentos
- ✅ Navegación por categorías
- ✅ Visualización de detalles
- ✅ Búsqueda y filtrado

### CU03: Aprendizaje de Teoría
- ✅ Acceso a contenido educativo
- ✅ Navegación por temas
- ✅ Progresión por dificultad

### CU04: Uso del Afinador
- ✅ Selección de instrumento
- ✅ Elección de afinación
- ✅ Simulación de proceso de afinación

## 🏆 Estado del Proyecto

### ✅ COMPLETADO - LISTO PARA ENTREGA

- **Funcionalidad:** 100% de los módulos requeridos implementados
- **Calidad:** Código limpio con patrones de diseño apropiados
- **Testing:** Suite completa de pruebas unitarias
- **Documentación:** Completa y actualizada
- **UI/UX:** Interfaz pulida y profesional
- **Base de Datos:** Schema completo con datos de prueba
- **Configuración:** Scripts automatizados para setup y ejecución

### 🚀 Listo para Demostración

El proyecto está completamente funcional y listo para:
- ✅ Demostración en vivo
- ✅ Evaluación académica
- ✅ Revisión de código
- ✅ Testing por usuarios finales

---

## 👥 Equipo de Desarrollo

- **Andrés Ramírez** - aramirezfa@unal.edu.co
- **Cristian Castillo** - crcastillo@unal.edu.co  
- **Nathalia Chaves** - nachavesp@unal.edu.co
- **Juan Cristancho** - jcristanchoa@unal.edu.co

**Universidad Nacional de Colombia - 2025**  
**Ingeniería de Software I**

---

*Proyecto académico desarrollado con fines educativos*
