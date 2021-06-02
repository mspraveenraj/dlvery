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

import com.project.dlvery.controller.UserController;
import com.project.dlvery.entity.User;
import com.project.dlvery.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
	public class UserControllerTest {
	
	@Autowired
	 MockMvc mockMvc;

	    @Autowired
	    protected WebApplicationContext wac;

	    @Autowired
	    UserController userController;

	    @MockBean
	    UserService userService;


	    @Before
	    public void setup() throws Exception {
	        this.mockMvc = standaloneSetup(this.userController).build();// Standalone context
	        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
	        // .build();
	        
	    }

	    @Test
	    @WithUserDetails("batman")
	    public void testUsersEnpoint() throws Exception {
	        User user = new User();
	        user.setId(10);
	        user.setUsername("batman");
	        // Mocking service
	        when(userService.findByUsername("batman")).thenReturn(Optional.of(user));

	        mockMvc.perform(get("/users/10").contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	            
	    }
	    
	    @Test
	    @WithUserDetails("superman")
	    public void testDLTeamEnpoint() throws Exception {
	        User user = new User();
	        user.setId(5);
	        user.setUsername("spiderman");
	        // Mocking service
	        when(userService.findByUsername("spiderman")).thenReturn(Optional.of(user));

	        mockMvc.perform(get("/dlTeam").contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	            
	    }
	    
	    @Test
	    @WithUserDetails("batman")
	    public void testNotFoundEnpoint() throws Exception {
	        
	        // Mocking service
	        //when(userService.findByUsername("batman")).thenReturn(Optional.of(user));

	        mockMvc.perform(get("/userss").contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	            
	    }

}
