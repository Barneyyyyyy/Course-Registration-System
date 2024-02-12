package courseregistration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Course> loadCoursesFromCSV(String filename) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Assumes CSV format matches Course constructor
                if (values.length == 6) { // Ensure there are enough data points
                    Course course = new Course(values[0], values[1], Integer.parseInt(values[2]), values[3], Integer.parseInt(values[4]), values[5]);
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin", "admin123", "Admin", "Administrator");
        new Student("student", "student123", "Student", "Learner");

        try {
            List<Course> courses = loadCoursesFromCSV("/Course-Registration-System/courseregistration/MyUniversityCourses.csv");
            courses.forEach(admin::createCourse); // Assuming Admin has a method to handle adding courses
        } catch (IOException e) {
            System.err.println("Error loading courses from CSV.");
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Are you an Admin or Student? (Enter 'Admin', 'Student', or 'Quit' to exit): ");
            String role = scanner.nextLine();

            if ("Admin".equalsIgnoreCase(role)) {
                // Admin functionalities here, e.g., create, delete, edit, display courses
                System.out.println("Admin functionality not implemented.");
            } else if ("Student".equalsIgnoreCase(role)) {
                // Student functionalities here, e.g., register for, withdraw from, view courses
                System.out.println("Student functionality not implemented.");
            } else if ("Quit".equalsIgnoreCase(role)) {
                break;
            } else {
                System.out.println("Invalid role. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Exiting Course Registration System.");
    }
}