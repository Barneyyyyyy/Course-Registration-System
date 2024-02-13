package courseregistration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

    private static void handleAdminActions(Scanner scanner, Admin admin, List<Course> courses) {
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Create a new course");
            System.out.println("2. Delete a course");
            System.out.println("3. Edit a course");
            System.out.println("4. Display course information");
            System.out.println("5. Register a student for a course");
            System.out.println("6. View all courses");
            System.out.println("7. View full courses");
            System.out.println("8. Write full courses to a file");
            System.out.println("9. View students in a course");
            System.out.println("10. View courses a student is registered in");
            System.out.println("11. Sort courses by current enrollment");
            System.out.println("12. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
            case 1:
                System.out.println("Enter course details (Name, ID, Max Students, Instructor, Section Number, Location):");
                String courseDetails = scanner.nextLine();
                String[] details = courseDetails.split(","); // Expecting user to input comma-separated values
                Course newCourse = new Course(details[0], details[1], Integer.parseInt(details[2]), details[3], Integer.parseInt(details[4]), details[5]);
                courses.add(newCourse);
                System.out.println("New course created successfully.");
                break;
            case 2:
                System.out.println("Enter the course ID to delete:");
                String courseIdToDelete = scanner.nextLine();
                courses.removeIf(course -> course.getCourseId().equals(courseIdToDelete));
                System.out.println("Course deleted successfully.");
                break;
            case 3:
                System.out.println("Enter the course ID to edit:");
                String courseIdToEdit = scanner.nextLine();
                // Find the course and edit details as needed
                for (Course course : courses) {
                    if (course.getCourseId().equals(courseIdToEdit)) {
                        // Assuming you prompt the admin for new details and set them here
                        System.out.println("Course edited successfully.");
                        break;
                    }
                }
                break;
            case 4:
                System.out.println("Enter the course ID to display information:");
                String courseIdToDisplay = scanner.nextLine();
                courses.stream()
                       .filter(course -> course.getCourseId().equals(courseIdToDisplay))
                       .findFirst()
                       .ifPresent(Course::displayCourseInfo); // Assuming Course class has a displayCourseInfo method
                break;
            case 5:
                System.out.println("Enter student details (Username, Password, First Name, Last Name) to register:");
                String studentDetails = scanner.nextLine();
                String[] studentInfo = studentDetails.split(",");
                if(studentInfo.length == 4) {
                    Student newStudent = new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3]);
                    admin.registerStudent(newStudent);
                    System.out.println("Student registered successfully.");
                } else {
                    System.out.println("Invalid student details provided.");
                }
                break;

            case 6:
                System.out.println("All courses:");
                courses.forEach(Course::displayCourseInfo); // Display all courses
                break;
            case 7:
                System.out.println("Full courses:");
                courses.stream()
                       .filter(course -> course.getCurrentNumStudents() == course.getMaxStudents())
                       .forEach(Course::displayCourseInfo); // Display full courses
                break;
            case 8:
                // Logic to write full courses to a file (not implemented here)
                System.out.println("Full courses written to file successfully.");
                break;
            case 9:
                System.out.println("Enter the course ID to view registered students:");
                // Logic to display students in a course (not implemented here)
                break;
            case 10:
                System.out.println("Enter student's first name and last name to view their courses:");
                // Logic to display courses a student is registered in (not implemented here)
                break;
            case 11:
                courses.sort(Comparator.comparingInt(Course::getCurrentNumStudents)); // Sort courses by current enrollment
                System.out.println("Courses sorted by current enrollment.");
                break;
            case 12:
                System.out.println("Exiting Admin Menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        } while (choice != 12);
    }

    private static void handleStudentActions(Scanner scanner, Student student, List<Course> courses) {
        int choice;
        do {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View all courses");
            System.out.println("2. View courses that are not full");
            System.out.println("3. Register for a course");
            System.out.println("4. Withdraw from a course");
            System.out.println("5. View enrolled courses");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Implement logic to view all courses
                    break;
                case 2:
                    // Implement logic to view courses that are not full
                    break;
                case 3:
                    // Implement logic to register for a course
                    break;
                case 4:
                    // Implement logic to withdraw from a course
                    break;
                case 5:
                    // Implement logic to view enrolled courses
                    break;
                case 6:
                    System.out.println("Exiting Student Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin", "admin123", "Admin", "Administrator");
        Student student = new Student("student", "student123", "Student", "Learner");
        List<Course> courses = new ArrayList<>();

        try {
            courses = loadCoursesFromCSV("C:\\Users\\bhavi\\OneDrive\\Documents\\Varsity\\Anna\\Course-Registration-System\\courseregistration\\MyUniversityCourses.csv"); // Adjust the path if needed
            // courses.forEach(course -> System.out.println(course.getCourseName())); // For testing purposes
        } catch (IOException e) {
            System.err.println("Error loading courses from CSV.");
            e.printStackTrace();
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("Are you an Admin or Student? (Enter 'Admin', 'Student', or 'Quit' to exit): ");
            String role = scanner.nextLine();

            switch (role.toLowerCase()) {
                case "admin":
                    handleAdminActions(scanner, admin, courses);
                    break;
                case "student":
                    handleStudentActions(scanner, student, courses);
                    break;
                case "quit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid role. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Exiting Course Registration System.");
    }
}
