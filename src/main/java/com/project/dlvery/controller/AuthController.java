package com.project.dlvery.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.dlvery.entity.AuthProvider;
import com.project.dlvery.entity.Team;
import com.project.dlvery.entity.User;
/*
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
*/
import com.project.dlvery.exception.BadRequestException;
import com.project.dlvery.payload.ApiResponse;
import com.project.dlvery.payload.AuthResponse;
import com.project.dlvery.payload.LoginRequest;
import com.project.dlvery.payload.SignUpRequest;
import com.project.dlvery.repository.TeamRepository;
import com.project.dlvery.repository.UserRepository;
import com.project.dlvery.security.TokenProvider;

	@RestController
	@RequestMapping("/auth")
	public class AuthController {

	    @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private TeamRepository teamRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private TokenProvider tokenProvider;

	    @PostMapping("/login")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getUsername(),
	                        loginRequest.getPassword()
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String token = tokenProvider.createToken(authentication);
	        return ResponseEntity.ok(new AuthResponse(token));
	    }

//	    @PostMapping("/signup")
//	    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//	        
//	    	if(userRepository.existsByUsername(signUpRequest.getUsername())) {
//	            throw new BadRequestException("Username already in use.");
//	        }
//	    	if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//	            throw new BadRequestException("Email address already in use.");
//	        }
//
//	        // Creating user's account
//	        User user = new User();
//	        user.setUsername(signUpRequest.getUsername());
//	        user.setFirstName(signUpRequest.getFirstName());
//	        user.setLastName(signUpRequest.getLastName());
//	        user.setEmail(signUpRequest.getEmail());
//	        user.setPassword(signUpRequest.getPassword());
//	        user.setContact(signUpRequest.getContact());
//	        user.setProvider(AuthProvider.local);
//
//	        user.setPassword(passwordEncoder.encode(user.getPassword()));
//	        
//	        //Team team = new Team();
//	        Optional<Team> teamOptional = teamRepository.findByTeamName(signUpRequest.getTeamName());
//	        if(!teamOptional.isPresent())
//	        	throw new BadRequestException("Please fill Team");
//	        user.setTeam(teamOptional.get());
//	        
//	        User result = userRepository.save(user);
//
//	        URI location = ServletUriComponentsBuilder
//	                .fromCurrentContextPath().path("/user/me")
//	                .buildAndExpand(result.getId()).toUri();
//
//	        return ResponseEntity.created(location)
//	                .body(new ApiResponse(true, "User registered successfully@"));
//	    }

	}
	
	
	
	
	/*@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> teams = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 teams));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strTeams = signUpRequest.getTeams();
		Set<Team> teams = new HashSet<>();
		Team userTeam = new Team();
		if (strTeams == null) {
			//Team userTeam = teamRepository.findByTeamName("InvTeam")
				//	.orElseThrow(() -> new RuntimeException("Error: Team is not found."));
			//teams.add(userTeam);
			new RuntimeException("Error: Team is not found.");
		} else {
			strTeams.forEach(team -> {
				switch (team) {
				case "Admin":
					Team adminTeam = teamRepository.findByTeamName("Admin")
							.orElseThrow(() -> new RuntimeException("Error: Team is not found."));
					teams.add(adminTeam);
					userTeam.setId(adminTeam.getId());
					userTeam.setTeamName(adminTeam.getTeamName());
					break;
				case "InvTeam":
					Team invTeam = teamRepository.findByTeamName("InvTeam")
						.orElseThrow(() -> new RuntimeException("Error: Team is not found."));
					teams.add(invTeam);
					userTeam.setId(invTeam.getId());
					userTeam.setTeamName(invTeam.getTeamName());
					break;
				case "DLTeam":
					Team dlTeam = teamRepository.findByTeamName("DLTeam")
							.orElseThrow(() -> new RuntimeException("Error: Team is not found."));
					teams.add(dlTeam);
					userTeam.setId(dlTeam.getId());
					userTeam.setTeamName(dlTeam.getTeamName());
					break;
				default:
					//Team userRole = teamRepository.findByTeamName("DLTeam")
					//		.orElseThrow(() -> new RuntimeException("Error: Team is not found."));
					//teams.add(userRole);
					new RuntimeException("Error: Team is not found.");
				}
			});
		}

		user.setTeams(teams);
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setContact(signUpRequest.getContact());
		user.setAddress(signUpRequest.getAddress());
		user.setTeam(userTeam);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}*/