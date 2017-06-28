package com.karco.entities;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Vehicule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3021005304665947506L;
	
	@Id
	@Column(name="id")
	private int id;
	@Basic
	@Column(name="type")
	private String type;
	@Basic
	@Column(name="maxPassenger")
	private int maxPassenger;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getMaxPassenger() {
		return maxPassenger;
	}
	public void setMaxPassenger(int maxPassenger) {
		this.maxPassenger = maxPassenger;
	}
	public Vehicule() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vehicule(int id, String type, int maxPassenger) {
		super();
		this.id = id;
		this.type = type;
		this.maxPassenger = maxPassenger;
	}
	
}
