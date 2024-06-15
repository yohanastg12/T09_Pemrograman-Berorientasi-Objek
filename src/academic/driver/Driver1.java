package academic.driver;

/**
 * @author 12S22050-YOHANA SITANGGANG
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;

import java.util.Collections;
import academic.model.Course;
import academic.model.Enrollment;
import academic.model.Student;
import academic.model.Lecturer;
import academic.model.CourseOpening;

public class Driver1 {

    final static Scanner scanner = new Scanner(System.in);

    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Enrollment> enrollments = new ArrayList<>();
    private static List<CourseOpening> courseOpen = new ArrayList<>();

    private static List<String> inputList = new ArrayList<>();
    private static List<Lecturer> lecturers = new ArrayList<>();

    public static void main(String[] _args) {
        do {
            String input = scanner.nextLine();
            if (input.equals("---")) {
                break;
            }
            inputList.add(input);
        } while (true);

        for (String input : inputList) {
            String[] command = input.split("#");

            switch (command[0]) {
                case "lecturer-add":
                    if (command.length == 6) {
                        Lecturer lecturer = new Lecturer(command[1], command[2], command[3], command[4], command[5]);
                        if (!isDuplicateLecturer(lecturer, lecturers)) {
                            lecturers.add(lecturer);
                        }
                    }
                    break;
                case "course-add":
                    if (command.length >= 5) {
                        String[] lecturerInitials = command[4].split(",");
                        List<Lecturer> courseLecturers = new ArrayList<>();
                        List<String> lecturerNames = new ArrayList<>();
                        for (String lecturerInitial : lecturerInitials) {
                            for (Lecturer lecturer : lecturers) {
                                if (lecturer.getInitial().equals(lecturerInitial)) {
                                    courseLecturers.add(lecturer);
                                    lecturerNames.add(lecturer.getInitial() + " (" + lecturer.getEmail() + ")");
                                    break;
                                }
                            }
                        }
                        Course course = new Course(command[1], command[2], Integer.parseInt(command[3]), command[4]);
                        if (!isDuplicateCourse(course, courses)) {
                            courses.add(course);
                        }
                    }
                    break;
                case "student-add":
                    if (command.length == 5) {
                        Student student = new Student(command[1], command[2], command[3], command[4]);
                        if (!isDuplicateStudent(student, students)) {
                            students.add(student);
                        }
                    }
                    break;
                case "enrollment-add":
                    if (command.length >= 5) {
                        Enrollment enrollment = new Enrollment(command[1], command[2], command[3], command[4]);
                        if (!enrollments.contains(enrollment)) {
                            enrollments.add(enrollment);
                        }
                    }
                    break;
                case "enrollment-grade":
                    if (command.length == 6) {
                        String courseCode = command[1];
                        String studentId = command[2];
                        String academicYear = command[3];
                        String semester = command[4];
                        String grade = command[5];

                        Enrollment targetEnrollment = findEnrollment(courseCode, studentId, academicYear, semester,
                                enrollments);

                        if (targetEnrollment != null) {
                            targetEnrollment.setGrade(grade);
                        }
                    }
                    break;

                case "student-details":
                    if (command.length == 2) {
                        String studentId = command[1];
                        Student targetStudent = findStudentById(studentId, students);
                        if (targetStudent != null) {
                            double gpa = calculateGPA(studentId, enrollments);
                            int totalCredit = calculateTotalCredit(studentId, enrollments);
                            System.out.println(targetStudent.getId() + "|" + targetStudent.getName() + "|"
                                    + targetStudent.getYear() + "|" +
                                    targetStudent.getStudyProgram() + "|" + String.format("%.2f", gpa) + "|"
                                    + totalCredit);
                        }
                    }
                    break;
                case "enrollment-remedial":
                    if (command.length == 6) {
                        String courseCode = command[1];
                        String studentId = command[2];
                        String academicYear = command[3];
                        String semester = command[4];
                        String grade = command[5];

                        for (int i = 0; i < enrollments.size(); i++) {
                            if (enrollments.get(i).getCode().equals(courseCode) &&
                                    enrollments.get(i).getId().equals(studentId) &&
                                    enrollments.get(i).getAcademicYear().equals(academicYear) &&
                                    enrollments.get(i).getSemester().equals(semester)) {
                                if (enrollments.get(i).getPreviousGrade().equals("None")) {
                                    enrollments.get(i).setPreviousGrade(grade);
                                }
                            }
                        }

                    }
                    break;

               case "course-open":
                    if (command.length == 5) {
                        String courseCode = command[1];
                        String academicYear = command[2];
                        String semester = command[3];
                        String lecturerInitial = command[4];

                        CourseOpening courseOpening = new CourseOpening(academicYear, semester, Collections.singletonList(lecturerInitial));
                        Course course = Course.findCourseByCode(courseCode, courses);

                        if (course != null) {
                            course.addCourseOpening(courseOpening);
                        
                        } else {
                            System.out.println("Course not found: " + courseCode);
                        }
                    }
                    break;

                case "course-history":
                        if (command.length == 2) {
                            String courseCode = command[1];
                            Course course = Course.findCourseByCode(courseCode, courses);

                           if (course != null) {
                                List<CourseOpening> sortedCourseOpenings = new ArrayList<>(course.getCourseOpenings());
                           Collections.sort(sortedCourseOpenings, new Comparator<CourseOpening>() {
                                @Override
                                public int compare(CourseOpening o1, CourseOpening o2) {
                                    // Sort by semester first, ODD before EVEN
                                    if (o1.getSemester().equals("odd") && o2.getSemester().equals("even")) {
                                        return -1; // ODD before EVEN
                                    } else if (o1.getSemester().equals("even") && o2.getSemester().equals("odd")) {
                                        return 1; // EVEN after ODD
                                    }
                                    
                                    // If semesters are the same or not ODD or EVEN, then sort by academic year
                                    return o1.getAcademicYear().compareTo(o2.getAcademicYear());
                                }
                            });

                                Collections.sort(enrollments, Comparator.comparing(Enrollment::getAcademicYear));


                                for (CourseOpening courseOpening : sortedCourseOpenings) {
                                    System.out.println(course.getCode() + "|" + course.getName() + "|" + course.getCredit() + "|" + course.getPassingGrade() +
                                        "|" + courseOpening.getAcademicYear() + "|" + courseOpening.getSemester() + "|" + courseOpening.getLecturers().get(0) +
                                        " (" + lecturers.stream().filter(l -> l.getInitial().equals(courseOpening.getLecturers().get(0))).findFirst().get().getEmail() + ")");


                                    for (Enrollment enrollment : enrollments) {
                                        if (enrollment.getCode().equals(courseCode) &&
                                                enrollment.getAcademicYear().equals(courseOpening.getAcademicYear()) &&
                                                enrollment.getSemester().equals(courseOpening.getSemester())) {

                                                    if (enrollment.getPreviousGrade().equals("None")) {
                                                        System.out.println(enrollment.getCode() + "|" + enrollment.getId() + "|" +
                                                        enrollment.getAcademicYear() + "|" + enrollment.getSemester() + "|" + enrollment.getGrade());
                                                    } else {
                                            System.out.println(enrollment.getCode() + "|" + enrollment.getId() + "|" +
                                                    enrollment.getAcademicYear() + "|" + enrollment.getSemester() + "|" + enrollment.getGrade() + "(" + enrollment.getPreviousGrade()+ ")");
                                        }
                                    }
                                }
                            } 
                           }
        
                        }
                        break;

                case "student-transcript":
                    
                    if (command.length == 2) {
                        String studentId = command[1];
                        Student targetStudent = findStudentById(studentId, students);
                        if (targetStudent != null) {
                            if (command.length == 2) {
                                    if (targetStudent != null) {
                                        double gpa = calculateGPA(studentId, enrollments);
                                        int totalCredit = calculateTotalCredit(studentId, enrollments);
                                        System.out.println(targetStudent.getId() + "|" + targetStudent.getName() + "|"
                                                + targetStudent.getYear() + "|" +
                                                targetStudent.getStudyProgram() + "|" + String.format("%.2f", gpa) + "|"
                                                + totalCredit);
                                    }
                                }

                            Map<String, Enrollment> enrollmentsMap = new HashMap<>();
                            Set<String> enrolledCourses = new HashSet<>(); // Set to keep track of enrolled courses

                            for (Enrollment enrollment : enrollments) {
                                if (enrollment.getId().equals(studentId)) {
                                    enrollmentsMap.put(enrollment.getCode() + "|" + enrollment.getAcademicYear() + "|" +
                                            enrollment.getSemester(), enrollment) ;
                                    enrolledCourses.add(enrollment.getCode());
                                }
                            }

                            List<Enrollment> sortedEnrollments = new ArrayList<>(enrollmentsMap.values());
                            sortedEnrollments.sort((o1, o2) -> {
                                // Sort by academic year first, descending
                                int yearComparison = o2.getAcademicYear().compareTo(o1.getAcademicYear());
                                if (yearComparison != 0) {
                                    return yearComparison;
                                }

                                // Then sort by semester, ODD before EVEN
                                if (o1.getSemester().equals("odd") && o2.getSemester().equals("even")) {
                                    return -1; // ODD before EVEN
                                } else if (o1.getSemester().equals("even") && o2.getSemester().equals("odd")) {
                                    return 1; // EVEN after ODD
                                }

                                return 0;
                            });

                            Set<String> latestEnrollments = new HashSet<>(); // Set to keep track of latest enrollments
Collections.sort(sortedEnrollments, Comparator.comparing(Enrollment::getCode));
for (Enrollment enrollment : sortedEnrollments) {
    String courseKey = enrollment.getCode();
    if (!latestEnrollments.contains(courseKey)) {
        latestEnrollments.add(courseKey);
        if (!enrollment.getGrade().equals("None")) {
            double cumulativeGradePoints = 0;
            int totalCredits = 0;
            for (Enrollment e : enrollments) {
                if (e.getId().equals(studentId) && !e.getGrade().equals("None") && e.getCode().equals(enrollment.getCode())) {
                    cumulativeGradePoints += getGradePoint(e.getGrade()) * getCourseCredit(e.getCode());
                    totalCredits += getCourseCredit(e.getCode());
                }
            }
            double gpa = totalCredits > 0 ? cumulativeGradePoints / totalCredits : 0.0;

            if (enrollment.getPreviousGrade() == null || enrollment.getPreviousGrade().equals("None")) {
                System.out.println(enrollment.getCode() + "|" + enrollment.getId() + "|" + enrollment.getAcademicYear() + "|" +
                        enrollment.getSemester() + "|" +  enrollment.getGrade());
            } else {
                System.out.println(enrollment.getCode() + "|" + enrollment.getId() + "|" + enrollment.getAcademicYear() + "|" +
                        enrollment.getSemester() + "|" +  enrollment.getGrade() + "(" + enrollment.getPreviousGrade() + ")");
            }
        }
    }
}  
                  }
                    }
                    break;
                    default:
                    break;
            }
        }

        for (Lecturer lecturer : lecturers) {
            System.out.println(lecturer);
        }

        for (Course course : courses) {
            System.out.println(course);
        }

        for (Student student : students) {
            System.out.println(student);
        }

       // Mengurutkan enrollments berdasarkan semester (odd sebelum even)
Collections.sort(enrollments, new Comparator<Enrollment>() {
    @Override
    public int compare(Enrollment o1, Enrollment o2) {
        // Sort by semester first, ODD before EVEN
        if (o1.getSemester().equals("odd") && o2.getSemester().equals("even")) {
            return -1; // ODD before EVEN
        } else if (o1.getSemester().equals("even") && o2.getSemester().equals("odd")) {
            return 1; // EVEN after ODD
        }

        // If semesters are the same or not ODD or EVEN, then sort by academic year
        return o1.getAcademicYear().compareTo(o2.getAcademicYear());
    }
});

// Mencetak output
for (Enrollment enrollment : enrollments) {
    System.out.println(enrollment);
}


    }

    private static boolean isDuplicateCourse(Course course, List<Course> courses) {
        return courses.stream().anyMatch(c -> c.getCode().equals(course.getCode()));
    }

    private static boolean isDuplicateStudent(Student student, List<Student> students) {
        return students.stream().anyMatch(s -> s.getId().equals(student.getId()));
    }

    private static boolean isDuplicateLecturer(Lecturer lecturer, List<Lecturer> lecturers) {
        return lecturers.stream().anyMatch(e -> e.getName().equals(lecturer.getName()));
    }

    private static Enrollment findEnrollment(String courseCode, String studentId, String academicYear, String semester,
            List<Enrollment> enrollments) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCode().equals(courseCode) &&
                    enrollment.getId().equals(studentId) &&
                    enrollment.getAcademicYear().equals(academicYear) &&
                    enrollment.getSemester().equals(semester)) {
                return enrollment;
            }
        }
        return null;
    }

  private static double calculateGPA(String studentId, List<Enrollment> enrollments) {
    Map<String, String> GradeAkhir = new HashMap<>();

    String tahun = "";
    String matkul = "";
    int sks = 0, totalsks = 0;
    double totalnilai = 0;
    double ipk = 0;
    for(int i = 0; i < enrollments.size(); i++){
        for(int j = i+1; j < enrollments.size(); j++){
            if(enrollments.get(i).getId().equals(studentId) && enrollments.get(j).getId().equals(studentId) && enrollments.get(i).getCode().equals(enrollments.get(j).getCode())){
                tahun = enrollments.get(i).getAcademicYear();
                matkul = enrollments.get(i).getCode();
                break;
            } 
        }
    }
        for(Enrollment E : enrollments){
        if(E.getAcademicYear().equals(tahun) && E.getCode().equals(matkul) && E.getId().equals(studentId)){
            continue;
        } else {
            if(E.getId().equals(studentId)){

            
            String grade = E.getGrade();
            String code = E.getCode();
            for(Course C : courses){
                if(C.getCode().equals(code)){
                    sks = C.getCredit();
                    totalsks = totalsks + sks;
                }

            }
            switch(grade){
                case "A":
                    totalnilai = totalnilai + 4.0 * sks;
                    break;
                case "AB":
                    totalnilai = totalnilai + 3.5 * sks;
                    break;
                case "B":
                    totalnilai = totalnilai + 3.0 * sks;
                    break;
                case "BC":
                    totalnilai = totalnilai + 2.5 * sks;
                    break;
                case "C":
                    totalnilai = totalnilai + 2.0 * sks;
                    break;
                case "D":
                    totalnilai = totalnilai + 1.0 * sks;
                    break;
                case "E":
                    totalnilai = totalnilai + 0.0 * sks;
                    break;
            }
            }
        }
    }
    ipk = totalnilai / totalsks;
    return ipk;

}

    private static int calculateTotalCredit(String studentId, List<Enrollment> enrollments) {
        Map<String, String> GradeAkhir = new HashMap<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equals(studentId) && !enrollment.getGrade().equals("None")) {
                GradeAkhir.put(enrollment.getCode(), enrollment.getGrade());
            }
        }

        int totalCredit = 0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equals(studentId) && !enrollment.getGrade().equals("None")) {
                if (GradeAkhir.containsKey(enrollment.getCode())) {
                    totalCredit += getCourseCredit(enrollment.getCode());
                    GradeAkhir.remove(enrollment.getCode());
                }
            }
        }

        return totalCredit;
    }

    private static double getGradePoint(String grade) {
        if (grade.equals("A")) {
            return 4.0;
        } else if (grade.equals("AB")) {
            return 3.5;
        } else if (grade.equals("B")) {
            return 3.0;
        } else if (grade.equals("BC")) {
            return 2.5;
        } else if (grade.equals("C")) {
            return 2.0;
        } else if (grade.equals("D")) {
            return 1.0;
        } else if (grade.equals("E")) {
            return 0.0;
        } else {
            return 0.0;
        }
    }

    private static int getCourseCredit(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course.getCredit();
            }
        }
        return 0;
    }

    public static Student findStudentById(String studentId, List<Student> students) {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }
}
