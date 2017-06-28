package com.isidis.karco.repository.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.isidis.karco.entity.ref.Address;

@Repository("AddressRepository")
public interface AddressRepository extends JpaRepository<Address,Integer> {
	@Query("Select a from Address a where a.zip_code = :zip_code and a.street like %:street% ")
	public List<Address> findAddressByFilter(@Param("zip_code")String zip_code,
											 @Param("street")String street);
	
	
}