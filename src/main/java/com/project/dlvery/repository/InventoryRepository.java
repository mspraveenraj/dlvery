package com.project.dlvery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.dlvery.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer>{

	List<Inventory> findByUser_UsernameEquals(String username);

	@Query(value = "SELECT MAX(inventory_id) FROM inventory", nativeQuery = true)
	int getNextId();

}
