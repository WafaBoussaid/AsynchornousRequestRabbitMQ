package com.isidis.karco.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isidis.karco.entity.ref.*;

@Repository("VehiculeRepository")
public interface VehiculeTypeRepository extends JpaRepository<VehiculeType,Integer> {
	
}