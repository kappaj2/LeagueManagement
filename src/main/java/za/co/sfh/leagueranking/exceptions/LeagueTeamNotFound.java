package za.co.sfh.leagueranking.exceptions;

public class LeagueTeamNotFound extends RuntimeException{
     public LeagueTeamNotFound() {
          super();
     }

     public LeagueTeamNotFound(String message) {
          super(message);
     }
}
