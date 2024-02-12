package courseregistration;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    // Constructor to initialize user properties
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password, ensuring that it's handled securely is important
    public void setPassword(String password) {
        // Here you would include hashing or encryption as needed for security
        this.password = password;
    }

    // Getter for first name
    public String getFirstName() {
        return firstName;
    }

    // Setter for first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter for last name
    public String getLastName() {
        return lastName;
    }

    // Setter for last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // A method to get the full name of the user
    public String getFullName() {
        return firstName + " " + lastName;
    }

}