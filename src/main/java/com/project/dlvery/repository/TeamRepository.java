package com.project.dlvery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.dlvery.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>{

	@Query(value = "SELECT seq_name.nextval FROM dual", nativeQuery = true)
	int getNextId();
	//Team findOneByTeamName(String teamName);

	Optional<Team> findByTeamName(String string);
}
