package courseregistration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseName;
    private String courseId;
    private int maxStudents;
    private List<Student> enrolledStudents;
    private List<String> studentNames = new ArrayList<>(); // Initialize here to avoid null
    private String courseInstructor;
    private int sectionNumber;
    private String location;

    public Course(String courseName, String courseId, int maxStudents, String courseInstructor, int sectionNumber,
            String location) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
        this.courseInstructor = courseInstructor;
        this.sectionNumber = sectionNumber;
        this.location = location;
    }

    // Getters and setters

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getCurrentNumStudents() {
        return enrolledStudents.size(); // Returns the size of the enrolledStudents list
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    // public void incrementCurrentNumStudents() {
    // if (enrolledStudents.size() < maxStudents) {
    // enrolledStudents.add(new Student("", "", "", "")); // Create a dummy student
    // } else {
    // System.out.println("Course is already full. Cannot increment.");
    // }
    // }

    // public void decrementCurrentNumStudents() {
    // if (!enrolledStudents.isEmpty()) {
    // enrolledStudents.remove(0); // Remove the first (or any) student
    // } else {
    // System.out.println("No students to decrement.");
    // }
    // }

    public void addStudent(Student student) {
        if (enrolledStudents.size() < maxStudents) {
            if (enrolledStudents.contains(student)) {
                System.out.println("You've already enrolled in this course.");
            } else {
                enrolledStudents.add(student);
                studentNames.add(student.getFirstName() + " " + student.getLastName());
            }
        } else {
            System.out.println("Course is full. Cannot add more students.");
        }
    }

    public void removeStudent(Student student) {
        if (enrolledStudents.contains(student)) {
            enrolledStudents.remove(student);
            studentNames.remove(student.getFirstName() + " " + student.getLastName());
        } else {
            System.out.println("Student not found in this course.");
        }
    }

    public List<String> getStudentNames() {
        return new ArrayList<>(studentNames); // Returns a copy of the studentNames list to prevent external
                                              // modifications.
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Additional methods
    public void displayCourseInfo() {
        System.out.println("Course Name: " + courseName);
        System.out.println("Course ID: " + courseId);
        System.out.println("Instructor: " + courseInstructor);
        System.out.println("Section Number: " + sectionNumber);
        System.out.println("Location: " + location);
        System.out.println("Enrolled Students: " + enrolledStudents.size() + "/" + maxStudents);
    }

}
