package com.project.dlvery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.dlvery.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	//Address findOneByCity(String city);
	//Address findOneByState(String state);
}
