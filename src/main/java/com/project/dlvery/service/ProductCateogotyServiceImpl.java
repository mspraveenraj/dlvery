package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dlvery.entity.ProductCategory;
import com.project.dlvery.repository.ProductCategoryRepository;

@Service
public class ProductCateogotyServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Override
	public List<ProductCategory> listAllProductCategory() {
		
		return productCategoryRepository.findAll();
	}
	@Override
	public Optional<ProductCategory> findById(int id) {

		return productCategoryRepository.findById(id);
	}
	@Override
	public String updateProductCategoy(ProductCategory updatedProductCategory) {

		productCategoryRepository.save(updatedProductCategory);
		
		return "Product Category Updated Successfully" ;
	}
	@Override
	public ProductCategory createProductCategory(ProductCategory productCategory) {
		
		return productCategoryRepository.save(productCategory);
		
	}
	@Override
	public int getNextId() {
		return productCategoryRepository.getNextId();
	}
	@Override
	public void deleteById(int id) {
		productCategoryRepository.deleteById(id);
		
	}

}
