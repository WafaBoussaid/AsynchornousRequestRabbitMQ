package com.isidis.karco.repository;

import com.isidis.karco.entity.ref.Address;
import com.isidis.karco.repository.dao.AddressDao;
import com.isidis.karco.repository.dto.AddressDto;


public class AddressService {
	public static void main(String[] args)
	{
		AddressService addressService = new AddressService();
		System.out.println(addressService.read(1));
	}

	public AddressDto read(int id){	

		JPAUtils instance = JPAUtils.getInstance();
		instance.createTransaction();

		AddressDao addressDao = new AddressDao();
		Address address = addressDao.read(id);
		
		AddressDto addressDto = new AddressDto(address.getNumber(),address.getStreet(), address.getZipCode(),address.getTown(),address.getLon(), address.getLat());
		

		instance.finish();
		
		return addressDto;
	}
	
}


