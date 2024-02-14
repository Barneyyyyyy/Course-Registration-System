package courseregistration;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    private static List<Student> loadStateStudents() {
		// TODO Auto-generated method stub
		return null;
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

	
	@SuppressWarnings({ "unchecked", "unused" })
	private static List<Course> loadStateCourses() throws IOException, ClassNotFoundException {
	    File file = new File(COURSES_FILE);
	    if (file.exists()) {
	        List<Course> courses = (List<Course>) SerializationUtil.deserialize(COURSES_FILE);
	        // Ensure that enrolledStudents list in each course is not null
	        for (Course course : courses) {
	            if (course.getEnrolledStudents() == null) {
	                course.setEnrolledStudents(new ArrayList<>());
	            }
	            // Ensure other attributes are properly initialized if needed
	        }
	        return courses;
	    } else {
	        return new ArrayList<>(); // Empty list if the file does not exist
	    }
	}

	private static void saveState(List<Course> courses, List<Student> students) throws IOException {
	    // First, ensure that the enrolledStudents list in each course is not null before serialization
	    for (Course course : courses) {
	        if (course.getEnrolledStudents() == null) {
	            course.setEnrolledStudents(new ArrayList<Student>());
	        }
	    }
	    // Then, serialize the courses and students lists
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
                // Trim each detail to remove leading and trailing spaces
                String courseName = details[0].trim();
                String courseId = details[1].trim();
                int maxStudents = Integer.parseInt(details[2].trim()); // Trim and parse to int
                String instructor = details[3].trim();
                int sectionNumber = Integer.parseInt(details[4].trim()); // Trim and parse to int
                String location = details[5].trim();

                Course newCourse = new Course(courseName, courseId, maxStudents, instructor, sectionNumber, location);
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
            String courseIdToEdit = scanner.nextLine().trim(); // Trim input for any leading or trailing spaces
            Course courseToEdit = courses.stream()
                                          .filter(course -> course.getCourseId().equals(courseIdToEdit))
                                          .findFirst()
                                          .orElse(null); // Find the course or return null if not found

            if (courseToEdit != null) {
                // Prompt for new details (not including ID and name)
                System.out.println("Editing Course: " + courseToEdit.getCourseName());
                System.out.print("Enter new max number of students (Current: " + courseToEdit.getMaxStudents() + "): ");
                int newMaxStudents = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Enter new instructor name (Current: " + courseToEdit.getCourseInstructor() + "): ");
                String newInstructor = scanner.nextLine().trim();
                System.out.print("Enter new section number (Current: " + courseToEdit.getSectionNumber() + "): ");
                int newSectionNumber = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Enter new location (Current: " + courseToEdit.getLocation() + "): ");
                String newLocation = scanner.nextLine().trim();

                // Update the course details
                courseToEdit.setMaxStudents(newMaxStudents);
                courseToEdit.setCourseInstructor(newInstructor);
                courseToEdit.setSectionNumber(newSectionNumber);
                courseToEdit.setLocation(newLocation);

                System.out.println("Course details updated successfully.");
                // Optionally, save the updated courses list to persist changes
                saveState(courses, students);
            } else {
                System.out.println("Course with ID '" + courseIdToEdit + "' not found.");
            }
            break;
            
        case 4:
            // Display course information
            System.out.println("Enter the course ID for which you want to display information:");
            String courseIdToDisplay = scanner.nextLine();
            Course courseToDisplay = courses.stream()
                                             .filter(course -> course.getCourseId().equals(courseIdToDisplay))
                                             .findFirst()
                                             .orElse(null); // Find the course with the given ID or return null if not found

            if (courseToDisplay != null) {
                // Display the found course's details
                System.out.println("Course Details:");
                System.out.println("Name: " + courseToDisplay.getCourseName());
                System.out.println("ID: " + courseToDisplay.getCourseId());
                System.out.println("Maximum Students: " + courseToDisplay.getMaxStudents());
                System.out.println("Current Enrolled: " + courseToDisplay.getCurrentNumStudents());
                System.out.println("Instructor: " + courseToDisplay.getCourseInstructor());
                System.out.println("Section Number: " + courseToDisplay.getSectionNumber());
                System.out.println("Location: " + courseToDisplay.getLocation());
                // If you're maintaining a list of student names or IDs within the course, you can display them here as well
            } else {
                System.out.println("Course with ID '" + courseIdToDisplay + "' not found.");
            }
            break;
        case 5:
            System.out.println("Enter student details (Username, Password, First Name, Last Name):");
            String studentDetails = scanner.nextLine();
            String[] studentInfo = studentDetails.split(",");
            System.out.println("Enter course ID to register the student for:");
            String courseIdToRegister = scanner.nextLine().trim(); // Get the course ID for registration

            Course courseToRegister = courses.stream()
                                              .filter(course -> course.getCourseId().equals(courseIdToRegister))
                                              .findFirst()
                                              .orElse(null);

            if (studentInfo.length == 4 && courseToRegister != null) {
                if (courseToRegister.getCurrentNumStudents() < courseToRegister.getMaxStudents()) {
                    Student newStudent = new Student(studentInfo[0].trim(), studentInfo[1].trim(), studentInfo[2].trim(), studentInfo[3].trim());
                    courseToRegister.addStudent(newStudent); // Add the student to the course's enrolled students list
                    if (students == null) {
                        students = new ArrayList<>(); // Initialize the list if it's null
                    }
                    students.add(newStudent); // Add student to the list of students
                    System.out.println("Student successfully registered for the course.");
                    saveState(courses, students); // Save the updated state
                } else {
                    System.out.println("Course is full. Cannot register the student.");
                }
            } else {
                if (studentInfo.length != 4) {
                    System.out.println("Invalid student details provided.");
                } else {
                    System.out.println("Course not found.");
                }
            }
            break;


        case 6:
            System.out.println("All courses:");
            courses.forEach(Course::displayCourseInfo); // Display all courses
            break;
        case 7:
            System.out.println("Full courses:");
            List<Course> fullCourses = courses.stream()
                                              .filter(course -> course.getCurrentNumStudents() == course.getMaxStudents())
                                              .collect(Collectors.toList());
            if (fullCourses.isEmpty()) {
                System.out.println("There are no full courses.");
            } else {
                fullCourses.forEach(Course::displayCourseInfo);
            }
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
            System.out.println("Enter student's first name and last name:");
            String[] nameParts = scanner.nextLine().trim().split(" ", 2);
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";

            Optional<Student> foundStudent = students.stream()
                                                     .filter(student -> student.getFirstName().equalsIgnoreCase(firstName) && student.getLastName().equalsIgnoreCase(lastName))
                                                     .findFirst();

            if (foundStudent.isPresent()) {
                System.out.println(firstName + " " + lastName + " is registered in the following courses:");
                List<Course> enrolledCourses = foundStudent.get().getEnrolledCourses();
                if (enrolledCourses.isEmpty()) {
                    System.out.println("This student is not registered in any courses.");
                } else {
                    enrolledCourses.forEach(course -> System.out.println(course.getCourseName() + " - " + course.getCourseId()));
                }
            } else {
                System.out.println("Student not found.");
            }
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

    
    