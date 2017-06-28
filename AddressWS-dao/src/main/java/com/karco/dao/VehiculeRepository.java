package com.karco.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.karco.entities.*;

@Repository("VehiculeRepository")
public interface VehiculeRepository extends JpaRepository<Vehicule,Integer> {
	
	@Query("Select v from Vehicule v where UPPER(v.type) = UPPER(:type)")
	public Vehicule findByType(@Param("type")String type);
	@Query("Select v.maxPassenger from Vehicule v where UPPER(v.type) = UPPER(:type)")
	public int findMaxPassengerByType(@Param("type")String type);
}
