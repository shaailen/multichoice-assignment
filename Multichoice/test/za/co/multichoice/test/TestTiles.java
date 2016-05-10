package za.co.multichoice.test;

import org.junit.Test;

import za.co.multichoice.environment.FlatLand;
import za.co.multichoice.environment.Forest;
import za.co.multichoice.environment.Mountain;
import za.co.multichoice.environment.Tile;
import za.co.multichoice.environment.TileFactory;
import za.co.multichoice.environment.Water;

import static org.junit.Assert.assertEquals;

public class TestTiles {

   
   TileFactory factory = TileFactory.getInstance();
   
   @Test
   public void testStartTile() {	
      System.out.println("Testing Start Tile");
   
      Tile testTile = new FlatLand(0, 0);
      testTile.setIcon('@');
      testTile.setStartTile(true);
      
      Tile generateTile = factory.getTile('@', 0, 0);
      
      assertEquals(testTile.getIcon(), generateTile.getIcon());
      assertEquals(testTile.getName(), generateTile.getName());
      assertEquals(testTile.getX(), generateTile.getX());
      assertEquals(testTile.getY(), generateTile.getY());
      assertEquals(testTile.isStartTile(), generateTile.isStartTile());
      
      System.out.println("Testing Start Tile - Passed");
   }
   
   @Test
   public void testGoalTile() {	
      System.out.println("Testing Goal Tile");
   
      Tile testTile = new FlatLand(5, 5);
      testTile.setIcon('X');
      testTile.setGoalTile(true);
      
      Tile generateTile = factory.getTile('X', 5, 5);
      
      assertEquals(testTile.getIcon(), generateTile.getIcon());
      assertEquals(testTile.getName(), generateTile.getName());
      assertEquals(testTile.getX(), generateTile.getX());
      assertEquals(testTile.getY(), generateTile.getY());
      assertEquals(testTile.isGoalTile(), generateTile.isGoalTile());
      
      System.out.println("Testing Goal Tile - Passed");
   }
   
   @Test
   public void testFlatLandTile() {	
      System.out.println("Testing FlatLand Tile");
   
      Tile testTile = new FlatLand(0, 1);
      testTile.setIcon('.');
      
      Tile generateTile = factory.getTile('.', 0, 1);
      
      assertEquals(testTile.getIcon(), generateTile.getIcon());
      assertEquals(testTile.getName(), generateTile.getName());
      assertEquals(testTile.getX(), generateTile.getX());
      assertEquals(testTile.getY(), generateTile.getY());
      
      System.out.println("Testing Flatland Tile - Passed");
   }
   
   @Test
   public void testForestTile() {	
      System.out.println("Testing Forest Tile");
   
      Tile testTile = new Forest(3, 1);
      testTile.setIcon('*');
      
      Tile generateTile = factory.getTile('*', 3, 1);
      
      assertEquals(testTile.getIcon(), generateTile.getIcon());
      assertEquals(testTile.getName(), generateTile.getName());
      assertEquals(testTile.getX(), generateTile.getX());
      assertEquals(testTile.getY(), generateTile.getY());      
      
      System.out.println("Testing Forest Tile - Passed");
   }
   
   @Test
   public void testMountainTile() {	
      System.out.println("Testing Mountain Tile");
   
      Tile testTile = new Mountain(3, 3);
      testTile.setIcon('^');
      
      Tile generateTile = factory.getTile('^', 3, 3);
      
      assertEquals(testTile.getIcon(), generateTile.getIcon());
      assertEquals(testTile.getName(), generateTile.getName());
      assertEquals(testTile.getX(), generateTile.getX());
      assertEquals(testTile.getY(), generateTile.getY());      
      
      System.out.println("Testing Mountain Tile - Passed");
   }
   
   @Test
   public void testWaterTile() {	
      System.out.println("Testing Water Tile");
   
      Tile testTile = new Water(3, 3);
      testTile.setIcon('~');
      testTile.setWalkable(false);
      
      Tile generateTile = factory.getTile('~', 3, 3);
      
      assertEquals(testTile.getIcon(), generateTile.getIcon());
      assertEquals(testTile.getName(), generateTile.getName());
      assertEquals(testTile.getX(), generateTile.getX());
      assertEquals(testTile.getY(), generateTile.getY());
      assertEquals(testTile.isWalkable(), generateTile.isWalkable());
      
      System.out.println("Testing Water Tile - Passed");
   }
}