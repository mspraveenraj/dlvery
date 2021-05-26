package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import com.project.dlvery.entity.ProductCategory;

public interface ProductCategoryService {
	List<ProductCategory> listAllProductCategory ();

	Optional<ProductCategory> findById(int id);

	String updateProductCategoy(ProductCategory updatedProductCategory);

	ProductCategory createProductCategory(ProductCategory productCategory);

	int getNextId();

	void deleteById(int id);
 
}
