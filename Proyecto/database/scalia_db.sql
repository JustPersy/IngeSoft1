-- Scalia Music Theory Application Database Schema
-- Created by: Juan Manuel Cristancho Álvarez

-- Create the database
CREATE DATABASE IF NOT EXISTS scalia_db;
USE scalia_db;

-- Users table for authentication
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- Will store hashed passwords
    email VARCHAR(100) UNIQUE,
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
('Drums', 3, 'Percussion', 'A percussion instrument consisting of a membrane stretched over a frame');

-- Insert a test user (password: 'test123' - in real app, this should be hashed)
INSERT INTO users (username, password, email) VALUES
('testuser', 'test123', 'test@example.com'); 