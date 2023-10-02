package com.example.demo.services;

import org.springframework.stereotype.Repository;

import com.example.demo.model.LocationStates;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface CoronaVirusDataServicerepository extends CrudRepository<LocationStates, Integer>{
	 LocationStates findBycountry(String countryname);
}
		
	

