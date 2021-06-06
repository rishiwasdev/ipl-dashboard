package io.javabrains.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.ipldashboard.model.Match;
import io.javabrains.ipldashboard.model.Team;
import io.javabrains.ipldashboard.repository.MatchRepository;
import io.javabrains.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
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

        team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, NUM_OF_LATEST_MATCHES));
        return team;
    }

    @GetMapping("/teams/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
        final LocalDate START_DATE = LocalDate.of(year, 1, 1);
        final LocalDate END_DATE = LocalDate.of(year, 12, 31);
        return this.matchRepository.getMatchesByTeamBetweenDates(teamName, START_DATE, END_DATE);
    }
}
