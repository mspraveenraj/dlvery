package com.project.dlvery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.dlvery.entity.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{

	ProductCategory findByName(String name);
	//ProductCategory findOneByName(String name);

	@Query(value = "SELECT seq_name.nextval FROM dual", nativeQuery = true)
	int getNextId();
}
