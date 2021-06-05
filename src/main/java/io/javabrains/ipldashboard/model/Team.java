package io.javabrains.ipldashboard.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String teamName;
    private long totalMathces;
    private long totalWins;
    @Transient
    private List<Match> matches;

    public Team() {
    }

    public Team(String teamName, long totalMathces) {
        this.teamName = teamName;
        this.totalMathces = totalMathces;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTotalMathces() {
        return totalMathces;
    }

    public void setTotalMathces(long totalMathces) {
        this.totalMathces = totalMathces;
    }

    public long getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(long totalWins) {
        this.totalWins = totalWins;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "Team [teamName=" + teamName + ", totalMathces=" + totalMathces + ", totalWins=" + totalWins + "]";
    }
}
