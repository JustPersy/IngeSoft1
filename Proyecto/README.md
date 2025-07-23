# 🎵 Scalia - Aplicación de escritorio para teoría musical e instrumentos

Scalia es una aplicación de escritorio educativa e interactiva diseñada para estudiantes, músicos autodidactas y docentes. Su objetivo es ofrecer un entorno intuitivo para explorar teoría musical, instrumentos, acordes, afinaciones y más.

---

## 🛠 Tecnologías utilizadas

- **Lenguaje:** Java
- **Framework GUI:** JavaFX
- **Base de datos:** MySQL (modelo relacional)
- **Conexión BD:** JDBC
- **Patrones:** MVC, DAO
- **Testing:** JUnit
- **IDE sugerido:** IntelliJ IDEA / NetBeans / Eclipse

---

## 📦 Estructura de módulos

### 1. 🎼 Teoría musical
- Guías interactivas de escalas, acordes e intervalos.
- Visualización gráfica de los conceptos.
- Registro y continuidad de progreso del usuario.

### 2. 🎹 Biblioteca de instrumentos
- Visualización de instrumentos por categoría.
- Detalle de cada instrumento: nombre, imagen, familia, descripción y partes.
- Instrumentos destacados usados por artistas reconocidos.
- Marcado como favorito.

### 3. 🔊 Visualizador de acordes
- Muestra visual de acordes y escalas según instrumento.
- Sonido de los acordes (según instrumento seleccionado).

### 4. 🎧 Afinador simple
- Identificación de nota mediante entrada de audio básica.
- Indicaciones de ajuste de afinación.

### 5. 🎵 Biblioteca de afinaciones
- Lista de afinaciones comunes por instrumento.
- Visualización de notas por cuerda.
- Posibilidad de guardar afinaciones preferidas.

### 6. 👤 Gestión de usuarios
- Registro e inicio de sesión.
- Historial de progreso.
- Favoritos personalizados.

---

## 📊 Base de datos

**Nombre de la base de datos:** `scalia_db`

Relaciones clave:
- `users` → `favorites`, `progress`
- `instruments` → `categories`, `instrument_parts`, `chord_visualizations`, `tuning_presets`, `artist_references`
- `categories` → múltiples instrumentos

> Se está modelando actualmente desde MySQL Workbench.

---

## ✅ Estado del proyecto

Todas las funcionalidades planeadas se encuentran implementadas y la aplicación está lista para entrega. Incluye todas las vistas, conexión a la base de datos y un conjunto de pruebas unitarias.

---

## 📁 Enlaces útiles

- [Documentación de JavaFX](https://openjfx.io/)
- [Documentación de MySQL](https://dev.mysql.com/doc/)
- [Guía JDBC](https://docs.oracle.com/javase/tutorial/jdbc/)

---

## 🧩 Reglas técnicas y restricciones

- Arquitectura **monolítica**.
- No se permiten microservicios ni servidores web externos.
- Se puede consumir APIs externas solo como complemento (no eje central).
- Aplicación completamente funcional en **modo local**.

---

## 🧠 Autores y contacto

> Andrés Ramírez — [aramirezfa@unal.edu.co](mailto:aramirezfa@unal.edu.co)
> Cristian Castillo — [crcastillo@unal.edu.co](mailto:crcastillo@unal.edu.co)
> Nathalia Chaves — [nachavesp@unal.edu.co](mailto:nachavesp@unal.edu.co)
> Juan Cristancho — [jcristanchoa@unal.edu.co](mailto:jcristanchoa@unal.edu.co)
> Proyecto académico para Ingeniería de Software I  
> Universidad Nacional de Colombia - 2025  

---

