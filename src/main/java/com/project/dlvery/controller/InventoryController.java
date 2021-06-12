package com.project.dlvery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.dlvery.entity.Inventory;
import com.project.dlvery.entity.ProductCategory;
import com.project.dlvery.entity.ProductStatus;
import com.project.dlvery.entity.User;
import com.project.dlvery.repository.ProductCategoryRepository;
import com.project.dlvery.repository.ProductStatusRepository;
import com.project.dlvery.service.InventoryService;
import com.project.dlvery.service.UserService;


@CrossOrigin//(origins = {"http://localhost:3000"})
@RestController
@Transactional
public class InventoryController {
	
	@Autowired
    private InventoryService inventoryService;
	@Autowired
	private ProductStatusRepository  productStatusRepository;
	@Autowired
	private ProductCategoryRepository  productCategoryRepository;
	@Autowired
	private UserService userService;

	//listInventories
    @RequestMapping(value = "inventory", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('InvTeam')")
    public List<Inventory> listInventories(){
    	try {
        return inventoryService.listInventories();
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return new ArrayList<Inventory>();
    }
    
    @RequestMapping(value = "inventory/view", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('InvTeam')")
    public ResponseEntity<Object> viewInventories() {
    	return new ResponseEntity<>(inventoryService.viewAndExportInventories(), HttpStatus.OK);
    }
    
    //listInventories/{id}
    @RequestMapping(value = "inventory/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('InvTeam') or hasAuthority('DLTeam')")
    public Inventory getInventory(@PathVariable int id){
    		
    	Optional<Inventory> inventoryOptional = inventoryService.findById(id);

        if (!inventoryOptional.isPresent())
        	return new Inventory();
        	
        return inventoryOptional.get();
    }
    
    //createInventory
    @RequestMapping(value = "inventory", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('InvTeam')")
    public String createStudent(@RequestBody Inventory inventory){
        return inventoryService.createInventory(inventory);
    }
    
    //updateInventory/{id}
    @RequestMapping(value = "inventory/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('InvTeam')")
    public ResponseEntity<Object> updateInventory(@RequestBody Inventory inventory, @PathVariable int id) {

    	Optional<Inventory> inventoryOptional = inventoryService.findById(id);

    	if (!inventoryOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	Inventory updatedInventory = inventoryOptional.get();
    	updatedInventory.setProductName(inventory.getProductName()); //ProductName
    	updatedInventory.setDateIn(inventory.getDateIn());	//DateIn
    	updatedInventory.setDateOut(inventory.getDateOut());	//DateOut
    	updatedInventory.setExpiryDate(inventory.getExpiryDate()); 	//ExpiryDate
    	//updatedInventory.setUser(inventory.getUser());	//DeliveryAgent
    	updatedInventory.setCustomerName(inventory.getCustomerName());	//CustomerName
    	updatedInventory.setCustomerSignature(inventory.getCustomerSignature());	//CustomerSignature
    	
    	//ProductStatus Update
    	ProductStatus productStatus = productStatusRepository.findByStatus(inventory.getProductStatus().getStatus());
    	if(productStatus!= null)
    	{
    		inventory.getProductStatus().setId(productStatus.getId());
    		updatedInventory.setProductStatus(inventory.getProductStatus());
    	}
    	
    	//DeliveryAgent Update
    	String userName = inventory.getUser().getUsername();
    	Optional<User> user = userService.findByUsername(userName);
    	if(user.isPresent())
    	{	
    		inventory.getUser().setId(user.get().getId());
    		updatedInventory.setUser(inventory.getUser());
    	}
    	
    	//ProductCategory Update
    	ProductCategory productCategory = productCategoryRepository.findByName(inventory.getProductCategory().getName());
    	inventory.getProductCategory().setId(productCategory.getId());
    	updatedInventory.setProductCategory(inventory.getProductCategory());
    	
    	inventoryService.updateInventory(updatedInventory);

    	return ResponseEntity.ok().build();
    }
    
    @RequestMapping(value = "outDelivery/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> outDelivery(@RequestBody Inventory inventory, @PathVariable int id) {

    	Optional<Inventory> inventoryOptional = inventoryService.findById(id);

    	if (!inventoryOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	Inventory updatedInventory = inventoryOptional.get();
    	updatedInventory.setDateOut(inventory.getDateOut());
    	
    	Optional<User> user = userService.findByUsername(inventory.getUser().getUsername());
    	inventory.getUser().setId(user.get().getId());
    	updatedInventory.setUser(inventory.getUser());
    	inventoryService.updateInventory(updatedInventory);

    	return ResponseEntity.ok().build();
    }
    
    
    @RequestMapping(value = "customerUpdate/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> customerUpdate(@RequestBody Inventory inventory, @PathVariable int id) {

    	Optional<Inventory> inventoryOptional = inventoryService.findById(id);

    	if (!inventoryOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	Inventory updatedInventory = inventoryOptional.get();
    	updatedInventory.setCustomerName(inventory.getCustomerName());
    	updatedInventory.setCustomerSignature(inventory.getCustomerSignature());
    	inventoryService.updateInventory(updatedInventory);

    	return ResponseEntity.ok().build();
    }
    
    //productStatusUpdate/{id}
    @RequestMapping(value = "productStatus/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('InvTema') or hasRole('DLTeam')")
    public ResponseEntity<Object> productStatusUpdate(@RequestBody Inventory inventory, @PathVariable int id) {

    	Optional<Inventory> inventoryOptional = inventoryService.findById(id);

    	if (!inventoryOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	Inventory updatedInventory = inventoryOptional.get();
    	
    	//System.out.println(updatedInventory.getProductStatus().getStatus());
    	
    	ProductStatus productStatus = productStatusRepository.findByStatus(inventory.getProductStatus().getStatus());
    	
    	//System.out.println(productStatus.getStatus()+" "+ productStatus.getId());
    	
    	inventory.getProductStatus().setId(productStatus.getId());
    	updatedInventory.setProductStatus(inventory.getProductStatus());
    	
    	//System.out.println(updatedInventory.getProductStatus().getStatus());
    	inventoryService.updateInventory(updatedInventory);

    	return ResponseEntity.ok().build();
    }
    
    // /dlTeam/listInventories/{username}
    @RequestMapping(value = "dlTeam/inventory/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DLTeam')")
    public List<Inventory> dlTeamListInventoriesByUsername(@PathVariable String username) {
    	
    	try {
    			return inventoryService.findByDLTeamUsername(username);
           		//System.out.println(li.get(0).getUser().getUsername());
        	}
        	catch(Exception e)
        	{
        		
        	}
        	return new ArrayList<Inventory>();
    }
    
    // /dlTeam/updateInventory/{id}
    @RequestMapping(value = "dlTeam/inventory/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('DLTeam')")
    public ResponseEntity<Object> dlTeamUpdateInventory(@RequestBody Inventory inventory, @PathVariable int id) {
    	
    	Optional<Inventory> inventoryOptional = inventoryService.findById(id);

    	if (!inventoryOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	Inventory updatedInventory = inventoryOptional.get();
    	updatedInventory.setDateOut(inventory.getDateOut());
    	
    	ProductStatus productStatus = productStatusRepository.findByStatus(inventory.getProductStatus().getStatus());
    	inventory.getProductStatus().setId(productStatus.getId());
    	updatedInventory.setProductStatus(inventory.getProductStatus());
    	
    	updatedInventory.setCustomerName(inventory.getCustomerName());
    	updatedInventory.setCustomerSignature(inventory.getCustomerSignature());
    	inventoryService.updateInventory(updatedInventory);

    	return ResponseEntity.ok().build();
    }
    
    @RequestMapping(value = "inventory/uploadFile", method = RequestMethod.POST) 
    public ResponseEntity<Boolean> uploadFile(@RequestBody MultipartFile file) {
        
    	return new ResponseEntity<>(inventoryService.uploadExcelFile(file), HttpStatus.OK);
  
    }
    
    @RequestMapping(value = "dlTeam/priorityDelivery/{userId}", method = RequestMethod.GET) 
    public ResponseEntity<List<Inventory>> findByUserAndPriorityDelivery(@PathVariable int userId) {
    	return new ResponseEntity<>(inventoryService.findByUserAndPriorityDelivery(userId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "dlTeam/pendingDelivery/{userId}", method = RequestMethod.GET) 
    public ResponseEntity<List<Inventory>> findByUserAndPendingDelivery(@PathVariable int userId) {
    	return new ResponseEntity<>(inventoryService.findByUserAndPendingDelivery(userId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "dlTeam/allDelivery/{userId}", method = RequestMethod.GET) 
    public ResponseEntity<List<Inventory>> findByUserAndAllDelivery(@PathVariable int userId) {
    	return new ResponseEntity<>(inventoryService.findByUserAndAllDelivery(userId), HttpStatus.OK);
    }
    
}
