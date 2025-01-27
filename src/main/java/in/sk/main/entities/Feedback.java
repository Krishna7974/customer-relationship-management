package in.sk.main.entities;

import jakarta.persistence.*;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3000)
    private String feedback;
    @Column
    private String userName;
    @Column
    private String userEmail;
    @Column
    private String dateOfFeedback;
    @Column
    private String timeOfFeedback;
    @Column
    private String readStatus;


    public String getDateOfFeedback() {
        return dateOfFeedback;
    }

    public void setDateOfFeedback(String dateOfFeedback) {
        this.dateOfFeedback = dateOfFeedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getTimeOfFeedback() {
        return timeOfFeedback;
    }

    public void setTimeOfFeedback(String timeOfFeedback) {
        this.timeOfFeedback = timeOfFeedback;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
