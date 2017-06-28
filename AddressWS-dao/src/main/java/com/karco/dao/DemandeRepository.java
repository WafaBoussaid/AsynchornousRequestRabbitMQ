package com.karco.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.karco.entities.*;

@Repository("DemandeRepository")
public interface DemandeRepository extends JpaRepository<Demande,Integer> {
	
}
