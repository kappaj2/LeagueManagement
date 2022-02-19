package za.co.sfh.leagueranking.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test the Score Ranking utility class")
@SpringBootTest
class ScoreRankingUtilTest {

     @Autowired
     private ScoreRankingUtil scoreRankingUtil;

     @BeforeEach
     void setUp() {
          scoreRankingUtil.resetMap();
     }

     @DisplayName("Test adding a score to a new instance.")
     @Test
     void addNewScore_withEmptyRanking_shouldBe_1() {

          var newScore = 10;
          scoreRankingUtil.addNewScore(newScore);

          assertEquals(1, scoreRankingUtil.getScoreRanking(10));
     }

     @DisplayName("Test adding two different scores to a new instance with smaller first")
     @Test
     void addTwoNewScores_withEmptyRanking_smallerFirst() {

          var newScore10 = 10;
          scoreRankingUtil.addNewScore(newScore10);

          var newScore20 = 20;
          scoreRankingUtil.addNewScore(newScore20);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(2, scoreRankingUtil.getScoreRanking(10));
     }

     @DisplayName("Test adding two different scores to a new instance with smaller second")
     @Test
     void addTwoNewScores_withEmptyRanking_smallerSecond() {

          var newScore20 = 20;
          scoreRankingUtil.addNewScore(newScore20);

          var newScore10 = 10;
          scoreRankingUtil.addNewScore(newScore10);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(2, scoreRankingUtil.getScoreRanking(10));
     }

     @DisplayName("Test adding three scores to a new instance with two duplicates")
     @Test
     void addThreeScores_withEmptyRanking_andTwoDuplicates() {

          var newScore10 = 10;
          scoreRankingUtil.addNewScore(newScore10);

          var newScore20 = 20;
          scoreRankingUtil.addNewScore(newScore20);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(2, scoreRankingUtil.getScoreRanking(10));

          scoreRankingUtil.addNewScore(newScore20);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(3, scoreRankingUtil.getScoreRanking(10));

          scoreRankingUtil.addNewScore(newScore20);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(4, scoreRankingUtil.getScoreRanking(10));
     }

     @DisplayName("Test adding four scores with two duplicates")
     @Test
     void addFourScores_withEmptyRanking_andTwoDuplicates() {

          var newScore10 = 10;
          scoreRankingUtil.addNewScore(newScore10);

          var newScore20 = 20;
          scoreRankingUtil.addNewScore(newScore20);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(2, scoreRankingUtil.getScoreRanking(10));

          scoreRankingUtil.addNewScore(newScore20);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(3, scoreRankingUtil.getScoreRanking(10));

          scoreRankingUtil.addNewScore(newScore20);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(4, scoreRankingUtil.getScoreRanking(10));

          var newScore5 = 5;
          scoreRankingUtil.addNewScore(newScore5);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(4, scoreRankingUtil.getScoreRanking(10));
          assertEquals(5, scoreRankingUtil.getScoreRanking(5));
     }

     @DisplayName("Test adding three scores with not duplicates")
     @Test
     void addThreeScores_withEmptyRanking_andNoDuplicates() {

          var newScore10 = 10;
          scoreRankingUtil.addNewScore(newScore10);

          var newScore20 = 20;
          scoreRankingUtil.addNewScore(newScore20);

          var newScore5 = 5;
          scoreRankingUtil.addNewScore(newScore5);

          assertEquals(1, scoreRankingUtil.getScoreRanking(20));
          assertEquals(2, scoreRankingUtil.getScoreRanking(10));
          assertEquals(3, scoreRankingUtil.getScoreRanking(5));
     }
}