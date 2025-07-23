-- Active: 1752108425606@@127.0.0.1@3306@scalia_db
-- Scalia Music Theory Application Database Schema
-- Created by: Juan Manuel Cristancho Álvarez

-- Create the database
CREATE DATABASE IF NOT EXISTS scalia_db;
USE scalia_db;


-- Users table for authentication
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Categories for instruments
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    icon VARCHAR(50)
);

-- Instruments table
CREATE TABLE instruments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INT,
    family VARCHAR(50),
    description TEXT,
    image_path VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- User favorites
CREATE TABLE favorites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    instrument_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (instrument_id) REFERENCES instruments(id),
    UNIQUE KEY unique_favorite (user_id, instrument_id)
);

-- User progress tracking
CREATE TABLE user_progress (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    module VARCHAR(50) NOT NULL, -- 'theory', 'chords', 'tuner', etc.
    progress_percentage DECIMAL(5,2) DEFAULT 0.00,
    last_accessed TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Chords table
CREATE TABLE chords (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    notes VARCHAR(255) NOT NULL,
    structure VARCHAR(100),
    instrument_id INT,
    chord_type VARCHAR(50),
    FOREIGN KEY (instrument_id) REFERENCES instruments(id)
);

-- Tuning presets table
CREATE TABLE tuning_presets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    instrument_id INT,
    tuning_notes VARCHAR(255) NOT NULL,
    description TEXT,
    is_standard BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (instrument_id) REFERENCES instruments(id)
);

-- Theory content table
CREATE TABLE theory_content (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    difficulty VARCHAR(20) DEFAULT 'Principiante',
    examples TEXT,
    order_index INT DEFAULT 1
);

-- Sample data for testing
INSERT INTO categories (name, description, icon) VALUES
('String Instruments', 'Instruments that produce sound through vibrating strings', '🎸'),
('Wind Instruments', 'Instruments that produce sound through air vibration', '🎷'),
('Percussion Instruments', 'Instruments that produce sound through striking', '🥁'),
('Keyboard Instruments', 'Instruments with keys that produce sound', '🎹');

INSERT INTO instruments (name, category_id, family, description) VALUES
('Guitar', 1, 'String', 'A fretted musical instrument with six strings'),
('Piano', 4, 'Keyboard', 'A musical instrument played using a keyboard'),
('Violin', 1, 'String', 'A bowed string instrument with four strings'),
('Flute', 2, 'Wind', 'A woodwind instrument that produces sound from the flow of air'),
('Drums', 3, 'Percussion', 'A percussion instrument consisting of a membrane stretched over a frame'),
('Bass Guitar', 1, 'String', 'A four-stringed instrument tuned an octave lower than a guitar'),
('Ukulele', 1, 'String', 'A small four-stringed instrument from Hawaii');

-- Insert chords data
INSERT INTO chords (name, notes, structure, instrument_id, chord_type) VALUES
('C Major', 'C-E-G', '1-3-5', 1, 'Major'),
('D Minor', 'D-F-A', '1-♭3-5', 1, 'Minor'),
('G Major', 'G-B-D', '1-3-5', 1, 'Major'),
('A Minor', 'A-C-E', '1-♭3-5', 1, 'Minor'),
('E Major', 'E-G#-B', '1-3-5', 1, 'Major'),
('F Major', 'F-A-C', '1-3-5', 1, 'Major'),
('C Major', 'C-E-G', '1-3-5', 2, 'Major'),
('F Major', 'F-A-C', '1-3-5', 2, 'Major'),
('G Major', 'G-B-D', '1-3-5', 2, 'Major');

-- Insert tuning presets
INSERT INTO tuning_presets (name, instrument_id, tuning_notes, description, is_standard) VALUES
('Standard Tuning', 1, 'E-A-D-G-B-E', 'Standard guitar tuning', TRUE),
('Drop D', 1, 'D-A-D-G-B-E', 'Popular alternative tuning for rock and metal', FALSE),
('Open G', 1, 'D-G-D-G-B-D', 'Open tuning that forms a G major chord', FALSE),
('DADGAD', 1, 'D-A-D-G-A-D', 'Popular tuning in Celtic and folk music', FALSE),
('Standard Bass', 6, 'E-A-D-G', 'Standard bass guitar tuning', TRUE),
('Drop D Bass', 6, 'D-A-D-G', 'Bass drop D tuning', FALSE),
('Standard Ukulele', 7, 'G-C-E-A', 'Standard ukulele tuning', TRUE),
('Low G Ukulele', 7, 'G-C-E-A', 'Ukulele tuning with low G string', FALSE);

-- Insert theory content
INSERT INTO theory_content (title, content, category, difficulty, examples, order_index) VALUES
('Escala Mayor', 'La escala mayor es una secuencia de siete notas que sigue el patrón: Tono-Tono-Semitono-Tono-Tono-Tono-Semitono.\n\nCaracterísticas:\n- Es la base de la música occidental\n- Tiene un sonido alegre y brillante\n- Se usa en muchos géneros musicales', 'Escalas', 'Principiante', 'Do Mayor: Do-Re-Mi-Fa-Sol-La-Si-Do\nSol Mayor: Sol-La-Si-Do-Re-Mi-Fa#-Sol', 1),

('Escala Menor Natural', 'La escala menor natural sigue el patrón: Tono-Semitono-Tono-Tono-Semitono-Tono-Tono.\n\nCaracterísticas:\n- Sonido más melancólico que la escala mayor\n- Se forma bajando el 3er, 6to y 7mo grado de la escala mayor\n- Muy usada en música clásica y popular', 'Escalas', 'Principiante', 'La menor: La-Si-Do-Re-Mi-Fa-Sol-La\nMi menor: Mi-Fa#-Sol-La-Si-Do-Re-Mi', 2),

('Acordes Triadas', 'Un acorde triada está formado por tres notas: la fundamental, la tercera y la quinta.\n\nTipos de triadas:\n- Mayor: 3era mayor y 5ta justa\n- Menor: 3era menor y 5ta justa\n- Disminuido: 3era menor y 5ta disminuida\n- Aumentado: 3era mayor y 5ta aumentada', 'Acordes', 'Principiante', 'Do Mayor: Do-Mi-Sol\nLa menor: La-Do-Mi\nSi disminuido: Si-Re-Fa', 1),

('Intervalos Básicos', 'Un intervalo es la distancia entre dos notas musicales.\n\nTipos de intervalos:\n- Unísono (0 semitonos)\n- Segunda menor (1 semitono)\n- Segunda mayor (2 semitonos)\n- Tercera menor (3 semitonos)\n- Tercera mayor (4 semitonos)', 'Intervalos', 'Principiante', 'Do a Re = Segunda mayor\nDo a Mi = Tercera mayor\nDo a Fa = Cuarta justa', 1),

('Círculo de Quintas', 'El círculo de quintas es una representación visual de las relaciones entre las 12 tonalidades de la música occidental.\n\nCaracterísticas:\n- Muestra las armaduras de cada tonalidad\n- Facilita la modulación entre tonalidades\n- Herramienta fundamental para la composición', 'Armonía', 'Intermedio', 'Do Mayor (sin alteraciones) → Sol Mayor (1 sostenido) → Re Mayor (2 sostenidos)', 1),

('Ritmo y Compás', 'El ritmo es la organización del tiempo en la música, mientras que el compás es la división regular del tiempo.\n\nCompases comunes:\n- 4/4: Cuatro tiempos por compás\n- 3/4: Tres tiempos por compás (vals)\n- 2/4: Dos tiempos por compás (marcha)', 'Ritmo', 'Principiante', 'Compás 4/4: 1-2-3-4, 1-2-3-4\nCompás 3/4: 1-2-3, 1-2-3 (vals)', 1);

-- Insert a test user (password: 'test123' - in real app, this should be hashed)
INSERT INTO users (username, email, password, first_name, last_name) VALUES
('testuser', 'test@example.com', 'test123', 'Test', 'User');

select * from users;