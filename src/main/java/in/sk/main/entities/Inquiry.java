package in.sk.main.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;

@Entity
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String phoneno;
    @Column
    private String interestedCourse;
    @Column
    private String discussion;
    @Column
    private String inquiryType;
    @Column
    private String callType;
    @Column
    private String status;
    @Column
    private String employeeEmail;
    @Column
    private String dateOfInquiry;
    @Column
    private String timeOfInquiry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getInterestedCourse() {
        return interestedCourse;
    }

    public void setInterestedCourse(String interestedCourse) {
        this.interestedCourse = interestedCourse;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getDateOfInquiry() {
        return dateOfInquiry;
    }

    public void setDateOfInquiry(String dateOfInquiry) {
        this.dateOfInquiry = dateOfInquiry;
    }

    public String getTimeOfInquiry() {
        return timeOfInquiry;
    }

    public void setTimeOfInquiry(String timeOfInquiry) {
        this.timeOfInquiry = timeOfInquiry;
    }
}
