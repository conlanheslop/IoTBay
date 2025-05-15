package model;

public class User {
    private int    id;
    private String fullname;
    private String email;
    private String password;
    private String phone;

    public User() { }

    // For new users (no id yet)
    public User(String fullname, String email, String password, String phone) {
        this.fullname = fullname;
        this.email    = email;
        this.password = password;
        this.phone    = phone;
    }

    // For existing users (with id)
    public User(int id, String fullname, String email, String password, String phone) {
        this.id       = id;
        this.fullname = fullname;
        this.email    = email;
        this.password = password;
        this.phone    = phone;
    }

    // Getters & setters
    public int    getId()         { return id; }
    public void   setId(int id)   { this.id = id; }
    public String getFullname()   { return fullname; }
    public void   setFullname(String fullname) { this.fullname = fullname; }
    public String getEmail()      { return email; }
    public void   setEmail(String email)       { this.email = email; }
    public String getPassword()   { return password; }
    public void   setPassword(String password) { this.password = password; }
    public String getPhone()      { return phone; }
    public void   setPhone(String phone)       { this.phone = phone; }
}
