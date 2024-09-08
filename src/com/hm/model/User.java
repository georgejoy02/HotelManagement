package com.hm.model;

public class User {
	private String customerName ;
	private String mobile;
	private String email;
	private String customerId;
	private String address;
	private String password ;
	
	public User(String customerName, String mobile, String email, String customerId, String address,
			String password) {
		this.customerName = customerName;
		this.mobile = mobile;
		this.email = email;
		this.customerId = customerId;
		this.address = address;
		this.password = password;
	}
	@Override
	public String toString() {		
		return "{"+this.customerName+"," +this.mobile+","+this.email+","+this.customerId+","+this.address+","+this.password +"}";
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
