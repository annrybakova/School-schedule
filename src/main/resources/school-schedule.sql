-- Database creation
DROP DATABASE IF EXISTS school_schedule;
CREATE DATABASE school_schedule;
USE school_schedule;

-- 1. Classes
CREATE TABLE classes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL UNIQUE
);

-- 2. Subjects (17 real school subjects)
CREATE TABLE subjects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    requires_special_room BOOLEAN DEFAULT FALSE,
    has_student_groups BOOLEAN DEFAULT FALSE
);

-- 3. Classrooms
CREATE TABLE classrooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    is_special BOOLEAN DEFAULT FALSE,
    capacity INT DEFAULT 30
);

-- 4. Teachers
CREATE TABLE teachers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    subject_id INT NOT NULL,
    max_lessons_per_day INT DEFAULT 6,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- 5. Student Groups (4 groups as required)
CREATE TABLE student_groups (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    class_id INT NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    UNIQUE KEY unique_group_class (name, class_id)
);

-- 6. Students with group assignment
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    class_id INT NOT NULL,
    group_id INT NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES student_groups(id) ON DELETE CASCADE
);

-- 7. Class-Subjects connection
CREATE TABLE class_subjects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_id INT NOT NULL,
    subject_id INT NOT NULL,
    lessons_per_week INT NOT NULL,
    uses_groups BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    UNIQUE KEY unique_class_subject (class_id, subject_id)
);

-- 8. Special classrooms
CREATE TABLE special_classrooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    classroom_id INT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (classroom_id) REFERENCES classrooms(id) ON DELETE CASCADE,
    UNIQUE KEY unique_subject_classroom (subject_id, classroom_id) -- унікальна пара
);

-- 9. Subject constraints
CREATE TABLE subject_constraints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL UNIQUE,
    not_first_lesson BOOLEAN DEFAULT FALSE,
    not_last_lesson BOOLEAN DEFAULT FALSE,
    preferred_lesson INT NULL,
    max_lessons_per_day INT DEFAULT 2,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- 10. Lessons schedule with group support
CREATE TABLE lessons (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_id INT NOT NULL,
    subject_id INT NOT NULL,
    teacher_id INT NOT NULL,
    classroom_id INT NOT NULL,
    group_id INT NULL, -- NULL for whole class, group_id for group lessons
    day_of_week INT NOT NULL CHECK (day_of_week BETWEEN 1 AND 5),
    lesson_number INT NOT NULL CHECK (lesson_number BETWEEN 1 AND 8),
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    FOREIGN KEY (classroom_id) REFERENCES classrooms(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES student_groups(id) ON DELETE CASCADE,
    UNIQUE KEY unique_class_time (class_id, day_of_week, lesson_number),
    UNIQUE KEY unique_teacher_time (teacher_id, day_of_week, lesson_number),
    UNIQUE KEY unique_classroom_time (classroom_id, day_of_week, lesson_number),
    UNIQUE KEY unique_group_time (group_id, day_of_week, lesson_number)
);

-- 11. Teacher availability
CREATE TABLE teacher_availability (
    id INT PRIMARY KEY AUTO_INCREMENT,
    teacher_id INT NOT NULL,
    day_of_week INT NOT NULL CHECK (day_of_week BETWEEN 1 AND 5),
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    UNIQUE KEY unique_teacher_day (teacher_id, day_of_week)
);

-- 12. Genetic algorithm parameters
CREATE TABLE genetic_algorithm_params (
    id INT PRIMARY KEY AUTO_INCREMENT,
    max_generations INT DEFAULT 1000,
    allowed_gaps INT DEFAULT 1,
    population_size INT DEFAULT 100,
    mutation_rate FLOAT DEFAULT 0.1,
    crossover_rate FLOAT DEFAULT 0.8,
    description VARCHAR(255) NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- 13. Generation log (with link to algorithm parameters)
CREATE TABLE schedule_generation_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    generation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('SUCCESS', 'FAILED') NOT NULL,
    generations_executed INT DEFAULT 0,
    best_fitness_score FLOAT DEFAULT 0.0,
    algorithm_params_id INT NULL,
    message TEXT,
    FOREIGN KEY (algorithm_params_id) REFERENCES genetic_algorithm_params(id) ON DELETE SET NULL
);

-- 14. Generated schedules connection
CREATE TABLE generated_schedules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    generation_id INT NOT NULL,
    lesson_id INT NOT NULL,
    FOREIGN KEY (generation_id) REFERENCES schedule_generation_log(id) ON DELETE CASCADE,
    FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE,
    UNIQUE KEY unique_generation_lesson (generation_id, lesson_id)
);

-- Insert data
INSERT INTO classes (name) VALUES
('10a'),
('10b'),
('10c');

-- 17 school subjects
INSERT INTO subjects (name, requires_special_room, has_student_groups) VALUES
('Mathematics', FALSE, TRUE),
('Physics', TRUE, FALSE),
('Chemistry', TRUE, FALSE),
('Physical Education', TRUE, TRUE),
('Ukrainian Literature', FALSE, FALSE),
('English Language', FALSE, TRUE),
('History', FALSE, FALSE),
('Biology', FALSE, TRUE),
('Geography', FALSE, FALSE),
('Computer Science', TRUE, TRUE),
('Art', FALSE, TRUE),
('Music', FALSE, TRUE),
('Foreign Language', FALSE, TRUE),
('Economics', FALSE, FALSE),
('Social Studies', FALSE, FALSE),
('Technology', TRUE, TRUE),
('Health Education', FALSE, FALSE);

INSERT INTO classrooms (room_number, is_special, capacity) VALUES
('101', FALSE, 30), ('102', FALSE, 30), ('103', FALSE, 30), ('104', FALSE, 30),
('201', FALSE, 30), ('202', FALSE, 30), ('203', FALSE, 30), ('204', FALSE, 30),
('Lab-1', TRUE, 20),   -- Physics lab
('Lab-2', TRUE, 20),   -- Chemistry lab
('Comp-1', TRUE, 15),  -- Computer lab
('Comp-2', TRUE, 15),  -- Computer lab  
('Tech-1', TRUE, 15),  -- Technology room
('Gym', TRUE, 40),     -- PE hall
('Art-Room', TRUE, 20),-- Art room
('Music-Room', TRUE, 20); -- Music room

INSERT INTO teachers (first_name, last_name, subject_id, max_lessons_per_day) VALUES
('Ivan', 'Petrenko', 1, 6),     -- Mathematics
('Olena', 'Kovalenko', 2, 4),   -- Physics
('Mykola', 'Shevchenko', 3, 4), -- Chemistry
('Svitlana', 'Bondarenko', 4, 5), -- PE
('Tetiana', 'Tkachenko', 5, 5), -- Ukrainian Literature
('Andrii', 'Kravchenko', 6, 6), -- English
('Natalia', 'Oliinyk', 7, 4),   -- History
('Yurii', 'Savchenko', 8, 4),   -- Biology
('Olexandr', 'Melnyk', 9, 4),   -- Geography
('Maria', 'Ivanova', 10, 5),    -- Computer Science
('Kateryna', 'Sydorenko', 11, 4), -- Art
('Pavlo', 'Zadorozhnyi', 12, 4), -- Music
('Anna', 'Polishchuk', 13, 5),  -- Foreign Language
('Viktor', 'Lysenko', 14, 4),   -- Economics
('Iryna', 'Kravets', 15, 4),    -- Social Studies
('Serhiy', 'Tkachuk', 16, 4),   -- Technology
('Olha', 'Shevchuk', 17, 3);    -- Health Education

-- Create 4 groups for each class
INSERT INTO student_groups (name, class_id) VALUES
-- 10a groups
('10a-Group-1', 1), ('10a-Group-2', 1), ('10a-Group-3', 1), ('10a-Group-4', 1),
-- 10b groups  
('10b-Group-1', 2), ('10b-Group-2', 2), ('10b-Group-3', 2), ('10b-Group-4', 2),
-- 10c groups
('10c-Group-1', 3), ('10c-Group-2', 3), ('10c-Group-3', 3), ('10c-Group-4', 3);

-- Insert students with group assignments (30 per class, 10 per group)
INSERT INTO students (first_name, last_name, class_id, group_id) VALUES 
-- 10a students (divided into 4 groups)
('Oleksiy', 'Melnyk', 1, 1), ('Sophia', 'Kravchenko', 1, 1), ('Dmytro', 'Boyko', 1, 1),
('Anastasia', 'Shevchuk', 1, 2), ('Yaroslav', 'Koval', 1, 2), ('Victoria', 'Polishchuk', 1, 2),
('Maxym', 'Lysenko', 1, 3), ('Alina', 'Savchenko', 1, 3), ('Bohdan', 'Tkachuk', 1, 3),
('Kateryna', 'Oliynyk', 1, 4), ('Ivan', 'Zakharchuk', 1, 4), ('Maria', 'Ponomarenko', 1, 4),
-- 10b students
('Andriy', 'Bilyk', 2, 5), ('Yuliia', 'Shcherbak', 2, 5), ('Serhiy', 'Hryhorchuk', 2, 5),
('Oksana', 'Symonenko', 2, 6), ('Vitaliy', 'Rudenko', 2, 6), ('Anna', 'Fedorenko', 2, 6),
('Pavlo', 'Mazur', 2, 7), ('Daria', 'Yushchenko', 2, 7), ('Nazar', 'Tkachenko', 2, 7),
('Yevhenia', 'Lytvynenko', 2, 8), ('Artem', 'Kostenko', 2, 8), ('Valeriia', 'Semenenko', 2, 8),
-- 10c students
('Vladyslav', 'Panchenko', 3, 9), ('Olena', 'Vlasenko', 3, 9), ('Mykyta', 'Moroz', 3, 9),
('Sofia', 'Zhuk', 3, 10), ('Ihor', 'Kravtsov', 3, 10), ('Anastasiia', 'Kovalchuk', 3, 10),
('Bohdan', 'Tkachenko', 3, 11), ('Yana', 'Lysenko', 3, 11), ('Dmytro', 'Savchuk', 3, 11),
('Kateryna', 'Ivanova', 3, 12), ('Maxym', 'Petrenko', 3, 12), ('Sophia', 'Kovalenko', 3, 12);

INSERT INTO class_subjects (class_id, subject_id, lessons_per_week, uses_groups) VALUES
-- Class 10a (with Mathematics)
(1, 1, 5, TRUE), (1, 2, 3, FALSE), (1, 3, 3, FALSE), (1, 4, 2, TRUE), 
(1, 5, 4, FALSE), (1, 6, 3, TRUE), (1, 7, 2, FALSE), (1, 8, 2, TRUE),
(1, 9, 2, FALSE), (1, 10, 2, TRUE), (1, 11, 1, TRUE), (1, 12, 1, TRUE),
(1, 13, 3, TRUE), (1, 14, 1, FALSE), (1, 15, 1, FALSE), (1, 16, 2, TRUE),
(1, 17, 1, FALSE),
-- Class 10b (NO Mathematics)
(2, 2, 4, FALSE), (2, 3, 3, FALSE), (2, 4, 2, TRUE), (2, 5, 4, FALSE),
(2, 6, 4, TRUE), (2, 7, 3, FALSE), (2, 8, 3, TRUE), (2, 9, 2, FALSE),
(2, 10, 2, TRUE), (2, 11, 1, TRUE), (2, 12, 1, TRUE), (2, 13, 3, TRUE),
(2, 14, 2, FALSE), (2, 15, 2, FALSE), (2, 16, 2, TRUE), (2, 17, 1, FALSE),
-- Class 10c (with Mathematics)
(3, 1, 4, TRUE), (3, 2, 3, FALSE), (3, 3, 3, FALSE), (3, 4, 2, TRUE),
(3, 5, 3, FALSE), (3, 6, 3, TRUE), (3, 7, 2, FALSE), (3, 8, 2, TRUE),
(3, 9, 2, FALSE), (3, 10, 2, TRUE), (3, 11, 1, TRUE), (3, 12, 1, TRUE),
(3, 13, 3, TRUE), (3, 14, 1, FALSE), (3, 15, 1, FALSE), (3, 16, 2, TRUE),
(3, 17, 1, FALSE);

INSERT INTO special_classrooms (subject_id, classroom_id) VALUES
(2, 9),  -- Physics -> Lab-1
(3, 10), -- Chemistry -> Lab-2
(4, 14), -- PE -> Gym
(10, 11), -- Computer Science -> Comp-1
(10, 12), -- Computer Science -> Comp-2
(16, 13), -- Technology -> Tech-1
(11, 15), -- Art -> Art-Room
(12, 16); -- Music -> Music-Room

INSERT INTO subject_constraints (subject_id, not_first_lesson, not_last_lesson, preferred_lesson, max_lessons_per_day) VALUES
(4, TRUE, FALSE, 6, 1),  -- PE not first, preferred last, max 1 per day
(2, FALSE, FALSE, 3, 2), -- Physics preferred middle
(3, FALSE, FALSE, 4, 2), -- Chemistry preferred middle
(10, FALSE, FALSE, 5, 2), -- Computer Science preferred later
(16, FALSE, FALSE, 4, 2); -- Technology preferred middle

INSERT INTO teacher_availability (teacher_id, day_of_week, is_available) VALUES
-- All teachers available all weekdays (1-5 = Monday-Friday)
(1, 1, TRUE), (1, 2, TRUE), (1, 3, TRUE), (1, 4, TRUE), (1, 5, TRUE),
(2, 1, TRUE), (2, 2, TRUE), (2, 3, TRUE), (2, 4, TRUE), (2, 5, TRUE),
(3, 1, TRUE), (3, 2, TRUE), (3, 3, TRUE), (3, 4, TRUE), (3, 5, TRUE),
(4, 1, TRUE), (4, 2, TRUE), (4, 3, TRUE), (4, 4, TRUE), (4, 5, TRUE),
(5, 1, TRUE), (5, 2, TRUE), (5, 3, TRUE), (5, 4, TRUE), (5, 5, TRUE),
(6, 1, TRUE), (6, 2, TRUE), (6, 3, TRUE), (6, 4, TRUE), (6, 5, TRUE),
(7, 1, TRUE), (7, 2, TRUE), (7, 3, TRUE), (7, 4, TRUE), (7, 5, TRUE),
(8, 1, TRUE), (8, 2, TRUE), (8, 3, TRUE), (8, 4, TRUE), (8, 5, TRUE),
(9, 1, TRUE), (9, 2, TRUE), (9, 3, TRUE), (9, 4, TRUE), (9, 5, TRUE),
(10, 1, TRUE), (10, 2, TRUE), (10, 3, TRUE), (10, 4, TRUE), (10, 5, TRUE),
(11, 1, TRUE), (11, 2, TRUE), (11, 3, TRUE), (11, 4, TRUE), (11, 5, TRUE),
(12, 1, TRUE), (12, 2, TRUE), (12, 3, TRUE), (12, 4, TRUE), (12, 5, TRUE),
(13, 1, TRUE), (13, 2, TRUE), (13, 3, TRUE), (13, 4, TRUE), (13, 5, TRUE),
(14, 1, TRUE), (14, 2, TRUE), (14, 3, TRUE), (14, 4, TRUE), (14, 5, TRUE),
(15, 1, TRUE), (15, 2, TRUE), (15, 3, TRUE), (15, 4, TRUE), (15, 5, TRUE),
(16, 1, TRUE), (16, 2, TRUE), (16, 3, TRUE), (16, 4, TRUE), (16, 5, TRUE),
(17, 1, TRUE), (17, 2, TRUE), (17, 3, TRUE), (17, 4, TRUE), (17, 5, TRUE);

-- Genetic algorithm parameters
INSERT INTO genetic_algorithm_params 
(max_generations, allowed_gaps, population_size, mutation_rate, crossover_rate, description, is_active) VALUES
(1000, 1, 100, 0.1, 0.8, 'Default genetic algorithm parameters for schedule generation', TRUE);

SELECT 'Database school_schedule created successfully with algorithm parameters link!' AS status;