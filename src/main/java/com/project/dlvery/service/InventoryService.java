package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.project.dlvery.entity.Inventory;
import com.project.dlvery.entity.InventoryExport;

public interface InventoryService {
	List<Inventory> listInventories();

	String createInventory(Inventory inventory);

	Optional<Inventory> findById(int id);

	String updateInventory(Inventory updatedInventory);

	List<Inventory> findByDLTeamUsername(String username);
	
	int getNextId();

	List<InventoryExport> viewAndExportInventories();

	Boolean uploadExcelFile(MultipartFile file);

	List<Inventory> findByUserAndPriorityDelivery(int userId);

	List<Inventory> findByUserAndPendingDelivery(int userId);

	List<Inventory> findByUserAndAllDelivery(int userId);
}
