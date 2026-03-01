CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

-- ----------------------------
-- Doctors Table
-- ----------------------------
CREATE TABLE doctors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL
);

-- Insert sample doctors
INSERT INTO doctors (name, specialization) VALUES
('Dr. Sharma', 'Cardiologist'),
('Dr. Khan', 'Neurologist'),
('Dr. Mehta', 'Orthopedic');

-- ----------------------------
-- Patients Table
-- ----------------------------
CREATE TABLE patients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL
);

-- ----------------------------
-- Appointments Table
-- ----------------------------
CREATE TABLE appointments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);