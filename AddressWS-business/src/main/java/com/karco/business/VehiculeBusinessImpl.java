package com.karco.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.karco.dao.VehiculeRepository;
import com.karco.entities.Vehicule;
import com.karco.interfaces.VehiculeBusiness;
@Service
public class VehiculeBusinessImpl implements VehiculeBusiness{
	@Autowired
	private VehiculeRepository vehiculeRepository; 

	@Override
	public Vehicule saveVehicule(Vehicule v) {
		return ((JpaRepository<Vehicule,Integer>) vehiculeRepository).save(v);
	}

	@Override
	public List<Vehicule> ListVehicule() {
		return ((JpaRepository<Vehicule, Integer>) vehiculeRepository).findAll();
	}

	@Override
	public Vehicule getVehiculeByType(String type) {
		return vehiculeRepository.findByType(type);
	}
	@Override
	public int getMaxPassengerByType(String type) {
		return vehiculeRepository.findMaxPassengerByType(type);
	}
	
}

	



