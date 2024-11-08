package com.g4AppDev.FoundIT.entity;
import jakarta.persistence.*;
@Entity
public class Admin {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, unique = true)
private String email;

@Column(nullable = false)
private String password;

public Admin() {}

public Admin(String email, String password) {
    this.email = email;
    this.password = password;
}

public Long getId() {
	return id; 
	}
public void setId(Long id) {
	this.id = id; 
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

}