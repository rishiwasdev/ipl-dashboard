package io.javabrains.ipldashboard.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.javabrains.ipldashboard.model.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    List<Team> findAll();
    Team findByTeamName(String teamName);
}
