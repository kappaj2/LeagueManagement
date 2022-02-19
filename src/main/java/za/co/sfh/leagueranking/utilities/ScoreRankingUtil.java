package za.co.sfh.leagueranking.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScoreRankingUtil {

     private static final Map<Integer, InternalTracker> rankingMap = new ConcurrentHashMap<>();

     public void resetMap() {
          rankingMap.clear();
     }

     /**
      * Add a new game score into the ranking map.
      *
      * @param score The new score.
      */
     public void addNewScore(int score) {

          var newScoreKey = Integer.valueOf(score);

          // map is empty, simply add entry and return
          if (rankingMap.isEmpty()) {

               var internalTracker = new InternalTracker();
               internalTracker.setScoreRanking(1);
               internalTracker.setScoreCount(1);

               rankingMap.put(newScoreKey, internalTracker);
               return;
          }

          if (rankingMap.containsKey(newScoreKey)) {
               rankingMap.replace(newScoreKey, determineNewScoreRanking(newScoreKey));
          } else {
               rankingMap.put(newScoreKey, determineNewScoreRanking(newScoreKey));
          }
     }

     /**
      * Retrieve the ranking for a game score.
      *
      * @param score The score to lookup
      * @return Return the ranking.
      */
     public int getScoreRanking(int score) {
          var scoreKey = Integer.valueOf(score);
          if (rankingMap.containsKey(scoreKey)) {
               return rankingMap.get(scoreKey).getScoreRanking();
          }
          return 0;
     }

     private InternalTracker determineNewScoreRanking(int score) {

          var scoreKey = Integer.valueOf(score);
          InternalTracker internalTracker = new InternalTracker();

          /*
             If score exists, then increment the counter. If the counter is now 2 or greater, then remove the next ranking(s).
             So two 3rd places will eliminate a fourth place, three 3rd places will eliminate 4th and 5th, etc.
           */

          if (rankingMap.containsKey(scoreKey)) {

               internalTracker = rankingMap.get(scoreKey);

               internalTracker.setScoreCount(internalTracker.getScoreCount() + 1);

               int scoreCount = internalTracker.getScoreCount();
               int ranking = internalTracker.getScoreRanking();
               if (scoreCount >= 2) {
                    moveScoreRankingsDownOne(ranking, score);
               }
               rankingMap.replace(scoreKey, internalTracker);

          } else {
               internalTracker.setScoreCount(1);
               var newRankingSlot = determineNewRankingSlot(score);

               internalTracker.setScoreRanking(newRankingSlot);

               // First move all down as we have a slot inserted. Will also move this entry if added first.
               moveScoreRankingsDownOne(newRankingSlot, score);

               rankingMap.put(scoreKey, internalTracker);
          }

          return internalTracker;
     }

     private int determineNewRankingSlot(int score) {

          int rankingSlot = 0;

          for (var entry : rankingMap.entrySet()) {

               if (rankingSlot == 0) { // first entry check
                    if (score >= entry.getKey()) {
                         rankingSlot = entry.getValue().getScoreRanking();
                    } else {
                         rankingSlot = entry.getValue().getScoreRanking() + 1;
                    }
               } else {
                    if (score < entry.getKey()) { // add at the end and cater for possible duplicates up the ladder
                         if (rankingSlot < entry.getValue().getScoreRanking() + entry.getValue().getScoreCount()) {
                              rankingSlot = entry.getValue().getScoreRanking() + entry.getValue().getScoreCount();
                         }
                    }
               }
          }
          return rankingSlot;
     }

     /**
      * Move all the score less than this ranking down one. Entry added somewhere in the middle.
      */
     private void moveScoreRankingsDownOne(int ranking, int excludeScore) {
          rankingMap.entrySet().forEach(entry -> {
               var key = entry.getKey();
               var keyValue = entry.getValue();

               if (keyValue.getScoreRanking() >= ranking && key != excludeScore) {
                    keyValue.setScoreRanking(keyValue.getScoreRanking() + 1);
               }
               rankingMap.replace(entry.getKey(), keyValue);
          });
     }

     @Data
     @AllArgsConstructor
     @NoArgsConstructor
     class InternalTracker {
          private int scoreCount;
          private int scoreRanking;
     }
}
