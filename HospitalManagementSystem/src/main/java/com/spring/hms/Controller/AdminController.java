package com.spring.hms.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.hms.entity.Admin;
import com.spring.hms.entity.Appointment;
import com.spring.hms.entity.User;
import com.spring.hms.service.AdminServiceImplementation;
import com.spring.hms.service.AppointmentServiceImplementation;
import com.spring.hms.service.EmailService;
import com.spring.hms.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	

	private UserService userService;

	private AdminServiceImplementation adminServiceImplementation;
	
	private AppointmentServiceImplementation appointmentServiceImplementation;
	
	@Autowired
	private EmailService emailService;

	
	@Autowired
	public AdminController(UserService userService,AdminServiceImplementation obj,
			AppointmentServiceImplementation app) {
		this.userService = userService;
		adminServiceImplementation=obj;
		appointmentServiceImplementation=app;
	}
	
	
	@RequestMapping("/user-details")
	public String index(Model model){
		
		
		List<Admin> list=adminServiceImplementation.findByRole("ROLE_USER");
		model.addAttribute("user", list);
		
		
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
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		
		
		return "admin/user";
	}
	
	@RequestMapping("/doctor-details")
	public String doctorDetails(Model model){
		
		
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
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		
		
		List<Admin> list=adminServiceImplementation.findByRole("ROLE_DOCTOR");
		
		
		
		// add to the spring model
		model.addAttribute("user", list);
		
		
		return "admin/doctor";
	}
	
	@RequestMapping("/admin-details")
	public String adminDetails(Model model){
		
		
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
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		
		         
		List<Admin> list=adminServiceImplementation.findByRole("ROLE_ADMIN");
		
		
		
		// add to the spring model
		model.addAttribute("user", list);
		
		
		return "admin/admin";
	}
	
	
	@GetMapping("/add-doctor")
	public String showFormForAdd(Model theModel) {
		
		
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
		
		Admin admin1 = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin1.setLastseen(log);
		         
		         adminServiceImplementation.save(admin1);
		
		
		// create model attribute to bind form data
		Admin admin = new Admin();
		
		theModel.addAttribute("doctor", admin);
		
		return "admin/addDoctor";
	}
	
	
	@PostMapping("/save-doctor")
	public String saveEmployee(RedirectAttributes redirectAttributes,@ModelAttribute("doctor") Admin admin) {
		
		// save the employee
	//	admin.setId(0);
		
		admin.setRole("ROLE_DOCTOR");
		
		admin.setPassword("default");
		
		admin.setEnabled(true);

		admin.setConfirmationToken(UUID.randomUUID().toString());
		
		System.out.println(admin);
		
		adminServiceImplementation.save(admin);
		String appUrl = "localhost:8080";
	    
	    
		SimpleMailMessage registrationEmail = new SimpleMailMessage();
		registrationEmail.setTo(admin.getEmail());
		registrationEmail.setSubject("Registration Confirmation");
		registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
				+ appUrl + "/confirm?token=" + admin.getConfirmationToken());
		registrationEmail.setFrom("spring.email.auth@gmail.com");
		
		emailService.sendEmail(registrationEmail);
		
		redirectAttributes.addFlashAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + admin.getEmail());
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/admin/doctor-details";
	}
	
	@PostMapping("/removeDoctor/{id}")
	public String removeDoctor(@PathVariable("id") Integer id) {
		System.out.println(id);
		userService.removeDoctor(id);
		return "redirect:/admin/doctor-details";
	}
	
	@PostMapping("/removeUser/{id}")
	public String removeUser(@PathVariable("id") Integer id) {
		System.out.println(id);
		userService.removeUser(id);
		return "redirect:/admin/user-details";
	}
	
	

	@GetMapping("/add-admin")
	public String showForm(Model theModel) {
		
		
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
		
		Admin admin1 = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin1.setLastseen(log);
		         
		         adminServiceImplementation.save(admin1);
		
		
		
		// create model attribute to bind form data
		Admin admin = new Admin();
		
		theModel.addAttribute("doctor", admin);
		
		return "admin/addAdmin";
	}
	
	
	@PostMapping("/save-admin")
	public String saveEmploye(RedirectAttributes redirectAttributes,@ModelAttribute("doctor") Admin admin) {
		
		// save the employee
	//	admin.setId(0);
		
		admin.setRole("ROLE_ADMIN");
		
		admin.setPassword("default");
		
		admin.setEnabled(true);

		admin.setConfirmationToken(UUID.randomUUID().toString());
		
		System.out.println(admin);
		
		adminServiceImplementation.save(admin);
		String appUrl = "localhost:8080";
	    
	    
		SimpleMailMessage registrationEmail = new SimpleMailMessage();
		registrationEmail.setTo(admin.getEmail());
		registrationEmail.setSubject("Registration Confirmation");
		registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
				+ appUrl + "/confirm?token=" + admin.getConfirmationToken());
		registrationEmail.setFrom("spring.email.auth@gmail.com");
		
		emailService.sendEmail(registrationEmail);
		
		redirectAttributes.addFlashAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + admin.getEmail());
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/admin/admin-details";
	}
	
	@GetMapping("/edit-my-profile")
	public String EditForm(Model theModel) {
		
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
		
		// get the employee from the service
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		System.out.println(admin);
		
		theModel.addAttribute("profile", admin);
		
		return "admin/updateMyProfile";
	}
	
	@GetMapping("/editPatient/{id}")
	public String editPatient(@PathVariable("id") Integer id, Model theModel) {

		List<Admin> list=adminServiceImplementation.findByRole("ROLE_USER");
		theModel.addAttribute("user", list);
		
		
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
		
		User patient = userService.findUserById(id);
		System.out.println(patient);
		
		theModel.addAttribute("profile", patient);
		
		return "admin/updatePatient";
	}
	
	@GetMapping("/editDoctor/{id}")
	public String editDoctor(@PathVariable("id") Integer id, Model theModel) {
		
//		String username="";
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (principal instanceof UserDetails) {
//		   username = ((UserDetails)principal).getUsername();
//		  String Pass = ((UserDetails)principal).getPassword();
//		  System.out.println("One + "+username+"   "+Pass);
//		} else {
//		 username = principal.toString();
//		  System.out.println("Two + "+username);
//		}	
		User patient = userService.findUserById(id);
		System.out.println(patient);
		
		theModel.addAttribute("profile", patient);
		return "admin/updateDoctor";
	}
			
	
	@PostMapping("/update")
	public String update(@ModelAttribute("profile") Admin admin) {
		
		
		System.out.println(admin);
		
		adminServiceImplementation.save(admin);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/admin/user-details";
	}
	
	
	@RequestMapping("/appointments")
	public String appointments(Model model){
		
		
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
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		
		         
		List<Appointment> list=appointmentServiceImplementation.findAll();
		List<User> allusers = userService.findAll();
		List<User> doctors = new ArrayList<User>();
		for(User usr : allusers) {
			if(usr.getRole().equals("ROLE_DOCTOR")) {
				doctors.add(usr);
			}
		}
		
		// add to the spring model
		model.addAttribute("app", list);
		model.addAttribute("doctors", doctors);
		model.addAttribute("count", list.size());
		
		
		return "admin/appointment";
	}
	
	
	@RequestMapping("/appointmentsByDoctor")
	public String appointmentsByDoctor(Model model, @RequestParam("appointmentDate") String appointmentsbydate, 
			@RequestParam("doctorId") Integer doctorId){
		
		
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
		
		Admin admin = adminServiceImplementation.findByEmail(username);
				 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date now = new Date();  
		    
		         String log=now.toString();
		    
		         admin.setLastseen(log);
		         
		         adminServiceImplementation.save(admin);
		
		
		         
//		List<Appointment> list=appointmentServiceImplementation.findAll();
		List<Appointment> appointmentByDate = new ArrayList<Appointment>();
		List<Appointment> list=appointmentServiceImplementation.findByDoctorId(doctorId);
		for(Appointment app : list) {
			if(app.getAppointmentDate().toString().equals(appointmentsbydate)) {
				System.out.println(app.getPrescription());
				appointmentByDate.add(app);
			}
			
		}
		List<User> allusers = userService.findAll();
		List<User> doctors = new ArrayList<User>();
		for(User usr : allusers) {
			if(usr.getRole().equals("ROLE_DOCTOR")) {
				doctors.add(usr);
			}
		}
		
		model.addAttribute("doctors", doctors);
		// add to the spring model
		model.addAttribute("app", appointmentByDate);
		
		model.addAttribute("count", appointmentByDate.size());
		return "admin/appointment";
	}
	
	
}
