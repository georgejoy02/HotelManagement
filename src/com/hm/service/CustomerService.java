package com.hm.service;

import com.hm.model.User;
import com.hm.dao.UserDAO;

public class CustomerService {
	UserDAO userDao = new UserDAO();

	public int reqisterCustomer(User user) {
		String hashedPassword = user.getPassword();
		user.setPassword(hashedPassword);
		return userDao.addUser(user);
	}

	public boolean loginCustomer(String userId, String password) {

		String hashedPassword = password;

		return userDao.validateUser(userId, hashedPassword);

	}

}
