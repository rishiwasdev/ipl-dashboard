package io.javabrains.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.javabrains.ipldashboard.model.Match;

// @Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    // 1st METHOD OF 2 ALTERNATIVES (*** COMMENTED BCOZ OF LENGHTY BIZARRE NAME ***)
    // List<Match>
    // getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(String
    // teamName1, LocalDate START_DATE1,
    // LocalDate END_DATE1, String teamName2, LocalDate START_DATE2, LocalDate
    // END_DATE2);

    // 2nd METHOD OF 2 ALTERNATIVES
    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :dateStart and :dateEnd order by date desc")
    List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
            @Param("dateStart") LocalDate START_DATE, @Param("dateEnd") LocalDate END_DATE1);

    default List<Match> findLatestMatchesByTeam(String teamName, int count) {
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
