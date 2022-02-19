package za.co.sfh.leagueranking.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.sfh.leagueranking.exceptions.LeagueTeamNotFound;
import za.co.sfh.leagueranking.models.LeagueTeam;
import za.co.sfh.leagueranking.persistence.LeagueTeamResultsData;
import za.co.sfh.leagueranking.utilities.ScoreRankingUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamManagementServiceImpl implements TeamManagementService {

     private final ScoreRankingUtil scoreRankingUtil;
     private final LeagueTeamResultsData leagueTeamResultsData;

     /**
      * Add a new team result to the system. If the team exists, then the results will be added to the existing date, else a new record will be
      * created
      *
      * @param teamA
      * @param teamB
      */
     @Override
     public void addNewTeamResults(LeagueTeam teamA, LeagueTeam teamB) {

          scoreTeamResults(teamA, teamB);
          addTeamResults(teamA);
          addTeamResults(teamB);
     }


     /**
      * Retrieve the league team using the name as key.
      *
      * @param teamName The team name to lookup.
      * @return An optional of the team information.
      */
     @Override
     public LeagueTeam retrieveLeagueTeamDetails(String teamName) {
          var leagueTeamOpt = leagueTeamResultsData.getLeagueTeam(teamName);
          if (leagueTeamOpt.isPresent()) {
               return leagueTeamOpt.get();
          }
          throw new LeagueTeamNotFound(String.format("Unable to find team with name %s", teamName));
     }

     /**
      * Retrieve all the teams - unsorted!
      *
      * @return Returns a list of all the teams unsorted.
      */
     @Override
     public List<LeagueTeam> findAllTeamDetailsUnsorted() {
          return leagueTeamResultsData.getAllLeagueTeams();
     }

     /**
      * Retrieve all the team, sorted by ranking and the team name.
      *
      * @return List of teams.
      */
     @Override
     public List<LeagueTeam> findAllTeamsRankedAndSorted() {

          var allLeagueTeamsList = leagueTeamResultsData.getAllLeagueTeams();
          allLeagueTeamsList.forEach(entry ->
                  scoreRankingUtil.addNewScore(entry.getTeamPoints())
          );

          /*
               So we now have all the team points ranked. Assign to the different teams.
           */
          allLeagueTeamsList.forEach(entry -> {
               int scoreRanking = scoreRankingUtil.getScoreRanking(entry.getTeamPoints());
               entry.setRanking(scoreRanking);
          });

          /*
               Now order via ranking and then name
           */
          var compareByRankAndName = Comparator
                  .comparing(LeagueTeam::getRanking)
                  .thenComparing(LeagueTeam::getTeamName);

          var sortedLeagueTeamList = allLeagueTeamsList.stream()
                  .sorted(compareByRankAndName)
                  .collect(Collectors.toList());

          log.info("========================== Task formatted output begin ==========================");
          sortedLeagueTeamList.forEach(entry ->
                  log.info("{}. {}, {} {}", entry.getRanking(), entry.getTeamName(), entry.getTeamPoints(), entry.getTeamPoints() == 1 ? "pt" : "pts")
          );
          log.info("========================== Task formatted output end ==========================");

          return sortedLeagueTeamList;
     }

     /**
      * Add team results to the data store.
      *
      * @param team The team to persist.
      */
     private void addTeamResults(LeagueTeam team) {

          var leagueTeamOpt = leagueTeamResultsData.getLeagueTeam(team.getTeamName());

          if (leagueTeamOpt.isPresent()) {
               var leagueTeam = leagueTeamOpt.get();
               leagueTeam.setTeamPoints(leagueTeam.getTeamPoints() + team.getTeamPoints());
               leagueTeamResultsData.updateLeagueTeamScore(leagueTeam);
          } else {
               leagueTeamResultsData.addLeagueTeam(team);
          }
     }

     /**
      * Score the team results after a game.
      *
      * @param teamA The first team.
      * @param teamB The second team.
      * @return The score results containing each team name and it score.
      */
     private void scoreTeamResults(LeagueTeam teamA, LeagueTeam teamB) {

          if (teamA.getTeamScore() < teamB.getTeamScore()) {
               teamA.setTeamPoints(0);
               teamB.setTeamPoints(3);
               return;
          }

          if (teamA.getTeamScore() > teamB.getTeamScore()) {
               teamA.setTeamPoints(3);
               teamB.setTeamPoints(0);
               return;
          }

          teamA.setTeamPoints(1);
          teamB.setTeamPoints(1);
     }
}
