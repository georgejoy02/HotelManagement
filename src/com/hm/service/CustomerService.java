package com.hm.service;

import com.hm.model.User;
import com.hm.util.PasswordUtils;
import com.hm.dao.UserDAO;

public class CustomerService {
	UserDAO userDao = new UserDAO();
	PasswordUtils pwdu =new PasswordUtils();

	public int reqisterCustomer(User user) {
		String hashedPassword = pwdu.generatePasswordHash(user.getPassword());
		user.setPassword(hashedPassword);
		return userDao.addUser(user);
	}

	public boolean loginCustomer(String userId, String password) {

		return userDao.validateUser(userId, password);

	}

}
