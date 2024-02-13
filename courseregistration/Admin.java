package courseregistration;
import java.util.List;
import java.util.ArrayList;

public class Admin extends User implements AdminInterface {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Course> courses; // List to manage courses
    private List<Student> students; // List to manage students

    public Admin(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
        this.courses = new ArrayList<>(); // Initialize an empty list of courses
        this.students = new ArrayList<>(); // Initialize an empty list of students
    }

    public void createCourse(Course course) {
        // Add the course to the list of courses
        courses.add(course);
    }

    public void deleteCourse(String courseId) {
        // Remove a course from the list based on courseId
        courses.removeIf(course -> course.getCourseId().equals(courseId));
    }

    public void editCourse(String courseId, Course newCourseData) {
        // Find the course and update its data
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                // Assuming Course class has setters for updating its fields
                course.setCourseName(newCourseData.getCourseName());
                course.setMaxStudents(newCourseData.getMaxStudents());
                // ... Update other fields as necessary
                break;
            }
        }
    }

    public void displayCourseInfo(String courseId) {
        // Find the course and print its details
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                // Print course details
                System.out.println("Course Name: " + course.getCourseName());
                System.out.println("Course ID: " + course.getCourseId());
                // ... Print other details as necessary
                break;
            }
        }
    }

    public void registerStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>(); // Initialize if null
        }
        students.add(student); // Add the student to the list
    }

    // ... Implement other methods as required by AdminInterface
}