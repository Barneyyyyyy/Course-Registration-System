package courseregistration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseName;
    private String courseId;
    private int maxStudents;
    private int currentNumStudents;
    private List<String> studentNames; // Storing student names/IDs
    private String courseInstructor;
    private int sectionNumber;
    private String location;

    // Constructor
    public Course(String courseName, String courseId, int maxStudents, String courseInstructor,
                  int sectionNumber, String location) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.maxStudents = maxStudents;
        this.currentNumStudents = 0; // Initialize with zero students registered
        this.studentNames = new ArrayList<>();
        this.courseInstructor = courseInstructor;
        this.sectionNumber = sectionNumber;
        this.location = location;
    }

    // Getters and Setters

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
        return currentNumStudents;
    }

    public void setCurrentNumStudents(int currentNumStudents) {
        this.currentNumStudents = currentNumStudents;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
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
    public void incrementCurrentNumStudents() {
        if (currentNumStudents < maxStudents) {
            currentNumStudents++;
        }
    }

    public void decrementCurrentNumStudents() {
        if (currentNumStudents > 0) {
            currentNumStudents--;
        }
    }

    public void addStudent(String studentName) {
        if (currentNumStudents < maxStudents) {
            studentNames.add(studentName);
            incrementCurrentNumStudents();
        } else {
            System.out.println("Course is full. Cannot add more students.");
        }
    }

    public void removeStudent(String studentName) {
        if (studentNames.contains(studentName)) {
            studentNames.remove(studentName);
            decrementCurrentNumStudents();
        } else {
            System.out.println("Student not found in this course.");
        }
    }

    // Method to display course details
    public void displayCourseInfo() {
        System.out.println("Course Name: " + courseName);
        System.out.println("Course ID: " + courseId);
        System.out.println("Instructor: " + courseInstructor);
        System.out.println("Section Number: " + sectionNumber);
        System.out.println("Location: " + location);
        System.out.println("Enrolled Students: " + currentNumStudents + "/" + maxStudents);
    }

}