package com.project.dlvery.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dlvery.entity.Address;
import com.project.dlvery.entity.Team;
import com.project.dlvery.entity.User;
import com.project.dlvery.exception.ResourceNotFoundException;
import com.project.dlvery.security.CurrentUser;
import com.project.dlvery.security.UserPrincipal;
import com.project.dlvery.service.TeamService;
import com.project.dlvery.service.UserService;

@CrossOrigin//(origins = {"http://localhost:3000"})
@RestController
@Transactional
public class UserController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @RequestMapping(value = "info", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('DLTeam')") //or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String info(){
        return "The application is up...";
    }
    
    @GetMapping("/user/me")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('InvTeam') or hasAuthority('DLTeam')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        
    	Optional<User> userOptional = userService.findById(userPrincipal.getId());

    	if (!userOptional.isPresent())
    		 throw new ResourceNotFoundException("User", "id", userPrincipal.getId());
    	return userOptional.get();
         
    }
    
    //listUsers
    //users?_end=10&_order=ASC&_sort=id&_start=0&firstName=Boss&q=reger&team.teamName=crashh&username=ra&users.username=ra
    @RequestMapping(value = "users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<User>> listUsers(@RequestParam Map<String,String> allParams){
    	try {
    		
    		//Set params = allParams.entrySet();
    		String paramUsername = allParams.get("username");
    		String paramFirstName = allParams.get("firstName");
    		String paramTeamId = allParams.get("team.id");
    		String paramEmail = allParams.get("email");
    		
    		if(paramUsername != null || paramFirstName !=null || paramTeamId != null || paramEmail != null)
    		{
    			List<User> paramsList = userService.findUsersWithPredicate(paramUsername, paramFirstName, paramTeamId, paramEmail);
    			return new ResponseEntity<>(paramsList, HttpStatus.OK);
    		}
    		
    		List<User> listUsers = 
    				userService.listUsers();
    	
    		//CORS issue so adding props to response headers
    		//HttpHeaders response = new HttpHeaders();
    		//response.add("Access-Control-Expose-Headers", "X-Total-Count");
    		//response.add("X-Total-Count", String.valueOf(listUsers.size()));
    	
    		return new ResponseEntity<>(listUsers, HttpStatus.OK);
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return ResponseEntity.notFound().build();
    }
    
    @RequestMapping(value = "users", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<User> createUser(@RequestBody User user){
        
    	User newUser = new User();
    	if(user.getUsername() == null)
    	{
    		newUser.setId(userService.getNextId());
    	}
    	else if( userService.checkUsernameUnique(user.getUsername())) {
    		return new ResponseEntity<>( HttpStatus.CONFLICT);
    	}
    	else if(userService.checkEmailUnique(user.getEmail())) {
    		return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
    	}
    	else {
    		newUser = userService.createUser(user);
    	}
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    	
    }
    
    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('Admin')")
    public User getUser(@PathVariable int id){
    	Optional<User> userOptional = userService.findById(id);

    	if (!userOptional.isPresent())
    		return new User();
    	
    	return userOptional.get();
    }
    
    
    //updateUser/{id}
    @RequestMapping(value = "users/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id) {

    	Optional<User> userOptional = userService.findById(id);

    	if (!userOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	User updatedUser = userOptional.get();
    	updatedUser.setContact(user.getContact());
    	updatedUser.setEmail(user.getEmail());
    	updatedUser.setFirstName(user.getFirstName());
    	updatedUser.setLastName(user.getLastName());
    	updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
    	
    	Optional<Team> teamOptional = teamService.findById(user.getTeam().getId());
    	if(!teamOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	
    	updatedUser.setTeam(teamOptional.get());
    	
    	userService.updateUser(updatedUser);

    	return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @RequestMapping(value = "users/changePassword/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> changePassword(@RequestBody User user, @PathVariable int id) {

    	Optional<User> userOptional = userService.findById(id);

    	if (!userOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	User updatedUser = userOptional.get();
    	updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
    	userService.updateUser(updatedUser);

    	return ResponseEntity.ok().build();
    }
    
    @RequestMapping(value = "users/changeAddress/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> changeAddress(@RequestBody Address address, @PathVariable int id) {

    	Optional<User> userOptional = userService.findById(id);

    	if (!userOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	User updatedUser = userOptional.get();
    	updatedUser.setAddress(address);
    	userService.updateUser(updatedUser);

    	return ResponseEntity.ok().build();
    }
    
    //listAllDLTeam
    @RequestMapping(value = "dlTeam", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('InvTeam')")
    public List<User> listAllDLTeam () {
    	return userService.findAllUserByTeamNameDLTeam();
    }
    
    //listAllAdminTeam
    @RequestMapping(value = "adminTeam", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllAdminTeam () {
    	List<User> adminTeams = userService.findAllUserByTeamAdmin();
    	
    	//CORS issue so adding props to response headers
    	//HttpHeaders response = new HttpHeaders();
    	//response.add("Access-Control-Expose-Headers", "X-Total-Count");
    	//response.add("X-Total-Count", String.valueOf(adminTeams.size()));
    	
    	return new ResponseEntity<>(adminTeams, HttpStatus.OK);
    }
    
    @RequestMapping(value = "users/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<User> deleteUserById(@PathVariable int id){
		Optional<User> userOptional = userService.findById(id);

    	if (!userOptional.isPresent())
    		return ResponseEntity.notFound().build();
    	
    	userService.deleteById(id);
    	for(int i = id-1; i>0 ; i--)
    	{
    		Optional<User> prevUser = userService.findById(id);
    		if(prevUser.isPresent()) {
    
    			return new ResponseEntity<>(prevUser.get(), HttpStatus.OK);
    		}
    	}
    	User emptyUser = new User();
    	emptyUser.setId(-1);
    	return new ResponseEntity<>(emptyUser, HttpStatus.OK);
	}
    
    @RequestMapping(value ="users/{username}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<User> deleteUserByUsername(@PathVariable String username){
    	 userService.deleteByUsername(username);
    	 return new ResponseEntity<>( HttpStatus.OK);
    }
    
    @RequestMapping(value = "users/checkUsernameUnique/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Boolean> checkUsernameIsUnique(@PathVariable String username){
    	return new ResponseEntity<>(userService.checkUsernameUnique(username), HttpStatus.OK);
    }
    
    @RequestMapping(value = "users/checkEmailUnique/{email}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Boolean> checkEmailIsUnique(@PathVariable String email){
    	return new ResponseEntity<>(userService.checkEmailUnique(email), HttpStatus.OK);
    }
    
  
    
}
