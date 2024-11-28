package com.spring.hms.service;

import java.util.List;

import com.spring.hms.entity.Admin;
import com.spring.hms.entity.User;

public interface AdminService {


	public List<Admin> findByRole(String user);

	public Admin findByEmail(String user);
	
	public List<Admin> findAll();

	public void save(Admin admin);

	List<Admin> getAllDoctors(String doctor);
	
}
