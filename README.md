# School schedule project

This project is a School Schedule Generator built in Java using MyBatis for persistence and a layered architecture with DAO, Service, and Validation layers. It generates weekly schedules for classes while applying constraints such as teacher availability, subject rules, and classroom capacities.

## Features

- Random weekly schedule generation

- Validation rules, including:

    - No gaps in class schedules

    - Classroom capacity & special requirements

    - Subject rules (e.g., PE not first lesson, max 6 lessons/day)

    - Teacher availability

- MyBatis-based persistence with SQLSessionFactory

- Modular architecture (DAO, Service, Generator, Validation layers)

## Project structure
```
school-schedule/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/solvd/school/
│   │   │       ├── Main.java
│   │   │       ├── model/
│   │   │       ├── mapper/  
│   │   │       ├── service/
│   │   │       │   ├── impl/
│   │   │       │   └── interfaces/
│   │   │       ├── generator/
│   │   │       ├── validator/
│   │   │       └── util/
│   │   └── resources/
│   │       ├── mybatis-config.xml
│   │       ├── mapper/
│   │       ├── db-schema.sql
│   │       ├── db-data.sql
│   │       ├── database.properties
│   │       └── log4j2.xml    
└── pom.xml    
```
## Technologies Used

- Java 17+

- MyBatis (ORM & SQLSessionFactory)

- Log4j2 (logging)

- Maven (build tool)

## Setup Instructions
1. Clone the repository

```
git clone https://github.com/annrybakova/school-schedule.git
cd school-schedule
```

2. Database Setup

Import the init.sql script into your MySQL/Postgres database to create tables.

Update database connection settings in resources/database.properties.

3. Build the project

```
mvn clean install
```

4. Run the application

```
mvn exec:java -Dexec.mainClass="com.solvd.school.Main"
```

## Authors

- Oleksandr Korneliuk
- Maksym Parfonov
- Anna Rybakova