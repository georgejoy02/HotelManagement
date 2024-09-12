package com.hm.service;

import com.hm.dao.AdminDAO;

public class AdminService {
	AdminDAO adminDao = new AdminDAO();
	public boolean loginAdmin(String adminId, String password){

		return adminDao.validateUser(adminId, password);

	}
	public boolean validateAdminServ(String adminId) {
		
		return adminDao.validateAdmin(adminId);
	}
	
}
