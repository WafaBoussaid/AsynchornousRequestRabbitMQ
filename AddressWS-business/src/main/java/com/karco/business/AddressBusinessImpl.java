package com.karco.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karco.dao.AddressRepository;
import com.karco.entities.Address;
import com.karco.interfaces.AddressBusiness;
@Service
public class AddressBusinessImpl implements AddressBusiness{
	@Autowired
	private AddressRepository addressRepository; 
	@Override
	public List<Address> getAddressByFilter(String voie) {
		List<Address> result = addressRepository.findAddressByFilter(voie);
		if(result.size() > 10 )
			return result.subList(0, 5);
		return result;
	}

}
