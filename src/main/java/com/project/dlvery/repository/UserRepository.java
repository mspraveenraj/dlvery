package com.project.dlvery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.dlvery.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	Optional<User> findByUsername(String username);

	//@Query(value = "SELECT u.user_id, u.username, u.first_name, u.last_name, u.contact, u.email, u.address_id FROM user u INNER JOIN team t ON u.team_id = t.team_id WHERE t.team_name = 'DLTeam'", nativeQuery = true)
	//List<User> findAllUserByTeamNameDLTeam();
	
	List<User> findByTeam_TeamNameEquals(String teamName);
	//public List<User> findByUsername(String username);
	//User findByFirstName(String firstName);
	//Optional<User> findOneById(Integer id);
	//List<User> findAll();
	@Query(value = "SELECT seq_name.nextval FROM dual", nativeQuery = true)
	int getNextId();

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);


}
