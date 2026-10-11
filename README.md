# Online Examination System

A console-based online examination system built with **Java, JDBC and MySQL**.

## Features
- Student registration and login
- Admin login
- Admin: add, view, update, delete subjects; add and view exams and questions
- Student: take timed multiple-choice exams
- Automatic marking: correct, incorrect, unanswered, marks and percentage
- Results saved in MySQL; students can view previous results

## Technologies
Java 21, JDBC, MySQL 8, Maven, IntelliJ IDEA

## Project Structure
- `util`: database connection
- `model`: data classes (Student, Admin, Subject, Exam, Question, Result)
- `dao`: SQL queries using JDBC
- `Main`: console menus

## How to Run
1. Install Java 21 and MySQL 8.
2. Run `database.sql` in MySQL Workbench to create the database and tables.
3. Open `src/main/java/com/onlineexam/util/DBConnection.java` and replace `YOUR_PASSWORD_HERE` with your MySQL root password.
4. Open the project in IntelliJ, let Maven download dependencies, and run `Main.java`.

## Default Admin Login
- Username: `admin`
- Password: `admin123`

