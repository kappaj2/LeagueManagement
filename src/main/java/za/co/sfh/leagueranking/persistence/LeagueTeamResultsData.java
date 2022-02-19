package za.co.sfh.leagueranking.persistence;

import org.springframework.stereotype.Component;
import za.co.sfh.leagueranking.exceptions.LeagueTeamNotFound;
import za.co.sfh.leagueranking.models.LeagueTeam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This is a stand-in replacement for persistent data to a datastore of choice.
 * For this implementation we only keep a single record of the team using the team name as key.
 * Team points are incremented after each game.
 */
@Component
public class LeagueTeamResultsData {

     private Map<String, LeagueTeam> leagueTeamsMap = new ConcurrentHashMap<>();

     public Optional<LeagueTeam> getLeagueTeam(String teamName) {
          return Optional.ofNullable(leagueTeamsMap.get(teamName));
     }

     public List<LeagueTeam> getAllLeagueTeams() {
          return leagueTeamsMap.values().stream()
                  .collect(Collectors.toList());
     }

     public void addLeagueTeam(LeagueTeam leagueTeam) {
          leagueTeamsMap.put(leagueTeam.getTeamName(), leagueTeam);
     }

     public void updateLeagueTeamScore(LeagueTeam leagueTeam) {

          Optional<LeagueTeam> leagueTeamOpt = getLeagueTeam(leagueTeam.getTeamName());

          if (leagueTeamOpt.isPresent()) {
               var leagueTeam1 = leagueTeamOpt.get();
               leagueTeam1.setTeamPoints(leagueTeam1.getTeamPoints() + leagueTeam.getTeamPoints());
               leagueTeamsMap.replace(leagueTeam.getTeamName(), leagueTeam1);
          } else {
               throw new LeagueTeamNotFound(String.format("Cannot find league team %s", leagueTeam.getTeamName()));
          }
     }
}
