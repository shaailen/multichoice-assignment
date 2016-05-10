package za.co.multichoice.test;

import org.junit.Test;

import za.co.multichoice.environment.Forest;
import za.co.multichoice.environment.Mountain;
import za.co.multichoice.environment.Tile;
import za.co.multichoice.util.ManhattanDistance;
import za.co.multichoice.util.ScoreComparator;

import static org.junit.Assert.assertEquals;

public class TestUtil {

   ManhattanDistance md = ManhattanDistance.getInstance();
   ScoreComparator sc = new ScoreComparator();
   
   @Test
   public void testCalculateDistance() {
      System.out.println("Testing ManhattanDistance calculateDistance()");
      
      int value = md.calculateDistance(2, 3, 4, 5);
      
      assertEquals(4,value);
      
      System.out.println("Testing ManhattanDistance calculateDistance() - Passed");
   }
   
   @Test
   public void testCompare() {
      System.out.println("Testing ScoreComparator compare()");
      Tile tile1 = new Forest(1, 2);
      Tile tile2 = new Mountain(2, 5);
      
      tile1.setScore(9);
      tile2.setScore(3);
      
      int value = sc.compare(tile1, tile2);
      
      assertEquals(value, 6);
      
      value = sc.compare(tile2, tile1);
      
      assertEquals(value, -6);
      
      System.out.println("Testing ScoreComparator compare() - Passed");
   }
}