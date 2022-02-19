package za.co.sfh.leagueranking.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.sfh.leagueranking.models.LeagueTeam;
import za.co.sfh.leagueranking.services.TeamManagementService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

     private final TeamManagementService teamManagementService;

     @GetMapping("/team/{team-name}")
     public ResponseEntity<LeagueTeam> findTeamInfoByName(@PathVariable(name = "team-name") final String teamName) {
          LeagueTeam leagueTeam = teamManagementService.retrieveLeagueTeamDetails(teamName);
          return new ResponseEntity<>(leagueTeam, HttpStatus.OK);
     }


     @GetMapping("/teams")
     public ResponseEntity<List<LeagueTeam>> findAllTeams() {
          List<LeagueTeam> leagueTeamList = teamManagementService.findAllTeamDetailsUnsorted();
          return new ResponseEntity<>(leagueTeamList, HttpStatus.OK);
     }

     @GetMapping("/teams/sorted")
     public ResponseEntity<List<LeagueTeam>> findTeamInfoSorted() {
          List<LeagueTeam> leagueTeamList = teamManagementService.findAllTeamsRankedAndSorted();
          return new ResponseEntity<>(leagueTeamList, HttpStatus.OK);
     }
}
