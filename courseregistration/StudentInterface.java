package courseregistration;

public interface StudentInterface {
    void registerForCourse(Course course, Student student);

    void withdrawFromCourse(Course course, Student student);

    void viewEnrolledCourses();
    // Other methods can be defined here as needed
}
