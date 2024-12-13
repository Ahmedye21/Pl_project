package Models.User;
public class User {
    int id;
    String name;
    String email;
    String password;
    String region;
    String role;

    public User(int id, String name, String email, String password, String role , String region) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public String getregion(){
        return region;
    }
    public void setregion(String region){
        this.region = region;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }




    public void logout() {
        System.out.println("User " + name + " logged out.");
    }

//    public static User login(String email, String password) {
//
//        String predefinedEmail = "user";
//        String predefinedPassword = "pass";
//
//        if (email.equals(predefinedEmail) && password.equals(predefinedPassword)) {
//            System.out.println("Login successful!");
//            return new User(1, "Default User", email, password, "Customer");
//        } else {
//            System.out.println("Invalid credentials.");
//            return null;
//        }
//    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
