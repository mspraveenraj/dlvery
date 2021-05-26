package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dlvery.entity.User;
import com.project.dlvery.repository.UserRepository;
import com.project.dlvery.searchspecification.GenericSpecification;
import com.project.dlvery.searchspecification.SearchCriteria;
import com.project.dlvery.searchspecification.SearchOperation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
		
	@Override
	public List<User> listUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public User createUser(User user) {
		return userRepository.save(user);
		
	}

	@Override
	public String updateUser(User user) {
		userRepository.save(user);
		return "User Updated Successfully";
	}

	@Override
	public Optional<User> findById(int id) {
		return  userRepository.findById(id);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public List<User> findAllUserByTeamNameDLTeam() {
		return userRepository.findByTeam_TeamNameEquals("DLTeam");
	}

	@Override
	public List<User> findAllUserByTeamAdmin() {
		return userRepository.findByTeam_TeamNameEquals("Admin");
	}

	@Override
	public int getNextId() {
		return userRepository.getNextId();
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
		
	}


	 @Override
	    public List<User> findUsersWithPredicate(String paramUsername, String paramFirstName, String paramTeamId, String paramEmail) {

	        GenericSpecification genericSpesification = new GenericSpecification<User>();
	        genericSpesification.add(new SearchCriteria("username", paramUsername, SearchOperation.USERNAME));
	        genericSpesification.add(new SearchCriteria("firstName", paramFirstName, SearchOperation.FIRSTNAME));
	        genericSpesification.add(new SearchCriteria("team.id", paramTeamId, SearchOperation.TEAMNAME));
	        genericSpesification.add(new SearchCriteria("email", paramEmail, SearchOperation.EMAIL));
	    
	        return userRepository.findAll(genericSpesification);
	    }

	@Override
	public Boolean checkUsernameUnique(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean checkEmailUnique(String email) {
		return userRepository.existsByEmail(email);
	}

	

}
