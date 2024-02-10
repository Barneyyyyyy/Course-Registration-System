package courseregistration;
import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L; //unique identifier for when we serialize; will check if vales match 

    private String courseName;
    private String courseId;
    private int maxStudents;
    private int currentNumStudents;
    private ArrayList<String> studentNames; // This will store the usernames of the students
    private String courseInstructor;
    private int sectionNumber;
    private String location;

    // Constructor
    public Course(String courseName, String courseId, int maxStudents, String courseInstructor,
                  int sectionNumber, String location) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.maxStudents = maxStudents;
        this.currentNumStudents = 0; // Initially, no students are registered
        this.studentNames = new ArrayList<>();
        this.courseInstructor = courseInstructor;
        this.sectionNumber = sectionNumber;
        this.location = location;
    }

    // Getters and setters and other methods
}
