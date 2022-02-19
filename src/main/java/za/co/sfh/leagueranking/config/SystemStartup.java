package za.co.sfh.leagueranking.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import za.co.sfh.leagueranking.services.FileDataLoadService;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!test")
public class SystemStartup implements CommandLineRunner {

     private final FileDataLoadService fileDataLoadService;

     @Override
     public void run(String... args) throws Exception {
          log.debug("Doing system startup task(s)");

          fileDataLoadService.loadFileFromSourceFolder();
     }
}
