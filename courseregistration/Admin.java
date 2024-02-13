package courseregistration;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User implements AdminInterface {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Course> courses; // List to manage courses
    private List<Student> students; // List to manage students

    // Hardcoded admin credentials for simplicity as per requirements
    private static final String HARDCODED_USERNAME = "Admin";
    private static final String HARDCODED_PASSWORD = "Admin001";

    // Admin class constructor
    public Admin() {
        super(HARDCODED_USERNAME, HARDCODED_PASSWORD, "Admin", "Administrator");
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    // Authentication method
    public boolean authenticate(String username, String password) {
        return this.getUsername().equals(username) && this.getPassword().equals(password);
    }

    @Override
    public void createCourse(Course course) {
        courses.add(course);
    }

    @Override
    public void deleteCourse(String courseId) {
        courses.removeIf(course -> course.getCourseId().equals(courseId));
    }

    @Override
    public void editCourse(String courseId, Course newCourseData) {
        courses.stream()
            .filter(course -> course.getCourseId().equals(courseId))
            .findFirst()
            .ifPresent(course -> {
                course.setCourseName(newCourseData.getCourseName());
                course.setMaxStudents(newCourseData.getMaxStudents());
                // Add other setters as necessary
            });
    }

    @Override
    public void displayCourseInfo(String courseId) {
        courses.stream()
            .filter(course -> course.getCourseId().equals(courseId))
            .findFirst()
            .ifPresent(course -> {
                // Assuming Course class has a method to display info
                course.displayCourseInfo();
            });
    }

    @Override
    public void registerStudent(Student student) {
        students.add(student);
    }

    // Implement other methods as required by AdminInterface...
}
