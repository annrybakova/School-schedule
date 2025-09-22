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

-- 5. Students
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    class_id INT NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);

-- 6. Class-Subjects connection
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

-- 7. Special classrooms
CREATE TABLE special_classrooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL,
    classroom_id INT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (classroom_id) REFERENCES classrooms(id) ON DELETE CASCADE,
    UNIQUE KEY unique_subject_classroom (subject_id, classroom_id)
);

-- 8. Subject constraints
CREATE TABLE subject_constraints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_id INT NOT NULL UNIQUE,
    not_first_lesson BOOLEAN DEFAULT FALSE,
    not_last_lesson BOOLEAN DEFAULT FALSE,
    preferred_lesson INT NULL,
    max_lessons_per_day INT DEFAULT 2,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- 9. Lessons schedule with group support
CREATE TABLE lessons (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_id INT NOT NULL,
    subject_id INT NOT NULL,
    teacher_id INT NOT NULL,
    classroom_id INT NOT NULL,
    day_of_week INT NOT NULL CHECK (day_of_week BETWEEN 1 AND 5),
    lesson_number INT NOT NULL CHECK (lesson_number BETWEEN 1 AND 8),
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    FOREIGN KEY (classroom_id) REFERENCES classrooms(id) ON DELETE CASCADE,
    UNIQUE KEY unique_class_time (class_id, day_of_week, lesson_number),
    UNIQUE KEY unique_teacher_time (teacher_id, day_of_week, lesson_number),
    UNIQUE KEY unique_classroom_time (classroom_id, day_of_week, lesson_number),
    UNIQUE KEY unique_group_time (day_of_week, lesson_number)
);

-- 10. Teacher availability
CREATE TABLE teacher_availability (
    id INT PRIMARY KEY AUTO_INCREMENT,
    teacher_id INT NOT NULL,
    day_of_week INT NOT NULL CHECK (day_of_week BETWEEN 1 AND 5),
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    UNIQUE KEY unique_teacher_day (teacher_id, day_of_week)
);

-- 11. Genetic algorithm parameters
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

-- 12. Generation log (with link to algorithm parameters)
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

-- 13. Generated schedules connection
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
('10c'),
('10d');

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

INSERT INTO students (first_name, last_name, class_id) VALUES
-- Class 1 (10a) - 20 students
('Ivan', 'Petrenko', 1),
('Maria', 'Kovalenko', 1),
('Oleksiy', 'Shevchenko', 1),
('Sophia', 'Bondarenko', 1),
('Dmytro', 'Tkachenko', 1),
('Anna', 'Kravchenko', 1),
('Andriy', 'Oliynyk', 1),
('Yulia', 'Savchenko', 1),
('Mykola', 'Polishchuk', 1),
('Natalia', 'Lysenko', 1),
('Serhiy', 'Melnyk', 1),
('Oksana', 'Koval', 1),
('Pavlo', 'Zakharchuk', 1),
('Tetiana', 'Ponomarenko', 1),
('Viktor', 'Shcherbak', 1),
('Iryna', 'Hryhorchuk', 1),
('Bohdan', 'Symonenko', 1),
('Olha', 'Rudenko', 1),
('Yuriy', 'Fedorenko', 1),
('Kateryna', 'Mazur', 1),

-- Class 2 (10b) - 20 students
('Vladyslav', 'Yushchenko', 2),
('Anastasia', 'Tkachenko', 2),
('Artem', 'Lytvynenko', 2),
('Valeriia', 'Kostenko', 2),
('Maksym', 'Panchenko', 2),
('Yevhenia', 'Vlasenko', 2),
('Nazar', 'Moroz', 2),
('Sofia', 'Zhuk', 2),
('Ihor', 'Kravtsov', 2),
('Anastasiia', 'Kovalchuk', 2),
('Roman', 'Tkachenko', 2),
('Yana', 'Lysenko', 2),
('Oleh', 'Savchuk', 2),
('Alina', 'Ivanova', 2),
('Vitaliy', 'Petrenko', 2),
('Daria', 'Kovalenko', 2),
('Stanislav', 'Shevchenko', 2),
('Veronika', 'Bondarenko', 2),
('Mykhailo', 'Tkachenko', 2),
('Mariana', 'Kravchenko', 2),

-- Class 3 (10c) - 20 students
('Yaroslav', 'Oliynyk', 3),
('Khrystyna', 'Savchenko', 3),
('Oleksandr', 'Polishchuk', 3),
('Viktoriia', 'Lysenko', 3),
('Ivan', 'Melnyk', 3),
('Solomiia', 'Koval', 3),
('Petro', 'Zakharchuk', 3),
('Zoriana', 'Ponomarenko', 3),
('Vadym', 'Shcherbak', 3),
('Nina', 'Hryhorchuk', 3),
('Ruslan', 'Symonenko', 3),
('Liliia', 'Rudenko', 3),
('Taras', 'Fedorenko', 3),
('Olena', 'Mazur', 3),
('Volodymyr', 'Yushchenko', 3),
('Halyna', 'Tkachenko', 3),
('Sergiy', 'Lytvynenko', 3),
('Tamara', 'Kostenko', 3),
('Leonid', 'Panchenko', 3),
('Lydia', 'Vlasenko', 3),

-- Class 4 (10d) - 20 students
('Anatoliy', 'Moroz', 4),
('Ivanna', 'Zhuk', 4),
('Vasyl', 'Kravtsov', 4),
('Oksana', 'Kovalchuk', 4),
('Borys', 'Tkachenko', 4),
('Nadia', 'Lysenko', 4),
('Zenoviy', 'Savchuk', 4),
('Raisa', 'Ivanova', 4),
('Myroslav', 'Petrenko', 4),
('Larysa', 'Kovalenko', 4),
('Yevhen', 'Shevchenko', 4),
('Lesya', 'Bondarenko', 4),
('Ihor', 'Tkachenko', 4),
('Svitlana', 'Kravchenko', 4),
('Roman', 'Oliynyk', 4),
('Iryna', 'Savchenko', 4),
('Orest', 'Polishchuk', 4),
('Nina', 'Lysenko', 4),
('Bohdan', 'Melnyk', 4),
('Olena', 'Koval', 4);

INSERT INTO class_subjects (class_id, subject_id, lessons_per_week, uses_groups) VALUES
-- Class 10a (with Mathematics)
(1, 1, 5, FALSE), (1, 2, 3, FALSE), (1, 3, 3, FALSE), (1, 4, 2, FALSE),
(1, 5, 4, FALSE), (1, 6, 3, FALSE), (1, 7, 2, FALSE), (1, 8, 2, FALSE),
(1, 9, 2, FALSE), (1, 10, 2, FALSE), (1, 11, 1, FALSE), (1, 12, 1, FALSE),
(1, 13, 3, FALSE), (1, 14, 1, FALSE), (1, 15, 1, FALSE), (1, 16, 2, FALSE),
(1, 17, 1, FALSE),
-- Class 10b (NO Mathematics)
(2, 2, 4, FALSE), (2, 3, 3, FALSE), (2, 4, 2, FALSE), (2, 5, 4, FALSE),
(2, 6, 4, FALSE), (2, 7, 3, FALSE), (2, 8, 3, FALSE), (2, 9, 2, FALSE),
(2, 10, 2, FALSE), (2, 11, 1, FALSE), (2, 12, 1, FALSE), (2, 13, 3, FALSE),
(2, 14, 2, FALSE), (2, 15, 2, FALSE), (2, 16, 2, FALSE), (2, 17, 1, FALSE),
-- Class 10c (with Mathematics)
(3, 1, 4, FALSE), (3, 2, 3, FALSE), (3, 3, 3, FALSE), (3, 4, 2, FALSE),
(3, 5, 3, FALSE), (3, 6, 3, FALSE), (3, 7, 2, FALSE), (3, 8, 2, FALSE),
(3, 9, 2, FALSE), (3, 10, 2, FALSE), (3, 11, 1, FALSE), (3, 12, 1, FALSE),
(3, 13, 3, FALSE), (3, 14, 1, FALSE), (3, 15, 1, FALSE), (3, 16, 2, FALSE),
(3, 17, 1, FALSE),
-- Class 10d (with Mathematics)
(4, 1, 4, FALSE), (4, 2, 3, FALSE), (4, 3, 3, FALSE), (4, 4, 2, FALSE),
(4, 5, 3, FALSE), (4, 6, 3, FALSE), (4, 7, 2, FALSE), (4, 8, 2, FALSE),
(4, 9, 2, FALSE), (4, 10, 2, FALSE), (4, 11, 1, FALSE), (4, 12, 1, FALSE),
(4, 13, 3, FALSE), (4, 14, 1, FALSE), (4, 15, 1, FALSE), (4, 16, 2, FALSE),
(4, 17, 1, FALSE);

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