package io.javabrains.ipldashboard.data;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import io.javabrains.ipldashboard.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        final Match match = new Match();
        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());

        // Set Team-1 and Team-2 depending on the innings order
        String firstInningsTeam = null, secondInningsTeam = null;
        final String TOSS_WINNER = matchInput.getToss_winner(), TOSS_DECISION = matchInput.getToss_decision();
        final String TEAM1 = matchInput.getTeam1(), TEAM2 = matchInput.getTeam2();
        if ("bat".equalsIgnoreCase(TOSS_DECISION)) {
            firstInningsTeam = TOSS_WINNER;
            secondInningsTeam = firstInningsTeam.equalsIgnoreCase(TEAM1) ? TEAM2 : TEAM1;
        } else {
            secondInningsTeam = TOSS_WINNER;
            firstInningsTeam = secondInningsTeam.equalsIgnoreCase(TEAM1) ? TEAM2 : TEAM1;
        }
        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);

        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setResult(matchInput.getResult());
        match.setMatchWinner(matchInput.getWinner());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        return match;
    }

}