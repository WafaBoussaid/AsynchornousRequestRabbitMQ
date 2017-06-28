package com.karco.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Demande {

	public Demande() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@Column(name="id")
	private int id;
	@Basic
	@Column(name="max_price")
	private double max_price;
	@Basic
	@Column(name="dep_lon")
	private String dep_lon;
	@Basic
	@Column(name="dep_lat")
	private String dep_lat;
	@Basic
	@Column(name="arr_lon")
    private String arr_lon;
	@Basic
	@Column(name="vehiculeType")
    private String vehiculeType;
	@Basic
	@Column(name="arr_lat")
    private String arr_lat;
    @Basic
	@Column(name="max_delay")
    private int max_delay;
    @Basic
	@Column(name="max_passenger")
    private int max_passenger;
    @Basic
	@Column(name="demandeStatus")
    private String demandeStatus;
    @Basic
	@Column(name="demandeDuration")
    private String demandeDuration;
    @Basic
	@Column(name="demandeDistance")
    private String demandeDistance;
	
    public String getDemandeStatus() {
		return demandeStatus;
	}
	public void setDemandeStatus(String demandeStatus) {
		this.demandeStatus = demandeStatus;
	}
	public String getDemandeDuration() {
		return demandeDuration;
	}
	public void setDemandeDuration(String demandeDuration) {
		this.demandeDuration = demandeDuration;
	}
	public String getDemandeDistance() {
		return demandeDistance;
	}
	public void setDemandeDistance(String demandeDistance) {
		this.demandeDistance = demandeDistance;
	}
	public int getId() {
		return id;
	}
    public double getMax_price() {
		return max_price;
	}
	public void setMax_price(double max_price) {
		this.max_price = max_price;
	}
	public String getDep_lon() {
		return dep_lon;
	}
	public void setDep_lon(String dep_lon) {
		this.dep_lon = dep_lon;
	}
	public String getDep_lat() {
		return dep_lat;
	}
	public void setDep_lat(String dep_lat) {
		this.dep_lat = dep_lat;
	}
	public String getArr_lon() {
		return arr_lon;
	}
	public String getVehiculeType() {
		return vehiculeType;
	}
	public void setVehiculeType(String vehiculeType) {
		this.vehiculeType = vehiculeType;
	}
	public void setArr_lon(String arr_lon) {
		this.arr_lon = arr_lon;
	}
	public String getArr_lat() {
		return arr_lat;
	}
	public void setArr_lat(String arr_lat) {
		this.arr_lat = arr_lat;
	}
	public int getMax_delay() {
		return max_delay;
	}
	public void setMax_delay(int max_delay) {
		this.max_delay = max_delay;
	}
	public int getMax_passenger() {
		return max_passenger;
	}
	public void setMax_passenger(int max_passenger) {
		this.max_passenger = max_passenger;
	}
	public Demande(int max_price, String dep_lon, String dep_lat, String arr_lon, String arr_lat, int max_delay,
			int max_passenger,String vehiculeType,String demandeStatus) {
		super();
		this.max_price = max_price;
		this.dep_lon = dep_lon;
		this.dep_lat = dep_lat;
		this.arr_lon = arr_lon;
		this.arr_lat = arr_lat;
		this.max_delay = max_delay;
		this.max_passenger = max_passenger;
		this.vehiculeType = vehiculeType;
		this.demandeStatus = demandeStatus;
	}
	
    
    
    
    
}

