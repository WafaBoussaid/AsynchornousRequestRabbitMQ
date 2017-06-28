package com.karco.interfaces;

import java.util.List;

import com.karco.entities.Address;

public interface AddressBusiness {
	public List<Address> getAddressByFilter(String voie);
	}
