package io.javabrains.ipldashboard.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.javabrains.ipldashboard.model.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager EM; // JdbcTemplate jdbcTemplate

    @Autowired
    public JobCompletionNotificationListener(EntityManager EM) { // JdbcTemplate jdbcTemplate
        this.EM = EM;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            // jdbcTemplate.query("SELECT team1,team2,date FROM match", (rs, row) -> "Team1:
            // " + rs.getString(1)
            // + ", Team2: " + rs.getString(2) + ", Date: " + rs.getString(3))
            // .forEach(str -> System.out.println(str));

            Map<String, Team> teamData = new HashMap<>();
            EM.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class).getResultList()
                    .stream().map(m -> new Team((String) m[0], (long) m[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));
            EM.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class).getResultList()
                    .stream().forEach(m -> {
                        Team team = teamData.get((String) m[0]);
                        team.setTotalMatches(team.getTotalMatches() + (long) m[1]);
                    });
            EM.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                    .getResultList().stream().forEach(m -> {
                        Team team = teamData.get((String) m[0]);
                        if (team != null)
                            team.setTotalWins((long) m[1]);
                    });
                    teamData.values().forEach(team -> EM.persist(team));
                    teamData.values().forEach(team -> System.out.println(team));
                }
    }
}