package in.sk.main.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Pattern(regexp = "^[a-z,A-Z ]{5,25}$", message = "Enter full Name")
    private String name;

    @Column
    @Pattern(regexp = "^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$", message = "Invalid email")
    private String email;

    @Column
    @Pattern(regexp = "^[a-z,A-Z,0-9]{5,25}$", message = "Enter a Strong Password")
    private String password;

    @Column
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String phoneno;

    @Column
    @Pattern(regexp = "^[a-z,A-Z]{3,25}$", message = "Invalid city format")
    private String city;

    @Column
    private boolean banStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isBanStatus() {
        return banStatus;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }
}
