package com.project.dlvery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dlvery.entity.ProductStatus;
import com.project.dlvery.repository.ProductStatusRepository;

@Service
public class ProductStatusServiceImpl implements ProductStatusService {

	@Autowired
	ProductStatusRepository productStatusRepository;
	@Override
	public List<ProductStatus> listAllProductStatus() {
		
		return productStatusRepository.findAll();
	}

}
