// package database;

// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;

// public class StudentTranskrip extends AbstractDatabase {
//     public StudentTranskrip(String url) {
//         super(url);
//     }

//     @Override
//     protected void createTable() throws SQLException {
//         // Kode pembuatan tabel
//         String student = "CREATE TABLE Students (" +
//                 "id VARCHAR(255), " +
//                 "name VARCHAR(255), " +
//                 "year INT, " +
//                 "studyProgram VARCHAR(255), " +
//                 "gpa DECIMAL(3,2), " +
//                 "totalCredit INT, " +
//                 "PRIMARY KEY (id))";

//         String course = "CREATE TABLE Courses (" +
//                 "code VARCHAR(255), " +
//                 "credit INT, " +
//                 "PRIMARY KEY (code))";

//         String enrollment = "CREATE TABLE Enrollments (" +
//                 "course VARCHAR(255), " +
//                 "student VARCHAR(255), " +
//                 "academicYear INT, " +
//                 "semester VARCHAR(255), " +
//                 "grade VARCHAR(255), " +
//                 "previousGrade VARCHAR(255), " +
//                 "PRIMARY KEY (course, student), " +
//                 "FOREIGN KEY (course) REFERENCES Courses(code), " +
//                 "FOREIGN KEY (student) REFERENCES Students(id))";

//         Statement statement = this.getConnection().createStatement();

//         statement.execute(student);
//         statement.execute(course);
//         statement.execute(enrollment);

//         statement.close();
//     }

//     @Override
//     protected void seedTables() throws SQLException {
//         // Kode pengisian data awal
//         String[] cleanUpSQLs = {
//                 "DELETE FROM Students",
//                 "DELETE FROM Courses",
//                 "DELETE FROM Enrollments"
//         };

//         String[] studentInserts = {
//                 "INSERT INTO Students (id, name, year, studyProgram, gpa, totalCredit) VALUES ('1', 'John Doe', 1, 'Computer Science', 3.5, 30)",
//                 "INSERT INTO Students (id, name, year, studyProgram, gpa, totalCredit) VALUES ('2', 'Jane Doe', 2, 'Computer Science', 3.8, 60)",
//                 "INSERT INTO Students (id, name, year, studyProgram, gpa, totalCredit) VALUES ('3', 'Alice', 3, 'Computer Science', 3.9, 90)",
//                 "INSERT INTO Students (id, name, year, studyProgram, gpa, totalCredit) VALUES ('4', 'Bob', 4, 'Computer Science', 3.7, 120)"
//         };

//         String[] courseInserts = {
//                 "INSERT INTO Courses (code, credit) VALUES ('CS101', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS102', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS103', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS104', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS105', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS106', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS107', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS108', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS109', 3)",
//                 "INSERT INTO Courses (code, credit) VALUES ('CS110', 3)"
//         };

//         String[] enrollmentInserts = {
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS101', '1', 2020, 'Fall', 'A', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS102', '1', 2020, 'Fall', 'B', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS103', '1', 2020, 'Fall', 'C', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS104', '1', 2020, 'Fall', 'D', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS105', '1', 2020, 'Fall', 'F', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS106', '1', 2020, 'Fall', 'A', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS107', '1', 2020, 'Fall', 'B', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS108', '1', 2020, 'Fall', 'C', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS109', '1', 2020, 'Fall', 'D', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS110', '1', 2020, 'Fall', 'F', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS101', '2', 2020, 'Fall', 'A', NULL)",
//                 "INSERT INTO Enrollments (course, student, academicYear, semester, grade, previousGrade) VALUES ('CS102', '2', 2020, 'Fall', 'B', NULL)"
//         };


//         Statement statement = this.getConnection().createStatement();

//         for (String sql : cleanUpSQLs) {
//             statement.execute(sql);
//             System.out.println(sql + " executed successfully.");
//         }

//         for (String sql : studentInserts) {
//             statement.execute(sql);
//             System.out.println(sql + " executed successfully.");
//         }

//         for (String sql : courseInserts) {
//             statement.execute(sql);
//             System.out.println(sql + " executed successfully.");
//         }

//         for (String sql : enrollmentInserts) {
//             statement.execute(sql);
//             System.out.println(sql + " executed successfully.");
//         }

//         statement.close();

//         String sql = "SELECT * FROM Students";

//         PreparedStatement preparedStatement = this.getConnection().prepareStatement(sql);

//         ResultSet resultSet = preparedStatement.executeQuery();
//         while (resultSet.next()) {
//             System.out.println("Student ID: " + resultSet.getString("id") + ", Name: " + resultSet.getString("name"));
//         }
//         resultSet.close();
//         preparedStatement.close();
//     }
// }
