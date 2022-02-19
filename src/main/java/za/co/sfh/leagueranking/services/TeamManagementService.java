package za.co.sfh.leagueranking.services;

import za.co.sfh.leagueranking.models.LeagueTeam;

import java.util.List;

public interface TeamManagementService {

     void addNewTeamResults(LeagueTeam teamA, LeagueTeam teamB);

     LeagueTeam retrieveLeagueTeamDetails(String teamName);

     List<LeagueTeam> findAllTeamDetailsUnsorted();

     List<LeagueTeam> findAllTeamsRankedAndSorted();
}
