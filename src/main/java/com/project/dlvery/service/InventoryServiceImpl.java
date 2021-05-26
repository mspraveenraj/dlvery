package com.project.dlvery.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.dlvery.entity.Inventory;
import com.project.dlvery.entity.InventoryExport;
import com.project.dlvery.entity.ProductCategory;
import com.project.dlvery.entity.ProductStatus;
import com.project.dlvery.entity.User;
import com.project.dlvery.repository.InventoryRepository;
import com.project.dlvery.repository.ProductCategoryRepository;
import com.project.dlvery.repository.ProductStatusRepository;


@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private ProductStatusRepository productStatusRepository;
	@Autowired
	private UserService userService;
	
	int skuId = 100000;
	
	@Override
	public List<Inventory> listInventories() {
		return inventoryRepository.findAll();
	}
	
	@Override
	public String createInventory(Inventory inventory) {
		
		inventory.setSku("SKU"+ (skuId + getNextId() + 1) );
		inventoryRepository.save(inventory);
	    return "Inventory record created successfully.";
	}

	@Override
	public Optional<Inventory> findById(int id) {
		return inventoryRepository.findById(id);
	}

	@Override
	public String updateInventory(Inventory updatedInventory) {
		inventoryRepository.save(updatedInventory);
		return "Inventory Updated Successfully";
	}

	@Override
	public List<Inventory> findByDLTeamUsername(String username) {
		return inventoryRepository.findByUser_UsernameEquals(username);
		
	}
	
	@Override
	public int getNextId() {
		return inventoryRepository.getNextId();
	}

	@Override
	public List<InventoryExport> viewAndExportInventories() {
		List<Inventory> viewInv =  inventoryRepository.findAll();
		List<InventoryExport> inventoryExport = new ArrayList<>();
		for(int i=0; i<viewInv.size(); i++) {
			InventoryExport invExport = new InventoryExport();
			invExport.setId(viewInv.get(i).getId());
			invExport.setSku(viewInv.get(i).getSku());
			invExport.setProductName(viewInv.get(i).getProductName());
			invExport.setProductCategory(viewInv.get(i).getProductCategory().getName());
			invExport.setDateIn(viewInv.get(i).getDateIn());
			invExport.setDateOut(viewInv.get(i).getDateOut());
			invExport.setExpiryDate(viewInv.get(i).getExpiryDate());
			invExport.setProductStatus(viewInv.get(i).getProductStatus().getStatus());
			invExport.setUsername(viewInv.get(i).getUser().getUsername());
			invExport.setCustomerName(viewInv.get(i).getCustomerName());
			invExport.setCustomerSignature(viewInv.get(i).getCustomerSignature());
			invExport.setEntryBy(viewInv.get(i).getEntryBy());
			invExport.setEntryDate(viewInv.get(i).getEntryDate());
			inventoryExport.add(invExport);
		}
		return inventoryExport;
	}
	
	@Override
	 public Boolean uploadExcelFile(MultipartFile file) {
		try {
	    	List<Inventory> inventories = new ArrayList<Inventory>();
	        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	        XSSFSheet worksheet = workbook.getSheetAt(0);
	        
	        int skuIdLoop = skuId + getNextId();
	        	for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
	        		Inventory inventory = new Inventory();
	                
	        		XSSFRow row = worksheet.getRow(i);
	        		
	        		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        		
	        		inventory.setSku("SKU"+ (skuIdLoop + i));
	        		
	        		Cell c = row.getCell(3);
	        		if(c!=null) 
	        			inventory.setProductName(row.getCell(3).getStringCellValue());
	        		c = row.getCell(4);
	        		if(c!=null) {
	        			ProductCategory productCategory = productCategoryRepository.findByName(row.getCell(4).getStringCellValue());
	        			inventory.setProductCategory(productCategory);
	        		}
	        		c = row.getCell(5);
	        		if(c!=null) {
	        			ProductStatus productStatus = productStatusRepository.findByStatus(row.getCell(5).getStringCellValue());
	        			inventory.setProductStatus(productStatus);
	        		}
	        		c = row.getCell(6);
	        		if(c!=null) 
	        			inventory.setDateIn(simpleDateFormat.format(row.getCell(6).getDateCellValue()));
	        		c = row.getCell(7);
	        		if(c!=null)
	        			inventory.setDateOut(simpleDateFormat.format(row.getCell(7).getDateCellValue()));
	        		c = row.getCell(8);
	        		if(c!=null)
	        			inventory.setExpiryDate(simpleDateFormat.format(row.getCell(8).getDateCellValue()));
	        		c = row.getCell(9);
	        		if(c!=null)
	        		{
	        			Optional<User> user = userService.findByUsername(row.getCell(9).getStringCellValue());
	        			if(user.isPresent())
	        				inventory.setUser(user.get());
	        		}
	            
	        		inventories.add(inventory);   
	        	}
	        	workbook.close();
	        	inventoryRepository.saveAll(inventories);
	        	return true;//new ResponseEntity<>(inventories, HttpStatus.OK);
	    	}
	    	catch(Exception e) {
	    		return false;//new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
	    	}
	}
}
