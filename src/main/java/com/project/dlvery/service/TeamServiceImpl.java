package com.project.dlvery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dlvery.entity.Team;
import com.project.dlvery.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepository;
	
	@Override
	public List<Team> listAllTeams() {
		
		return teamRepository.findAll();
	}

	@Override
	public Optional<Team> findById(int id) {
	
		return teamRepository.findById(id);
	}

	@Override
	public String updateTeam(Team updatedTeam) {
	
		teamRepository.save(updatedTeam);
		return "Team Updated Successfully";
	}

	@Override
	public Team createTeam(Team team) {
		 return teamRepository.save(team);
	}

	@Override
	public int getNextId() {
		return teamRepository.getNextId();
	}

	@Override
	public void deleteById(int id) {
		teamRepository.deleteById(id);
		
	}
	
}
