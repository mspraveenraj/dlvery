package com.project.dlvery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.dlvery.entity.ProductStatus;
import com.project.dlvery.service.ProductStatusService;

@CrossOrigin
@RestController
@Transactional
public class ProductStatusController {
	
	@Autowired
	ProductStatusService productStatusService;
	
	//listProductStatus
	@RequestMapping(value = "productStatus", method = RequestMethod.GET)
    public List<ProductStatus> listProductStatus(){
    	try {
        return productStatusService.listAllProductStatus();
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return new ArrayList<ProductStatus>();
    }
	
	@RequestMapping(value = "productStatus/{id}", method = RequestMethod.GET)
	public Optional<ProductStatus> getProductStatus(@PathVariable int id) {
		return productStatusService.getProductStatusById(id);
	}
}
