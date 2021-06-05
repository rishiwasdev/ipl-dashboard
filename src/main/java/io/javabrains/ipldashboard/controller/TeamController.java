package io.javabrains.ipldashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.ipldashboard.model.Team;
import io.javabrains.ipldashboard.repository.MatchRepository;
import io.javabrains.ipldashboard.repository.TeamRepository;

@RestController
public class TeamController {
    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    // @Autowired
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        final int NUM_OF_LATEST_MATCHES = 4;

        // Pageable pageable = PageRequest.of(0, 4);
        // team.setMatches(matchRepository.getByTeam1OrTeam2OrderByDateDesc(teamName,teamName,pageable));
        // domain.PageRequest(above) in Controller(Tightly-coupled) -> Bad practice.
        // Creating defualt method in Repo, AND use that as below:

        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName, NUM_OF_LATEST_MATCHES));
        return team;
    }
}
