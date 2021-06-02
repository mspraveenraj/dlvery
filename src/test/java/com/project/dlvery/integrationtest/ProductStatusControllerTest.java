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

import com.project.dlvery.controller.ProductStatusController;
import com.project.dlvery.entity.ProductStatus;
import com.project.dlvery.service.ProductStatusService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductStatusControllerTest {
	
		@Autowired
		 MockMvc mockMvc;

		 @Autowired
		 protected WebApplicationContext wac;

		 @Autowired
		 ProductStatusController productStatusController;

		    @MockBean
		    ProductStatusService productStatusService;


		    @Before
		    public void setup() throws Exception {
		        this.mockMvc = standaloneSetup(this.productStatusController).build();// Standalone context
		        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
		        // .build();
		        
		    }

		    @Test
		    @WithUserDetails("batman")
		    public void testProductStatusEnpoint() throws Exception {
		        ProductStatus productStatus = new ProductStatus();
		        productStatus.setId(7);
		        productStatus.setStatus("Pending");
		        // Mocking service
		        when(productStatusService.getProductStatusById(7)).thenReturn(Optional.of(productStatus));

		        mockMvc.perform(get("/productStatus/7").contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk());

	}

}
