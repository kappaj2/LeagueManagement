package za.co.sfh.leagueranking.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import za.co.sfh.leagueranking.models.LeagueTeam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDataLoadService {

     @Value("${application.file-name}")
     private String fileName;

     private final TeamManagementService teamManagementService;

     /**
      * Load the file using Spring ResourceUtils. Read the file line by line and then add the teams using the TeamManagementService.
      *
      * @throws IOException
      */
     public void loadFileFromSourceFolder() throws IOException {

          File file = ResourceUtils.getFile(fileName);

          try (Stream<String> stream = Files.lines(file.toPath())) {
               stream.forEach(line -> unpackFileLine(line));
          }

     }

     /**
      * Unpack the line using two splits. First split between the teams and then split between team and score.
      *
      * @param line The line to consume and unpack
      */
     private void unpackFileLine(String line) {
          log.debug("Reading file data line : [{}]", line);

          var teamsInfo = line.split("[,]");
          var teamAString = teamsInfo[0];
          var teamBString = teamsInfo[1];

          var aSplit = teamAString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); // matches between non-digit and digit and then digit and non didgit
          var bSplit = teamBString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

          var teamA = LeagueTeam.builder()
                  .teamName(aSplit[0].trim())
                  .teamScore(Integer.parseInt(aSplit[1].trim()))
                  .build();

          var teamB = LeagueTeam.builder()
                  .teamName(bSplit[0].trim())
                  .teamScore(Integer.parseInt(bSplit[1].trim()))
                  .build();

          teamManagementService.addNewTeamResults(teamA, teamB);
     }
}
