package database;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;
import java.sql.ResultSet;
import database.tabel;
import database.AbstractDatabase;


public class DriverDatabase {

    // String url = "jdbc:mysql://localhost:3306/tralala"; // Ganti dengan URL database Anda
    String username = "root"; // Ganti dengan username database Anda
    String password = "Sitanggang123_"; // Ganti dengan password database Anda

    public static void main(String[] args) {

        //javac example\*.java -d bin
        //java -cp "bin;./libs/*" example.Driver
        try {
            tabel database = new database("jdbc:mysql://localhost:3306/tralala");
            Scanner masukan = new Scanner(System.in);
            while (masukan.hasNextLine()){
                String input = masukan.nextLine();
                if (input.equals("---")){
                    String courses = "SELECT * FROM courses";
                    Statement stmt = database.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery(courses);
                    while (rs.next()){
                        System.out.println(rs.getString("code") + "|" + rs.getString("name") + "|" + rs.getString("credit") + "|" + rs.getString("passing_grade"));
                    } 
                    //buatlah untuk student
                    String students = "SELECT * FROM students";
                    Statement stmt2 = database.getConnection().createStatement();
                    ResultSet rs2 = stmt2.executeQuery(students);
                    while (rs2.next()){
                        System.out.println(rs2.getString("id") + "|" + rs2.getString("name") + "|" + rs2.getString("year") + "|" + rs2.getString("study_program"));
                    }

                    String lecturer = "SELECT * FROM lecturer";
                    Statement stmt3 = database.getConnection().createStatement();
                    ResultSet rs3 = stmt3.executeQuery(lecturer);
                    while (rs3.next()){
                        System.out.println(rs3.getString("id") + "|" + rs3.getString("name") + "|" + rs3.getString("initial") + "|" + rs3.getString("email") + "|" + rs3.getString("study_program"));
                    }

                    String enrollment = "SELECT * FROM enrollment";
                    Statement stmt4 = database.getConnection().createStatement();
                    ResultSet rs4 = stmt4.executeQuery(enrollment);
                    while (rs4.next()){
                        System.out.println(rs4.getString("code") + "|" + rs4.getString("id") + "|" + rs4.getString("academic_year") + "|" + rs4.getString("semester") + "|" + rs4.getString("grade"));
                    }

                    String courseopening = "SELECT * FROM courseopening";
                    Statement stmt5 = database.getConnection().createStatement();
                    ResultSet rs5 = stmt5.executeQuery(courseopening);
                    while (rs5.next()){
                        System.out.println(rs5.getString("id") + "|" + rs5.getString("academic_year") + "|" + rs5.getString("semester") + "|" + rs5.getString("lecturer"));
                    }


                break;

                
            } 

            String[] tokens = input.split("#"); 
            if (tokens[0].equals("course-add")){
                database.course(connection, tokens[1], tokens[2], tokens[3], tokens[4]);
            }
            
            else if (tokens[0].equals("student-add")){
                database.students(tokens[1], tokens[2], (tokens[3]), tokens[4]);
            }
            else if (tokens[0].equals("lecturer-add")){
                database.lecturers(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]); 
            }
            else if (tokens[0].equals("enrollment-add")){
                database.enrollments(tokens[1], tokens[2], tokens[3], tokens[4], "None");
            }
            else if (tokens[0].equals("course-open")){
                database.addCourseOpening(tokens[1], tokens[2], tokens[3], tokens[4]);
            }
            else if (tokens[0].equals("enrollment-grade")) {
                database.enrollmentGrade(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
            }



        }
        database.shutdown();

        
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }
}