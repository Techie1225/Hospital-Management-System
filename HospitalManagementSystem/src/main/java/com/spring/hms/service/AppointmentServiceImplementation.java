package com.spring.hms.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hms.entity.Admin;
import com.spring.hms.entity.Appointment;
import com.spring.hms.repository.AppointmentRepository;


@Service
public class AppointmentServiceImplementation  {

	private AppointmentRepository appointmentRepository;
	

    public List<String> getAvailableTimeSlots(int doctorId, String appointmentDate) {
        // Logic to retrieve available time slots for the given doctor and date.
        // You can fetch data from the database or use a predefined set of time slots.

        // For simplicity, returning some dummy time slots.
        // You can implement the logic to check existing appointments for the selected doctor and date.
        if (doctorId == 1) {
            return List.of("10:00 AM", "11:00 AM", "12:00 PM", "02:00 PM", "03:00 PM");
        } else if (doctorId == 2) {
            return List.of("09:00 AM", "10:30 AM", "01:00 PM", "03:30 PM");
        } else {
            return List.of();
        }
    }

	//inject employee dao
	@Autowired   //Adding bean id @Qualifier
	public AppointmentServiceImplementation( AppointmentRepository obj)
	{
		appointmentRepository=obj;
	}
	
	
	public void save(Appointment app)
	{
		
		appointmentRepository.save(app);
	}
	
	
	public List<Appointment> findAll() {
		return appointmentRepository.findAll();
	}

	public Appointment findAppById(Integer id) {
		return appointmentRepository.findById(id).get();
	}

	public List<Appointment> findByDoctorId(int doctordId) {
		return appointmentRepository.findByDoctorId(doctordId);
	}

	
	public boolean isDoctorAvailable(Integer doctorId, String appointmentDate, String appointmentTime) {

        List<Appointment> existingAppointments = appointmentRepository
            .findByAppointment(doctorId, appointmentDate, appointmentTime);

        return existingAppointments.isEmpty();
    }



	
}
