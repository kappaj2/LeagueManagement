package za.co.sfh.leagueranking.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class LeagueTeam {

     private int ranking;
     private String teamName;
     private int teamScore;
     private int teamPoints;

}
