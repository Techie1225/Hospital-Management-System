package com.spring.hms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.hms.entity.Appointment;


@Repository("appointmentRepository")
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	List<Appointment> findByDoctorId(Integer doctordId);
	
	@Query(value = "SELECT * FROM app WHERE doctor_id = :doctorId AND appointment_date = :appointmentDate AND appointment_time = :appointmentTime", nativeQuery = true)
    List<Appointment> findByAppointment(Integer doctorId, String appointmentDate, String appointmentTime);
	
}