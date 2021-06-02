package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.dlvery.entity.User;

@Service
public interface UserService {
	List<User> listUsers();
	User createUser(User user);
	String updateUser(User user);
	Optional<User> findById(int id);
	Optional<User> findByUsername(String username);
	List<User> findAllUserByTeamNameDLTeam();
	List<User> findAllUserByTeamAdmin();
	int getNextId();
	void deleteById(int id);
	void deleteByUsername(String username);
	List<User> findUsersWithPredicate(String paramUsername, String paramFirstName, String paramTeamId, String paramEmail);
	Boolean checkUsernameUnique(String username);
	Boolean checkEmailUnique(String email);

}

