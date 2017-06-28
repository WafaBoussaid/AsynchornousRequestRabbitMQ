package com.karco.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {

	public Address() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@Column(name="id")
	private int id;
	@Basic
	@Column(name="numero")
	private String numero;
	@Basic
	@Column(name="voie")
	private String voie;
	@Basic
	@Column(name="code_post")
	private String code_post;
	@Basic
	@Column(name="nom_comm")
	private String nom_comm;
    @Basic
	@Column(name="lon")
    private String lon;
    @Basic
	@Column(name="lat")
    private String lat;
	
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getVoie() {
		return voie;
	}
	public void setVoie(String voie) {
		this.voie = voie;
	}
	public String getCode_post() {
		return code_post;
	}
	public void setCode_post(String code_post) {
		this.code_post = code_post;
	}
	public String getNom_comm() {
		return nom_comm;
	}
	public void setNom_comm(String nom_comm) {
		this.nom_comm = nom_comm;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "KarCoAddress [id=" + id + ", numero=" + numero + ", voie=" + voie + ", code_post=" + code_post
				+ ", nom_comm=" + nom_comm + ", lon=" + lon + ", lat=" + lat + "]";
	}
	public Address(String numero, String voie, String code_post, String nom_comm, String lon, String lat) {
		super();
		this.numero = numero;
		this.voie = voie;
		this.code_post = code_post;
		this.nom_comm = nom_comm;
		this.lon = lon;
		this.lat = lat;
	}

}

