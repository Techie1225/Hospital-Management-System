package com.spring.hms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "app")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;
    
    @Column(name = "prescription")
    private String prescription;

	@Column(name = "BookedDateTime")
    @Transient
    private String bookeddatetime; 
    
    @Column(name = "status")
    private String status;

    @Column(name = "diagnosis_duration")
    private int diagnosisDuration; 
    
    @Column(name = "appointment_date")
    private String AppointmentDate; 
    
    @Column(name = "appointment_time")
    private String AppointmentTime;

    @Column(name = "diagnosis_date")
    private String diagnosisDate;  
    
    @Column(name = "diagnosis_time")
    private String diagnosisTime;  

    @Column(name = "doctor_id")
    private Integer doctorId; 

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	 public String getPrescription() {
			return prescription;
	}
	
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getBookeddatetime() {
		return bookeddatetime;
	}

	public void setBookeddatetime(String bookeddatetime) {
		this.bookeddatetime = bookeddatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDiagnosisDuration() {
		return diagnosisDuration;
	}

	public void setDiagnosisDuration(int diagnosisDuration) {
		this.diagnosisDuration = diagnosisDuration;
	}

	public String getAppointmentDate() {
		return AppointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		AppointmentDate = appointmentDate;
	}

	public String getAppointmentTime() {
		return AppointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		AppointmentTime = appointmentTime;
	}

	public String getDiagnosisDate() {
		return diagnosisDate;
	}

	public void setDiagnosisDate(String diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}

	public String getDiagnosisTime() {
		return diagnosisTime;
	}

	public void setDiagnosisTime(String diagnosisTime) {
		this.diagnosisTime = diagnosisTime;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	

   
}
