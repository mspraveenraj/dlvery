package com.project.dlvery.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.dlvery.entity.ProductCategory;
import com.project.dlvery.service.ProductCategoryService;

@CrossOrigin
@RestController
@Transactional
public class ProductCategoryController {
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	//listProductCategory
	@RequestMapping(value = "productCategory", method = RequestMethod.GET)
    public ResponseEntity<List<ProductCategory>> listProductCategory(){
    	try {
        	List<ProductCategory> allProductCategory = productCategoryService.listAllProductCategory();
    	
    	
    	return new ResponseEntity<>(allProductCategory, HttpStatus.OK);
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return ResponseEntity.notFound().build();
    }
	
	@RequestMapping(value = "productCategory/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable int id){
    
		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(id);

    	if (!productCategoryOptional.isPresent())
    		return new ResponseEntity<>(new ProductCategory(), HttpStatus.OK);
		
    
    	return new ResponseEntity<>(productCategoryOptional.get(), HttpStatus.OK);
    
    }
	
	@RequestMapping(value = "productCategory/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody ProductCategory productCategory, @PathVariable int id){
		
		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(id);

    	if (!productCategoryOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	ProductCategory updatedProductCategory = productCategoryOptional.get();
    	updatedProductCategory.setName(productCategory.getName());
     	
    	productCategoryService.updateProductCategoy(updatedProductCategory);

    	return new ResponseEntity<>(updatedProductCategory, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "productCategory", method = RequestMethod.POST)
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory){
        
		ProductCategory newProductCategory = new ProductCategory();
		if(productCategory.getName() == null)
		{
			newProductCategory.setId(productCategoryService.getNextId());
		}
		else {
			newProductCategory = productCategoryService.createProductCategory(productCategory);
		}
		return new ResponseEntity<>(newProductCategory, HttpStatus.OK);
    }
	
	@RequestMapping(value = "productCategory/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<ProductCategory> deleteProductCategoryById(@PathVariable int id){
		Optional<ProductCategory> productCategoryOptional = productCategoryService.findById(id);

    	if (!productCategoryOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	
    	productCategoryService.deleteById(id);
    	for(int i = id-1; i>0 ; i--)
    	{
    		Optional<ProductCategory> prevproductCategory = productCategoryService.findById(id);
    		if(prevproductCategory.isPresent()) {
    			return new ResponseEntity<>(prevproductCategory.get(), HttpStatus.OK);
    		}
    	}
    	ProductCategory emptyProductCategory = new ProductCategory();
    	emptyProductCategory.setId(-1);
    	return new ResponseEntity<>(emptyProductCategory, HttpStatus.OK);
	}

}
