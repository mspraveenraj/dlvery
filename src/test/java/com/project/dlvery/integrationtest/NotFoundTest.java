package com.project.dlvery.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

public class NotFoundTest {
	@Autowired
	 MockMvc mockMvc;


	  @Test (expected = NullPointerException.class)
	  @WithUserDetails("batman")
	  public void testAppErrorEnpoint() throws Exception {
	        
	      mockMvc.perform(get("/abcd").contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());

}
}
