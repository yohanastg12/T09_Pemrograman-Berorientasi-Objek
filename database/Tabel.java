package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import database.Tabel;
import database.AbstractDatabase;
import database.DriverDatabase;

public class Tabel extends AbstractDatabase {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tralala"; // Ganti dengan URL database Anda
        String username = "root"; // Ganti dengan username database Anda
        String password = "Sitanggang123_"; // Ganti dengan password database Anda

        Connection connection = null;
        Statement statement = null;

        try {
            // Menghubungkan ke database
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected");

            // Membuat pernyataan SQL
            statement = connection.createStatement();


            // Membuat tabel students
            String createStudentsTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
            "id VARCHAR(20) PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL," +
            "year VARCHAR(4) NOT NULL," +
            "study_program VARCHAR(100) NOT NULL)";
            statement.executeUpdate(createStudentsTableSQL);
            System.out.println("Table 'students' created successfully");

            
            String deleteStudentsSQL = "DELETE FROM students";
            statement.executeUpdate(deleteStudentsSQL);
            System.out.println("Data deleted from 'students' table");

            // // Menambahkan data ke tabel students
            // String insertStudentSQL = "INSERT INTO students (id, name, year, study_program) VALUES " +
            //         "('12S22001', 'John Doe', '2024', 'Computer Science')," +
            //         "('12S20002', 'Jane Smith', '2023', 'Information Systems')";
            // statement.executeUpdate(insertStudentSQL);
            // System.out.println("Data inserted into 'students' table");

            // Membuat tabel courses
            String createCoursesTableSQL = "CREATE TABLE IF NOT EXISTS courses (" +
            "code VARCHAR(20) PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL," +
            "credit INT NOT NULL," +
            "passing_grade VARCHAR(2) NOT NULL)";
            statement.executeUpdate(createCoursesTableSQL);
            System.out.println("Table 'courses' created successfully");

            
            String deleteCoursesSQL = "DELETE FROM courses";
            statement.executeUpdate(deleteCoursesSQL);
            System.out.println("Data deleted from 'courses' table");

            // // Menambahkan data ke tabel courses
            // String insertCourseSQL = "INSERT INTO courses (code, name, credit, passing_grade) VALUES " +
            //         "('CS101', 'Introduction to Computer Science', 3, 'C')," +
            //         "('IS201', 'Database Systems', 3, 'C')";
            // statement.executeUpdate(insertCourseSQL);
            // System.out.println("Data inserted into 'courses' table");

            //membuat tabel enrollment
            String createEnrollmentTableSQL = "CREATE TABLE IF NOT EXISTS enrollment (" +
            "code VARCHAR(20) NOT NULL," +
            "id VARCHAR(20) NOT NULL," +
            "academic_year VARCHAR(10) NOT NULL," +
            "semester VARCHAR(10) NOT NULL," +
            "grade VARCHAR(10) DEFAULT 'None'," +
            "previous_grade VARCHAR(10) DEFAULT 'None'," +
            "PRIMARY KEY (code, id, academic_year, semester))";
            statement.executeUpdate(createEnrollmentTableSQL);
            System.out.println("Table 'enrollment' created successfully");

            
            String deleteEnrollmentSQL = "DELETE FROM enrollment";
            statement.executeUpdate(deleteEnrollmentSQL);
            System.out.println("Data deleted from 'enrollments' table");

            // // Menambahkan data ke tabel enrollment
            // String insertEnrollmentSQL = "INSERT INTO enrollment (code, id, academic_year, semester, grade, previous_grade) VALUES " +
            //         "('CS101', '12S20001', '2024/2025', 'Fall', 'B+', 'None')," +
            //         "('IS201', '12S20002', '2023/2024', 'Spring', 'A', 'B')";
            // statement.executeUpdate(insertEnrollmentSQL);
            // System.out.println("Data inserted into 'enrollment' table");

            //membuat tabel lecturer
            String createLecturerTableSQL = "CREATE TABLE IF NOT EXISTS lecturer (" +
            "id VARCHAR(20) PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL," +
            "initial VARCHAR(20) NOT NULL," +
            "email VARCHAR(100) NOT NULL," +
            "study_program VARCHAR(100) NOT NULL)";
            statement.executeUpdate(createLecturerTableSQL);
            System.out.println("Table 'lecturer' created successfully");

            
            String deleteLecturersSQL = "DELETE FROM lecturers";
            statement.executeUpdate(deleteLecturersSQL);
            System.out.println("Data deleted from 'lecturers' table");

            // // Menambahkan data ke tabel lecturer
            // String insertLecturerSQL = "INSERT INTO lecturer (id, name, initial, email, study_program) VALUES " +
            //         "('L001', 'Dr. Smith', 'S', 'smith@example.com', 'Computer Science')," +
            //         "('L002', 'Prof. Johnson', 'J', 'johnson@example.com', 'Information Systems')";
            // statement.executeUpdate(insertLecturerSQL);
            // System.out.println("Data inserted into 'lecturer' table");

            //membuat tabel courseopening
            String createCourseOpeningTableSQL = "CREATE TABLE IF NOT EXISTS CourseOpening (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "academic_year VARCHAR(10) NOT NULL," +
            "semester VARCHAR(10) NOT NULL)";
            statement.executeUpdate(createCourseOpeningTableSQL);
            System.out.println("Table 'course_opening' created successfully");

            
            String deleteCourseOpeningSQL = "DELETE FROM CourseOpening";
            statement.executeUpdate(deleteCourseOpeningSQL);
            System.out.println("Data deleted from 'CourseOpening' table");

            // // Menambahkan data ke tabel course_opening
            // String insertCourseOpeningSQL = "INSERT INTO course_opening (academic_year, semester) VALUES " +
            //         "('2024/2025', 'Fall')," +
            //         "('2023/2024', 'Spring')";
            // statement.executeUpdate(insertCourseOpeningSQL);
            // System.out.println("Data inserted into 'course_opening' table");
            
            
                

        } catch (SQLException e) {
            System.out.println("Database connection error");
            e.printStackTrace();

            
        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //buatlah method untuk insert value
    public void course(String code, String name, String credit, String passingGrade) {
        String insertCourseSQL = "INSERT INTO courses (code, name, credit, passing_grade) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertCourseSQL);
            statement.setString(1, code);
            statement.setString(2, name);
            statement.setString(3, credit);
            statement.setString(4, passingGrade);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error inserting course: " + e.getMessage());
        }
    }
    
    
    //buatlah method students untuk insert value
    public void students(String id, String name, String year, String studyProgram) {
        String insertStudentSQL = "INSERT INTO students (id, name, year, study_program) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertStudentSQL);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, year);
            statement.setString(4, studyProgram);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error inserting student: " + e.getMessage());
        }
    }

    //buatlah method lecturers untuk insert value
    public void lecturers(String id, String name, String initial, String email, String studyProgram) {
        String insertLecturerSQL = "INSERT INTO lecturers (id, name, initial, email, study_program) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertLecturerSQL);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, initial);
            statement.setString(4, email);
            statement.setString(5, studyProgram);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error inserting lecturer: " + e.getMessage());
        }
    }

    //buatlah method enrollments untuk insert value
    public void enrollments(Connection connection, String code, String id, String academicYear, String semester, String grade, String previousGrade) {
        String insertEnrollmentSQL = "INSERT INTO enrollment (code, id, academic_year, semester, grade, previous_grade) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertEnrollmentSQL);
            statement.setString(1, code);
            statement.setString(2, id);
            statement.setString(3, academicYear);
            statement.setString(4, semester);
            statement.setString(5, grade);
            statement.setString(6, previousGrade);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error inserting enrollment: " + e.getMessage());
        }
    }

}
