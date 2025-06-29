-- Create database
CREATE DATABASE IF NOT EXISTS quiz_app;
USE quiz_app;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    email VARCHAR(100) UNIQUE
);

-- Create questions table
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT,
    option1 VARCHAR(255),
    option2 VARCHAR(255),
    option3 VARCHAR(255),
    option4 VARCHAR(255),
    correct_answer VARCHAR(255)
);

-- Create scores table
CREATE TABLE IF NOT EXISTS scores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    score INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert sample user
INSERT INTO users (name, username, password, email) VALUES
('Demo User', 'demo', 'demo123', 'demo@example.com');

-- Insert sample questions
INSERT INTO questions (question_text, option1, option2, option3, option4, correct_answer) VALUES
('What is the capital of India?', 'Mumbai', 'Delhi', 'Kolkata', 'Chennai', 'Delhi'),
('2 + 2 = ?', '3', '4', '5', '6', '4'),
('Sun rises in the?', 'East', 'West', 'North', 'South', 'East'),
('H2O is the chemical formula of?', 'Salt', 'Oxygen', 'Hydrogen', 'Water', 'Water'),
('What color is the sky?', 'Green', 'Yellow', 'Blue', 'Red', 'Blue');
