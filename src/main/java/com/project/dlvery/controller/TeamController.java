package com.project.dlvery.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.dlvery.entity.Team;
import com.project.dlvery.service.TeamService;

@CrossOrigin//(origins = {"http://localhost:3000"})
@RestController
@Transactional
public class TeamController {
	@Autowired
    private TeamService teamService;

	//listAllTeams
    @RequestMapping(value = "teams", method = RequestMethod.GET)
    ResponseEntity<List<Team>> listAllTeams(){
        List<Team> allTeams = teamService.listAllTeams();
    	
    	//CORS issue so adding props to response headers
    	//HttpHeaders response = new HttpHeaders();
    	//response.add("Access-Control-Expose-Headers", "X-Total-Count");
    	//response.add("X-Total-Count", String.valueOf(allTeams.size()));
    	
    	return new ResponseEntity<>(allTeams, HttpStatus.OK);
    }
    
       
    @RequestMapping(value = "teams/{id}", method = RequestMethod.GET)
    public ResponseEntity<Team> getTeamById(@PathVariable int id){
        
    	Optional<Team> teamOptional = teamService.findById(id);
    	
    	if (!teamOptional.isPresent())
    		return new ResponseEntity<>(new Team(), HttpStatus.OK);
    		
    	return new ResponseEntity<>(teamOptional.get(), HttpStatus.OK);
    	
    }
    
    @RequestMapping(value = "teams/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Team> updateTeam(@RequestBody Team team, @PathVariable int id){
		
		Optional<Team> teamOptional = teamService.findById(id);

    	if (!teamOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	Team updatedTeam = teamOptional.get();
    	updatedTeam.setTeamName(team.getTeamName());
     	
    	teamService.updateTeam(updatedTeam);

    	return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }
    
    @RequestMapping(value = "teams", method = RequestMethod.POST)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Team> createTeam(@RequestBody Team team){
    	
    	Team newTeam = new Team();
    	if(team.getTeamName() == null)
    	{
    		newTeam.setId(teamService.getNextId());
    	}
    	else {
    		newTeam = teamService.createTeam(team);
    	}
        return new ResponseEntity<>(newTeam, HttpStatus.OK);
    }
    
    @RequestMapping(value = "teams/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Team> deleteTeamById(@PathVariable int id){
		Optional<Team> teamOptional = teamService.findById(id);

    	if (!teamOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	
    	teamService.deleteById(id);
    	for(int i = id-1; i>0 ; i--)
    	{
    		Optional<Team> prevTeam = teamService.findById(id);
    		if(prevTeam.isPresent()) {
    
    			return new ResponseEntity<>(prevTeam.get(), HttpStatus.OK);
    		}
    	}
    	Team emptyTeam = new Team();
    	emptyTeam.setId(-1);
    	return new ResponseEntity<>(emptyTeam, HttpStatus.OK);
	}
}
