
package com.model;

public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String role;
	private String status;

	public User() {
	}

	// Constructor for updating user (without password & address)
	public User(int id, String name, String email, String phone, String role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
	}

	// Constructor for adding new user
	public User(String name, String email, String password, String phone, String address, String role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.status = "Active";
	}

	// Getters & Setters
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
