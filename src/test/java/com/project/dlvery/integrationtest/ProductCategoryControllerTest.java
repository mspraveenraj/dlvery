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

import com.project.dlvery.controller.ProductCategoryController;
import com.project.dlvery.entity.ProductCategory;
import com.project.dlvery.service.ProductCategoryService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductCategoryControllerTest {
	
		@Autowired
		 MockMvc mockMvc;

		 @Autowired
		 protected WebApplicationContext wac;

		 @Autowired
		 ProductCategoryController productCategoryController;

		    @MockBean
		    ProductCategoryService productCategoryService;


		    @Before
		    public void setup() throws Exception {
		        this.mockMvc = standaloneSetup(this.productCategoryController).build();// Standalone context
		        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
		        // .build();
		        
		    }

		    @Test
		    @WithUserDetails("batman")
		    public void testProductCategoryEnpoint() throws Exception {
		        ProductCategory productCategory = new ProductCategory();
		        productCategory.setId(3);
		        productCategory.setName("Essential");
		        // Mocking service
		        when(productCategoryService.findById(3)).thenReturn(Optional.of(productCategory));

		        mockMvc.perform(get("/productCategory/3").contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk());

	}

}
