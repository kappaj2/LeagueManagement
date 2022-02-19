package za.co.sfh.leagueranking.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import za.co.sfh.leagueranking.exceptions.LeagueTeamNotFound;
import za.co.sfh.leagueranking.models.LeagueTeam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Test the Team management class")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class TeamManagementServiceImplTest {

     @Autowired
     private TeamManagementService teamManagementService;

     @DisplayName("Test adding two teams with team a losing to team b")
     @Test
     void addNewTeamResults_teamA_losing_teamB() {
          var teamAName = "TEAM_A";
          var teamBName = "TEAM_B";
          var teamAScore = 10;
          var teamBScore = 50;

          var teamA = LeagueTeam.builder()
                  .teamName(teamAName)
                  .teamScore(teamAScore)
                  .build();

          var teamB = LeagueTeam.builder()
                  .teamName(teamBName)
                  .teamScore(teamBScore)
                  .build();

          teamManagementService.addNewTeamResults(teamA, teamB);

          LeagueTeam leagueTeamA = teamManagementService.retrieveLeagueTeamDetails(teamAName);
          LeagueTeam leagueTeamB = teamManagementService.retrieveLeagueTeamDetails(teamBName);

          assertEquals(0, leagueTeamA.getTeamPoints());
          assertEquals(3, leagueTeamB.getTeamPoints());
     }

     @DisplayName("Test adding two teams with team a winning team b")
     @Test
     void addNewTeamResults_teamA_winning_teamB() {
          var teamAName = "TEAM_A";
          var teamBName = "TEAM_B";
          var teamAScore = 45;
          var teamBScore = 23;

          var teamA = LeagueTeam.builder()
                  .teamName(teamAName)
                  .teamScore(teamAScore)
                  .build();

          var teamB = LeagueTeam.builder()
                  .teamName(teamBName)
                  .teamScore(teamBScore)
                  .build();

          teamManagementService.addNewTeamResults(teamA, teamB);

          var leagueTeamA = teamManagementService.retrieveLeagueTeamDetails(teamAName);
          var leagueTeamB = teamManagementService.retrieveLeagueTeamDetails(teamBName);

          assertEquals(3, leagueTeamA.getTeamPoints());
          assertEquals(0, leagueTeamB.getTeamPoints());
     }

     @DisplayName("Test adding two teams with team a drawing with team b")
     @Test
     void addNewTeamResults_teamA_drawing_with_teamB() {
          var teamAName = "TEAM_A";
          var teamBName = "TEAM_B";
          var teamAScore = 15;
          var teamBScore = 15;

          var teamA = LeagueTeam.builder()
                  .teamName(teamAName)
                  .teamScore(teamAScore)
                  .build();

          var teamB = LeagueTeam.builder()
                  .teamName(teamBName)
                  .teamScore(teamBScore)
                  .build();

          teamManagementService.addNewTeamResults(teamA, teamB);

          LeagueTeam leagueTeamA = teamManagementService.retrieveLeagueTeamDetails(teamAName);
          LeagueTeam leagueTeamB = teamManagementService.retrieveLeagueTeamDetails(teamBName);

          assertEquals(1, leagueTeamA.getTeamPoints());
          assertEquals(1, leagueTeamB.getTeamPoints());
     }

     @Test
     @DisplayName("Test retrieval of unknown team")
     void testRetrieval_unknown_team() {

          var teamName = "BlouBulle";
          assertThrows(LeagueTeamNotFound.class, () -> teamManagementService.retrieveLeagueTeamDetails(teamName));
     }
}