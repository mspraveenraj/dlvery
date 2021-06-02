package com.project.dlvery.integrationtest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.project.dlvery.controller.InventoryController;
import com.project.dlvery.entity.Inventory;
import com.project.dlvery.service.InventoryService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InventoryControllerTest {
		
		@Autowired
		 MockMvc mockMvc;

		    @Autowired
		    protected WebApplicationContext wac;

		    @Autowired
		    InventoryController inventoryController;

		    @MockBean
		    InventoryService inventoryService;


		    @Before
		    public void setup() throws Exception {
		        this.mockMvc = standaloneSetup(this.inventoryController).build();// Standalone context
		        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
		        // .build();
		        
		    }

		   @Test
		   @WithUserDetails("superman")
		    public void testInventoryEnpoint() throws Exception {
		        Inventory inventory = new Inventory();
		        inventory.setId(1);
		        inventory.setProductName("Milk");
		        // Mocking service
		        when(inventoryService.findById(1)).thenReturn(Optional.of(inventory));

		        mockMvc.perform(get("/inventory/1").contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk());
		   
	}

}
