package courseregistration;

import java.util.ArrayList;
import java.util.List;

public class Student extends User implements StudentInterface {
    private static final long serialVersionUID = 1L;
    private List<Course> enrolledCourses;

    public Student(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
        this.enrolledCourses = new ArrayList<>();
    }

    public void registerForCourse(Course course) {
        if (course.getCurrentNumStudents() < course.getMaxStudents()) {
            enrolledCourses.add(course);
            course.incrementCurrentNumStudents();
        } else {
            System.out.println("Course is full. Cannot enroll.");
        }
    }

    public void withdrawFromCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            course.decrementCurrentNumStudents();
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

   
    public void viewEnrolledCourses() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }
        for (Course course : enrolledCourses) {
            System.out.println(course.getCourseName() + " - " + course.getCourseId());
        }
    }
}
