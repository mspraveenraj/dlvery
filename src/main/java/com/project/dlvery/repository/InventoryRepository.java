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

	@Query(value = "SELECT DISTINCT * FROM inventory i INNER JOIN user u "
			+ "ON i.user_id = u.user_id "
			+ "WHERE (u.user_id = ?1) AND (i.product_status_id = 7 OR i.product_status_id = 6) AND (i.product_category_id = 4 OR i.product_category_id = 5)",
			nativeQuery = true)
	List<Inventory> findByDeliveryPriority(int userId);

	@Query(value = "SELECT DISTINCT * FROM inventory i INNER JOIN user u "
			+ "ON i.user_id = u.user_id "
			+ "WHERE (u.user_id = ?1) AND (i.product_status_id = 7 OR i.product_status_id = 6) ",
			nativeQuery = true)
	List<Inventory> findByDeliveryPending(int userId);

	@Query(value = "SELECT DISTINCT * FROM inventory i INNER JOIN user u "
			+ "ON i.user_id = u.user_id "
			+ "WHERE (u.user_id = ?1) ",
			nativeQuery = true)
	List<Inventory> findByDeliveryAll(int userId);
}
