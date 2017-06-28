package com.karco.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.karco.entities.*;
@Repository("AddressRepository")
public interface AddressRepository extends JpaRepository<Address,Integer> {
	
	@Query("Select a from Address a where a.voie like %:voie% ")
	public List<Address> findAddressByFilter(@Param("voie")String voie);
	
	@Query("Select a from Address a where a.numero = :numero and a.voie =:voie and a.code_post = :code_post ")
	public List<Address> findAddress(@Param("numero") String numero,@Param("voie") String voie,@Param("code_post") String code_post);
}