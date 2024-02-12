package courseregistration;

public interface AdminInterface {
    void createCourse(Course course);
    void deleteCourse(String courseId);
    void editCourse(String courseId, Course course);
    void displayCourseInfo(String courseId);
    void registerStudent(Student student);
    // Other methods can be defined here as needed
}