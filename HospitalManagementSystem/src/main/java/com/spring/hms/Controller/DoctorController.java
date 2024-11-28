package com.spring.hms.Controller;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.hms.entity.Admin;
import com.spring.hms.entity.Appointment;
import com.spring.hms.service.AdminServiceImplementation;
import com.spring.hms.service.AppointmentServiceImplementation;
import com.spring.hms.service.UserService;


@Controller
@RequestMapping("/doctor")
public class DoctorController {

	private UserService userService;

	private AdminServiceImplementation adminServiceImplementation;
	
	private AppointmentServiceImplementation appointmentServiceImplementation;

	
	@Autowired
	public DoctorController(UserService userService,AdminServiceImplementation obj,
			AppointmentServiceImplementation app) {
		this.userService = userService;
		adminServiceImplementation=obj;
		appointmentServiceImplementation=app;
	}
	
	@RequestMapping("/updateAppointment")
	public String updateAppointment( Model model,   @RequestParam Integer id,
            @RequestParam String diagnosisDate,
            @RequestParam String diagnosisTime,
            @RequestParam String status,
            @RequestParam String prescription,
            @RequestParam Integer diagnosisDuration){
		Appointment app=appointmentServiceImplementation.findAppById(id);
		app.setDiagnosisDuration(diagnosisDuration);
		app.setStatus(status);
		app.setDiagnosisTime(diagnosisTime);
		app.setDiagnosisDate(diagnosisDate);
		app.setPrescription(prescription);
		appointmentServiceImplementation.save(app);
		return "redirect:/doctor/index";
	}
	
	
	@RequestMapping("/index")
	public String index(Model model){

	
		
		// get last seen
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);
		  
		  
		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}
		System.out.println(username);
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		
		int doctordId = userService.findByEmail(username).getId();
		System.out.println(doctordId);
		List<Appointment> list=appointmentServiceImplementation.findByDoctorId(doctordId);
		System.out.println(list);
		model.addAttribute("name",admin.getFirstName());
		
		model.addAttribute("email",admin.getEmail());
		
		
		model.addAttribute("user",admin.getFirstName()+" "+admin.getLastName());
		
		// add to the spring model
		model.addAttribute("app", list);
		model.addAttribute("count", list.size());
		
		return "doctor/index";
	}
	
	
	@RequestMapping("/appointmenyByDate")
	public String appointmenyByDateappointmenyByDate(Model model, @RequestParam("appomimentDate") String appointmentsbydate){

	
		
		// get last seen
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		  String Pass = ((UserDetails)principal).getPassword();
		  System.out.println("One + "+username+"   "+Pass);
		  
		  
		} else {
		 username = principal.toString();
		  System.out.println("Two + "+username);
		}
		System.out.println(username);
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int doctordId = userService.findByEmail(username).getId();
		System.out.println(doctordId);
		List<Appointment> appointmentByDate = new ArrayList<Appointment>();
		List<Appointment> list=appointmentServiceImplementation.findByDoctorId(doctordId);
		for(Appointment app : list) {
			if(app.getAppointmentDate().toString().equals(appointmentsbydate)) {
				System.out.println(app.getPrescription());
				appointmentByDate.add(app);
			}
			
		}
		System.out.println(appointmentByDate);
		model.addAttribute("name",admin.getFirstName());
		
		model.addAttribute("email",admin.getEmail());
		
		
		model.addAttribute("user",admin.getFirstName()+" "+admin.getLastName());
		
		// add to the spring model
		model.addAttribute("app", appointmentByDate);
		model.addAttribute("count", appointmentByDate.size());
		
		return "doctor/index";
	}
	
//	@PostMapping("changepassword")
//	public String changepassword(ChangePassword changepassword, Model model) {
//		System.out.println(changepassword.getWaiterid());
//		System.out.println(changepassword);
//		Optional<Waiter> obj = iWaiterRepo.findBy_id(changepassword.getWaiterid());
//		Waiter waiter = obj.get();
//		if(waiter.getPassword().equals(changepassword.getCurrentpassword())) {
//			waiter.setPassword(changepassword.getNewpassword());
//			waiter.setStatus(null);
//			iWaiterRepo.save(waiter);
//			return "waiterLogin";
//		}
//		else {
//			
//			model.addAttribute("waiterid",waiter.get_id());
//			return "changePassword";
//			}
//	}
	
	
}
