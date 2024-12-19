package Core.Interfaces;

public interface UserInterface {

    // Method to register a new user
    boolean register();

    // Method to login a user
    void login();

    // Method to authenticate user with a password
    boolean authenticate(String enteredPassword);

    // Method to logout the current user
    void logout();

    // Method to handle bill payment
    void handleBillPayment(String meterCode, int price);

    // Getters for user information
    String getId();
    String getName();
    String getEmail();
    String getPassword();
    String getRole();
    String getRegion();

    // Setters for user information
    void setId(String id);
    void setName(String name);
    void setEmail(String email);
    void setPassword(String password);
    void setRole(String role);
    void setRegion(String region);
}
