package com.project.dlvery.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.project.dlvery.entity.Inventory;
import com.project.dlvery.repository.InventoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {

	    @Mock
	    private InventoryRepository inventoryRepository;

	    @InjectMocks
	    private InventoryServiceImpl inventoryService;

	    @Test
	    public void whenSaveInventory_shouldReturnInventory() {
	        Inventory inventory = new Inventory();
	        inventory.setProductName("Test");

	        when(inventoryRepository.save(ArgumentMatchers.any(Inventory.class))).thenReturn(inventory);

	        String created = inventoryService.createInventory(inventory);

	        assertThat(created).isSameAs("Inventory record created successfully.");
	        //verify(inventoryRepository.save(inventory));
	    }
	    
	    @Test
	    public void when_Inventory_Present() {
	        Inventory inventory = new Inventory();
	        inventory.setId(1);

	        lenient().when(inventoryRepository.findById(inventory.getId())).thenReturn(Optional.of(inventory));
	        inventoryService.findById(inventory.getId());
	    }
	    
	    @Test
	    public void when_Inventory_doesnt_exist() {
	        Inventory inventory = new Inventory();
	        inventory.setId(0);

	        lenient().when(inventoryRepository.findById(inventory.getId())).thenReturn(Optional.ofNullable(null));
	        inventoryService.findById(inventory.getId());
	    }

	    @Test
	    public void whenGivenId_shouldReturnInventory_ifFound() {
	        Inventory inventory = new Inventory();
	        inventory.setId(5);

	        when(inventoryRepository.findById(inventory.getId())).thenReturn(Optional.of(inventory));

	        Inventory expected = inventoryService.findById(inventory.getId()).get();

	        assertThat(expected).isSameAs(inventory);
	        verify(inventoryRepository).findById(inventory.getId());
	    }

	    @Test
	    public void should_throw_exception_when_Inventory_doesnt_exist() {
	        Inventory inventory = new Inventory();
	        inventory.setId(0);

	        lenient().when(inventoryRepository.findById(0)).thenReturn(Optional.ofNullable(null));
	        inventoryService.findById(inventory.getId());
	    }
	    
	    @Test
	    public void shouldReturnAllInventorys() {
	        List<Inventory> inventories = new ArrayList<>();
	        inventories.add(new Inventory());

	        when(inventoryRepository.findAll()).thenReturn(inventories);

	        List<Inventory> expected = inventoryService.listInventories();

	        assertEquals(expected, inventories);
	        verify(inventoryRepository).findAll();
	    }
	    
	    @Test
	    public void whenGivenId_shouldUpdateInventory_ifFound() {
	        Inventory inventory = new Inventory();
	        inventory.setId(28);
	        inventory.setProductName("Test");

	        Inventory newInventory = new Inventory();
	        newInventory.setProductName("New Test");

	        lenient().when(inventoryRepository.findById(inventory.getId())).thenReturn(Optional.of(inventory));
	        inventoryService.updateInventory(newInventory);

	        verify(inventoryRepository).save(newInventory);
	        //verify(inventoryRepository).findById(inventory.getId());
	    
	}

}
