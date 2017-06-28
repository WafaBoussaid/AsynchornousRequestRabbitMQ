package com.karco.entities;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	public User() {
	}

	@Id
	@GeneratedValue
	@Column(name="users_id")
	private int id;

	@Basic
	@Column(name="users_name")
	private String Name;

	@Basic
	@Column(name="users_email")
	private String Email;

	@Basic
	@Column(name="users_password")
	private String Password;

	@Basic
	@Column(name="users_role")
	private String Role;

	public String getEmail() {
		return Email;
	}

	public User(String name, String email, String password, String role) {
		super();
		Name = name;
		Email = email;
		Password = password;
		Role = role;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", Name=" + Name + "]";
	}
	
}
