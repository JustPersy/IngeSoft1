-- Scalia Database Schema
-- Music Theory Desktop Application

-- Create database
CREATE DATABASE IF NOT EXISTS scalia_db;
USE scalia_db;

-- START: Drop tables if they exist (Orden inverso para manejar dependencias de FOREIGN KEY)
DROP TABLE IF EXISTS user_progress;
DROP TABLE IF EXISTS tunings;
DROP TABLE IF EXISTS chords;
DROP TABLE IF EXISTS theory_concepts;
DROP TABLE IF EXISTS instruments;
-- END: Drop tables

-- Tabla de categorías de instrumentos
CREATE TABLE IF NOT EXISTS instrument_categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Modificar tabla de instrumentos para agregar la relación con categoría
DROP TABLE IF EXISTS instruments;
CREATE TABLE IF NOT EXISTS instruments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    tuning_standard VARCHAR(50),
    category_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES instrument_categories(id) ON DELETE SET NULL
);

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Tunings table
CREATE TABLE IF NOT EXISTS tunings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    instrument_id INT,
    notes JSON NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (instrument_id) REFERENCES instruments(id) ON DELETE SET NULL
);

-- Chords table
CREATE TABLE IF NOT EXISTS chords (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    root_note VARCHAR(10) NOT NULL,
    chord_type VARCHAR(50) NOT NULL,
    notes JSON NOT NULL,
    fingering JSON,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Theory concepts table
CREATE TABLE IF NOT EXISTS theory_concepts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    examples JSON,
    difficulty_level ENUM('beginner', 'intermediate', 'advanced') DEFAULT 'beginner',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- User progress table
CREATE TABLE IF NOT EXISTS user_progress (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    concept_id INT NOT NULL,
    status ENUM('not_started', 'in_progress', 'completed') DEFAULT 'not_started',
    score INT DEFAULT 0,
    completed_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (concept_id) REFERENCES theory_concepts(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_concept (user_id, concept_id)
);

-- Insertar categorías populares
INSERT INTO instrument_categories (name) VALUES
('Cuerdas frotadas'),
('Cuerdas pulsadas'),
('Viento madera'),
('Viento metal'),
('Percusión'),
('Teclado'),
('Electrónicos');

-- Insertar instrumentos con categoría
INSERT INTO instruments (name, type, description, tuning_standard, category_id) VALUES
('Guitarra Acústica', 'String', 'Guitarra acústica de 6 cuerdas', 'EADGBE', 2),
('Guitarra Eléctrica', 'String', 'Guitarra eléctrica de 6 cuerdas', 'EADGBE', 2),
('Piano', 'Keyboard', 'Piano acústico de 88 teclas', NULL, 6),
('Bajo', 'String', 'Bajo eléctrico de 4 cuerdas', 'EADG', 2),
('Guitarra', 'String', 'Guitarra clásica de 6 cuerdas', 'EADGBE', 2);

INSERT INTO theory_concepts (name, category, description, difficulty_level) VALUES
('Notas Musicales', 'Fundamentos', 'Las siete notas básicas de la música: Do, Re, Mi, Fa, Sol, La, Si', 'beginner'),
('Escalas Mayores', 'Escalas', 'Construcción y uso de escalas mayores', 'beginner'),
('Acordes Mayores', 'Acordes', 'Formación y uso de acordes mayores', 'beginner'),
('Intervalos', 'Fundamentos', 'Distancia entre dos notas musicales', 'intermediate'),
('Progresiones de Acordes', 'Armonía', 'Secuencias comunes de acordes en la música', 'intermediate'),
('Modos Griegos', 'Escalas', 'Los siete modos de la escala mayor', 'advanced');

-- Insert sample tunings
INSERT INTO tunings (name, instrument_id, notes, description) VALUES
('Estándar', 1, '["E", "A", "D", "G", "B", "E"]', 'Afinación estándar de guitarra'),
('Drop D', 1, '["D", "A", "D", "G", "B", "E"]', 'Afinación Drop D para guitarra'),
('Estándar', 2, '["E", "A", "D", "G", "B", "E"]', 'Afinación estándar de guitarra eléctrica'),
('Estándar', 4, '["E", "A", "D", "G"]', 'Afinación estándar de bajo');

-- Insert sample chords
INSERT INTO chords (name, root_note, chord_type, notes, fingering) VALUES
('Do Mayor', 'C', 'Major', '["C", "E", "G"]', '{"positions": [{"strings": [1,2,3], "frets": [0,0,0]}]}'),
('Re Mayor', 'D', 'Major', '["D", "F#", "A"]', '{"positions": [{"strings": [1,2,3], "frets": [2,3,2]}]}'),
('Mi Mayor', 'E', 'Major', '["E", "G#", "B"]', '{"positions": [{"strings": [1,2,3], "frets": [0,0,0]}]}'),
('Fa Mayor', 'F', 'Major', '["F", "A", "C"]', '{"positions": [{"strings": [1,2,3], "frets": [1,1,1]}]}'),
('Sol Mayor', 'G', 'Major', '["G", "B", "D"]', '{"positions": [{"strings": [1,2,3], "frets": [3,3,3]}]}'),
('La Mayor', 'A', 'Major', '["A", "C#", "E"]', '{"positions": [{"strings": [1,2,3], "frets": [0,2,2]}]}'),
('Si Mayor', 'B', 'Major', '["B", "D#", "F#"]', '{"positions": [{"strings": [1,2,3], "frets": [2,4,4]}]}');