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

import com.project.dlvery.controller.TeamController;
import com.project.dlvery.entity.Team;
import com.project.dlvery.service.TeamService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)

public class TeamControllerTest {
		
		@Autowired
		 MockMvc mockMvc;

		 @Autowired
		 protected WebApplicationContext wac;

		 @Autowired
		 TeamController teamController;

		    @MockBean
		    TeamService teamService;


		    @Before
		    public void setup() throws Exception {
		        this.mockMvc = standaloneSetup(this.teamController).build();// Standalone context
		        // mockMvc = MockMvcBuilders.webAppContextSetup(wac)
		        // .build();
		        
		    }

		    @Test
		    @WithUserDetails("batman")
		    public void testTeamsEnpoint() throws Exception {
		        Team team = new Team();
		        team.setId(1);
		        team.setTeamName("Admin");
		        // Mocking service
		        when(teamService.findById(1)).thenReturn(Optional.of(team));

		        mockMvc.perform(get("/teams/1").contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk());

	}

}
