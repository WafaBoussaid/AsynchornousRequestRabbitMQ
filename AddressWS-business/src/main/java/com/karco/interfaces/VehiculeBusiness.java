package com.karco.interfaces;

import java.util.List;

import com.karco.entities.Vehicule;

public interface VehiculeBusiness {
	
	public Vehicule saveVehicule(Vehicule v);
	public List<Vehicule> ListVehicule();
	public Vehicule getVehiculeByType(String type);
	public int getMaxPassengerByType(String type);

}
