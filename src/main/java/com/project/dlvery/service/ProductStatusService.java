package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import com.project.dlvery.entity.ProductStatus;

public interface ProductStatusService {
	List<ProductStatus> listAllProductStatus();

	Optional<ProductStatus> getProductStatusById(int id);
	
}
