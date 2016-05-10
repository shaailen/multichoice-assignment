package za.co.multichoice.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import za.co.multichoice.environment.FlatLand;
import za.co.multichoice.environment.Tile;
import za.co.multichoice.pathfinder.Environment;

public class TestEnvironment {
	
	
	private Environment env; // the class under test
    private Method m;
    private static String METHOD_NAME = "getTileCost";
    private Class[] parameterTypes;
    private Object[] parameters;

    @Before
    public void setUp() throws Exception {
    	//this will initialize the environment since I need it for reflection. Not ideal to have this happening while testing.
        env = Environment.getInstance();
        parameterTypes = new Class[1];
        parameterTypes[0] = za.co.multichoice.environment.Tile.class;
        m = env.getClass().getDeclaredMethod(METHOD_NAME, parameterTypes);
        m.setAccessible(true);
        parameters = new Object[1];
    }

    @Test
    public void testGetTileCost() throws Exception {
		System.out.println("Testing Environment - getTileCost()");
 	   
	    Tile testTile = new FlatLand(0, 0);
	    testTile.setIcon('@');
	    testTile.setStartTile(true);
	    testTile.setCost(0);
	      
	    parameters[0] = testTile;
    	int cost = (int) m.invoke(env, parameters); 
  
        assertEquals(testTile.getCost(), cost);
        
        System.out.println("Testing Environment - getTileCost() - Passed");
   }
}
