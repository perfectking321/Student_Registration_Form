# Student Registration System

## Introduction

The Student Registration System is a desktop application built using Java Swing that provides a comprehensive solution for managing student registrations. This application features a user-friendly graphical interface that allows users to register students, view registered student records, and manage student information efficiently.

### Key Features

- **Student Registration**: Register new students with complete information including student ID, name, email, phone, gender, course, and address
- **Data Validation**: Built-in validation for email format and phone numbers (10-digit)
- **Data Persistence**: Uses SQLite database to store student records permanently
- **View Records**: Display all registered students in a tabular format
- **Duplicate Prevention**: Prevents duplicate student IDs from being registered
- **User-Friendly Interface**: Clean and intuitive GUI with clear labels and organized layout
- **Clear Form**: Quick form reset functionality to register multiple students efficiently

## Requirements

### Software Requirements

1. **Java Development Kit (JDK)**
   - Version: JDK 8 or higher
   - Download from: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

2. **SQLite JDBC Driver**
   - Version: 3.50.3.0 (included in the `lib` folder)
   - File: `sqlite-jdbc-3.50.3.0.jar`

### Hardware Requirements

- **RAM**: Minimum 512 MB (1 GB recommended)
- **Disk Space**: At least 50 MB free space
- **Display**: Minimum resolution of 1024x768

### Operating System

- Windows (7, 8, 10, 11)
- macOS
- Linux

## Steps to Run the Program

### Method 1: Using Command Line (PowerShell/Command Prompt)

1. **Navigate to the project directory**:
   ```powershell
   cd "d:\APP PORTFOLIO\project 2\Student-Registration"
   ```

2. **Compile the Java program**:
   ```powershell
   javac -cp "lib\sqlite-jdbc-3.50.3.0.jar;." StudentRegistrationForm.java
   ```

3. **Run the program**:
   ```powershell
   java -cp "lib\sqlite-jdbc-3.50.3.0.jar;." StudentRegistrationForm
   ```

### Method 2: Using IDE (Eclipse/IntelliJ IDEA/NetBeans)

1. **Open your IDE** and create a new Java project

2. **Import the source file**:
   - Copy `StudentRegistrationForm.java` to your project's `src` folder

3. **Add the SQLite JDBC library**:
   - Right-click on your project
   - Select "Build Path" → "Configure Build Path" (Eclipse) or "Project Structure" (IntelliJ)
   - Add the `sqlite-jdbc-3.50.3.0.jar` file from the `lib` folder as an external library

4. **Run the program**:
   - Right-click on `StudentRegistrationForm.java`
   - Select "Run As" → "Java Application"

### Creating an Executable JAR (Optional)

To create a standalone executable JAR file:

1. **Create a manifest file** (`manifest.txt`):
   ```
   Main-Class: StudentRegistrationForm
   Class-Path: lib/sqlite-jdbc-3.50.3.0.jar
   ```

2. **Create the JAR**:
   ```powershell
   jar cvfm StudentRegistration.jar manifest.txt StudentRegistrationForm.class lib\sqlite-jdbc-3.50.3.0.jar
   ```

3. **Run the JAR**:
   ```powershell
   java -jar StudentRegistration.jar
   ```

## How to Use the Application

1. **Launch the application** using one of the methods described above

2. **Register a New Student**:
   - Fill in all the required fields:
     - Student ID (unique identifier)
     - Full Name
     - Email (must be in valid format)
     - Phone (10-digit number)
     - Gender (select from dropdown)
     - Course (select from dropdown)
     - Address (can be multi-line)
   - Click the **Register** button
   - A success message will appear if registration is successful

3. **Clear the Form**:
   - Click the **Clear** button to reset all fields

4. **View All Registered Students**:
   - Click the **View All** button to refresh the table with all registered students
   - The table displays: ID, Student ID, Name, Email, Phone, Gender, and Course

## Database Information

- **Database Type**: SQLite
- **Database File**: `students.db` (created automatically in the project directory)
- **Table Name**: `students`
- **Table Schema**:
  - `id` - Auto-incremented primary key
  - `student_id` - Unique student identifier
  - `name` - Student's full name
  - `email` - Student's email address
  - `phone` - Student's phone number
  - `gender` - Student's gender
  - `course` - Enrolled course
  - `address` - Student's address

## Screenshot

![Student Registration System Screenshot](screenshot.png)

*Note: Replace `screenshot.png` with an actual screenshot of the running application. To capture a screenshot:*
1. Run the application
2. Fill in some sample data
3. Press `Win + Shift + S` (Windows) or use the Snipping Tool
4. Save the screenshot as `screenshot.png` in the project directory

## Troubleshooting

### Common Issues

1. **"Class not found" error**:
   - Ensure the SQLite JDBC driver is in the `lib` folder
   - Verify the classpath includes the JAR file

2. **Database error on startup**:
   - Check if you have write permissions in the project directory
   - The `students.db` file will be created automatically

3. **Validation errors**:
   - Email must be in format: `example@domain.com`
   - Phone must be exactly 10 digits
   - All fields are required

## Project Structure

```
Student-Registration/
│
├── StudentRegistrationForm.java    # Main application source code
├── lib/
│   └── sqlite-jdbc-3.50.3.0.jar   # SQLite JDBC driver
├── students.db                     # SQLite database (created at runtime)
└── README.md                       # This file
```

## Author

Developed as part of the App Portfolio - Project 2

## License

This project is open source and available for educational purposes.

---

**Version**: 1.0  
**Last Updated**: October 2025
