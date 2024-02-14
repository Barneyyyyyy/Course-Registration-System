package courseregistration;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    // Constants for file paths
    private static final String COURSES_FILE = "courses.ser";
    private static final String STUDENTS_FILE = "students.ser";

    // Predefined admin and students for demonstration
    @SuppressWarnings("unused")
	private static Admin admin = new Admin();
    private static List<Student> predefinedStudents = new ArrayList<>();
    
    // Static initializer block
    static {
        // Initialize one or two predefined students
        predefinedStudents.add(new Student("student1", "password1", "Student", "One"));
        predefinedStudents.add(new Student("student2", "password2", "Student", "Two"));
    }
    
    

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        // Attempt to load the serialized data
        try {
            courses = loadStateCourses();
            students = loadStateStudents();
        } catch (IOException | ClassNotFoundException e) {
            // If loading serialized data fails, try loading from CSV
            System.err.println("Error loading saved data. Starting with an empty state.");
            courses = loadCoursesFromCSV("MyUniversityCourses.csv");
        }

        Admin admin = new Admin(); // Hardcoded admin with credentials
        Student loggedInStudent = null; // Placeholder for logged-in student

        boolean exit = false;
        while (!exit) {
            System.out.print("Are you an Admin or Student? (Enter 'Admin', 'Student', or 'Quit' to exit): ");
            String role = scanner.nextLine();

            if ("Admin".equalsIgnoreCase(role)) {
                System.out.print("Enter admin username: ");
                String username = scanner.nextLine();
                System.out.print("Enter admin password: ");
                String password = scanner.nextLine();
                // Authenticate admin
                if (admin.authenticate(username, password)) {
                    // Admin logic is handled here
                    handleAdminActions(scanner, admin, courses, students);
                } else {
                    System.out.println("Incorrect admin username or password.");
                }
            } else if ("Student".equalsIgnoreCase(role)) {
                System.out.print("Enter student username: ");
                String username = scanner.nextLine();
                System.out.print("Enter student password: ");
                String password = scanner.nextLine();
                // Authenticate student
                loggedInStudent = authenticateStudent(username, password);
                if (loggedInStudent != null) {
                    handleStudentActions(scanner, loggedInStudent, courses);
                } else {
                    System.out.println("Incorrect username or password for student.");
                }
            } else if ("Quit".equalsIgnoreCase(role)) {
                exit = true;
            } else {
                System.out.println("Invalid role. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Exiting Course Registration System.");
    }

    private static Student authenticateStudent(String username, String password) {
        for (Student student : predefinedStudents) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                return student;
            }
        }
        return null; // No matching student found
    }

    private static List<Course> loadCoursesFromCSV(String filePath) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Assumes CSV format matches Course constructor
                if (values.length >= 6) { // Ensure there are enough data points
                    Course course = new Course(values[0], values[1], Integer.parseInt(values[2]), values[3], Integer.parseInt(values[4]), values[5]);
                    courses.add(course);
                }
            }
        }
        return courses;
    }


	@SuppressWarnings("unchecked")
	private static List<Course> loadStateCourses() throws IOException, ClassNotFoundException {
        File file = new File(COURSES_FILE);
        if (file.exists()) {
            return (List<Course>) SerializationUtil.deserialize(COURSES_FILE);
        } else {
            return new ArrayList<>(); // Empty list if the file does not exist
        }
    }

    @SuppressWarnings("unchecked")
	private static List<Student> loadStateStudents() throws IOException, ClassNotFoundException {
        File file = new File(STUDENTS_FILE);
        if (file.exists()) {
            return (List<Student>) SerializationUtil.deserialize(STUDENTS_FILE);
        } else {
            return new ArrayList<>(); // Empty list if the file does not exist
        }
    }

    private static void saveState(List<Course> courses, List<Student> students) throws IOException {
        SerializationUtil.serialize(courses, COURSES_FILE);
        SerializationUtil.serialize(students, STUDENTS_FILE);
    }

    @SuppressWarnings("unused")
	private static boolean authenticateAdmin(Scanner scanner, Admin admin) {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        
        return admin.authenticate(username, password);
    }
    
    private static void writeFullCoursesToFile(List<Course> courses, String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : courses) {
                if (course.getCurrentNumStudents() == course.getMaxStudents()) {
                    bw.write(course.toString());
                    bw.newLine();
                }
            }
        }
    }


@SuppressWarnings("unused")
private static void handleAdminActions(Scanner scanner, Admin admin, List<Course> courses, List<Student> students) throws IOException {
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
            // Create a new course
            System.out.println("Enter course details (Name, ID, Max Students, Instructor, Section Number, Location):");
            String courseDetails = scanner.nextLine();
            String[] details = courseDetails.split(","); // Expecting user to input comma-separated values
            if (details.length == 6) {
                Course newCourse = new Course(details[0], details[1], Integer.parseInt(details[2]), details[3], Integer.parseInt(details[4]), details[5]);
                courses.add(newCourse);
                System.out.println("New course created successfully.");
                saveState(courses, students); // Save state after adding new course
            } else {
                System.out.println("Invalid course details provided.");
            }
            break;
        case 2:
            // Delete a course
            System.out.println("Enter the course ID to delete:");
            String courseIdToDelete = scanner.nextLine();
            boolean removed = courses.removeIf(course -> course.getCourseId().equals(courseIdToDelete));
            if (removed) {
                System.out.println("Course deleted successfully.");
                saveState(courses, students); // Save state after deleting course
            } else {
                System.out.println("Course ID not found.");
            }
            break;
        case 3:
            // Edit a course
            System.out.println("Enter the course ID to edit:");
            String courseIdToEdit = scanner.nextLine();
            Course courseToEdit = courses.stream().filter(course -> course.getCourseId().equals(courseIdToEdit)).findFirst().orElse(null);
            if (courseToEdit != null) {
                // Assuming you have logic here to prompt for and handle new details
                System.out.println("Course edited successfully.");
                saveState(courses, students); // Save state after editing course
            } else {
                System.out.println("Course ID not found.");
            }
            break;
        case 5:
            // Register a student
            System.out.println("Enter student details (Username, Password, First Name, Last Name) to register:");
            String studentDetails = scanner.nextLine();
            String[] studentInfo = studentDetails.split(",");
            if (studentInfo.length == 4) {
                Student newStudent = new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3]);
                students.add(newStudent); // Assuming admin class has access to students list
                System.out.println("Student registered successfully.");
                saveState(courses, students); // Save state after registering student
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
            // Write full courses to a file
            try {
                writeFullCoursesToFile(courses, "full_courses.txt");
                System.out.println("Full courses written to file successfully.");
            } catch (IOException e) {
                System.out.println("Failed to write full courses to file: " + e.getMessage());
            }
            break;
        case 9:
            // View students in a specific course
            System.out.println("Enter the course ID to view registered students:");
            String courseIDForStudents = scanner.nextLine();
            courses.stream()
                   .filter(course -> course.getCourseId().equals(courseIDForStudents))
                   .findFirst()
                   .ifPresentOrElse(
                       course -> course.getStudentNames().forEach(System.out::println),
                       () -> System.out.println("Course not found.")
                   );
            break;
        case 10:
            // View courses a student is registered in
            System.out.println("Enter student's first name and last name:");
            String studentName = scanner.nextLine();
            students.stream()
                    .filter(student -> (student.getFirstName() + " " + student.getLastName()).equals(studentName))
                    .findFirst()
                    .ifPresentOrElse(
                        student -> student.getEnrolledCourses().forEach(course -> System.out.println(course.getCourseName() + " - " + course.getCourseId())),
                        () -> System.out.println("Student not found.")
                    );
            break;
        case 11:
            // Sort courses by current enrollment and display
            courses.sort(Comparator.comparingInt(Course::getCurrentNumStudents).reversed());
            System.out.println("Courses sorted by current enrollment:");
            courses.forEach(course -> System.out.println(course.getCourseName() + " - Enrolled: " + course.getCurrentNumStudents()));
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
        System.out.println("2. View courses that are not FULL");
        System.out.println("3. Register for a course");
        System.out.println("4. Withdraw from a course");
        System.out.println("5. View enrolled courses");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                courses.forEach(Course::displayCourseInfo);
                break;
            case 2:
                courses.stream().filter(course -> course.getCurrentNumStudents() < course.getMaxStudents())
                        .forEach(Course::displayCourseInfo);
                break;
            case 3:
                System.out.println("Enter course ID and section: ");
                String courseId = scanner.nextLine();
                String section = scanner.nextLine(); // Assuming section is needed for identification
                Optional<Course> courseToRegister = courses.stream()
                        .filter(course -> course.getCourseId().equals(courseId) && String.valueOf(course.getSectionNumber()).equals(section))
                        .findFirst();
                if (courseToRegister.isPresent()) {
                    student.registerForCourse(courseToRegister.get());
                    System.out.println("Registered successfully.");
                } else {
                    System.out.println("Course not found or full.");
                }
                break;
            case 4:
                System.out.println("Enter course ID to withdraw from: ");
                String courseIdToWithdraw = scanner.nextLine();
                student.withdrawFromCourse(courses.stream()
                        .filter(course -> course.getCourseId().equals(courseIdToWithdraw))
                        .findFirst().orElse(null));
                System.out.println("Withdrawn successfully.");
                break;
            case 5:
                student.getEnrolledCourses().forEach(Course::displayCourseInfo);
                break;
            case 6:
                System.out.println("Exiting Student Menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 6);
}}

    
    