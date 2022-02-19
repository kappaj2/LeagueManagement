package za.co.sfh.leagueranking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class LeagueTeam {

     @JsonProperty("ranking")
     private int ranking;

     @JsonProperty("team-name")
     private String teamName;

     @JsonIgnore
     private int teamScore;

     @JsonProperty("team-points")
     private int teamPoints;

}
