package com.project.dlvery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dlvery.entity.ProductStatus;

@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatus, Integer>{

	ProductStatus findByStatus(String status);
	//ProductStatus findOneByStatus(String status);
}
