package za.co.multichoice.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses(
					{ 
						TestTiles.class,TestUtil.class, TestEnvironment.class, TestPathFinder.class
					}
				   )
public class JunitTestSuite {
}