package model;

public class Staff extends User {
    private String position;

    /** new user (no DB id yet) */
    public Staff(String fullname,
                 String email,
                 String password,
                 String phone,
                 String position) {
        super(fullname, email, password, phone);
        this.position = position;
    }

    /** existing user loaded from DB (with id) */
    public Staff(int id,
                 String fullname,
                 String email,
                 String password,
                 String phone,
                 String position) {
        super(id, fullname, email, password, phone);
        this.position = position;
    }

    public String getPosition()               { return position; }
    public void   setPosition(String position){ this.position = position; }
}
