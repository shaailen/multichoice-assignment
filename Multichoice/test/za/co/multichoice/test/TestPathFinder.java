package za.co.multichoice.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import za.co.multichoice.environment.Mountain;
import za.co.multichoice.environment.Tile;
import za.co.multichoice.pathfinder.PathFinder;

public class TestPathFinder {
	
	private PathFinder pf; // the class under test
    private Method m;
    private static String METHOD_NAME = "getSpecifiedTile";
    private Class[] parameterTypes;
    private Object[] parameters;

    @Before
    public void setUp() throws Exception {
    	//this will initialize the environment since I need it for reflection. Not ideal to have this happening while testing.
        pf = new PathFinder();
        parameterTypes = new Class[2];
        parameterTypes[0] = int[].class;
        m = pf.getClass().getDeclaredMethod(METHOD_NAME, int.class, int.class);
        m.setAccessible(true);
        parameters = new Object[2];
    }
    
    @Test
    public void testGetSpecifiedTile() throws Exception {
		System.out.println("Testing PathFinder - getSpecifiedTile()");
 	   
	    Tile testTile = new Mountain(0, 1);
	    testTile.setIcon('^');
	    testTile.setCost(3);
	      
	    parameters[0] = 0;
	    parameters[1] = 1;
	    
    	Tile expectedTile = (Tile) m.invoke(pf, parameters[0], parameters[1]); 

    	assertEquals(testTile.getIcon(), expectedTile.getIcon());
        assertEquals(testTile.getName(), expectedTile.getName());
        assertEquals(testTile.getX(), expectedTile.getX());
        assertEquals(testTile.getY(), expectedTile.getY());
    	assertEquals(testTile.getCost(), expectedTile.getCost());
    	
    	System.out.println("Testing PathFinder - getSpecifiedTile() - Passed");
   }
}
