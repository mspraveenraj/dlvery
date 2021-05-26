package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import com.project.dlvery.entity.Team;

public interface TeamService {
	
	List<Team> listAllTeams();

	Optional<Team> findById(int id);

	String updateTeam(Team updatedTeam);

	Team createTeam(Team team);

	int getNextId();

	void deleteById(int id);

}
