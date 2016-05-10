package za.co.multichoice.pathfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import za.co.multichoice.environment.Tile;
import za.co.multichoice.environment.TileFactory;
import za.co.multichoice.environment.TileType;
import za.co.multichoice.resource.ResourceFile;

public class Environment {

	//File to read in
	private final String fileName = ResourceFile.inputFileName;
	//Stores the content of the file in an ArrayList
	private List<String> mapContentList = new ArrayList<String>();
	//Stores each individual character as a Tile in the mappedEnvironment 
	private Tile[][] mappedEnvironment = null;
	//The number of rows and columns in the mapped environment 
	private int rows = -1;
	private int columns = -1;

	//Store the start and end goal. Assumes that there is only one of each
	private Tile startTile = null;
	private Tile goalTile = null;

	//Access to only one instance of the environment 
	private static Environment instance = null;

	private static final Log LOGGER;
    static {
        LOGGER = LogFactory.getLog(Environment.class);
    }
	
	private Environment() {
		initializer();
	}
	
	/*
	 * Singleton implementation
	 */
	public static Environment getInstance() {
		if (instance==null) {
			instance = new Environment();
		}
		return instance;
	}

	private void initializer() {
		if (readInputFile()) {
			initializeMappedEnvironment();
		}
	}

	/*
	 * Method to read in the input file of the map to be explored
	 * @return readSuccess - Indicates that the file had content to read or that there was no error while reading the file
	 */
	private boolean readInputFile() {
		LOGGER.info(String.format("%s::readInputFile - Start reading input file: %s", this.getClass().getSimpleName(), fileName));
		
		boolean readSuccess = false;
		
		//variables used to open the streams and read the file
		InputStream in = null;
		BufferedReader reader = null;
		
		try {
			in = ResourceFile.class.getResourceAsStream(fileName);					
			reader = new BufferedReader (new InputStreamReader(in));
			
			//read the file line by line
			String line = null;
			while((line = reader.readLine() ) != null) {
				mapContentList.add(line);
			}
			if (!mapContentList.isEmpty()) {
				readSuccess = true;
			}
			
			
		} catch (IOException ioe) { //Exception handling if error occurred with the file
			LOGGER.error(String.format("%s::readInputFile - Error reading input file: %s", this.getClass().getSimpleName(), fileName));
			LOGGER.error(String.format("%s::readInputFile - Error Message: %s", this.getClass().getSimpleName(), ioe.getMessage()));
		} finally {
			//close off all open streams
			if (in!=null) {
				try {
					in.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			if (reader!=null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		LOGGER.info(String.format("%s::readInputFile - End reading input file: %s", this.getClass().getSimpleName(), fileName));
		return readSuccess;
	}

	/*
	 * Method to initialize the 2D array with tiles that represents the input file map
	 */
	private void initializeMappedEnvironment() {
		LOGGER.info(String.format("%s::initMappedEnvironment - Initialize the environment", this.getClass().getSimpleName()));

		//only initialize the environment if there was content from the input file
		if (!mapContentList.isEmpty()) {
			
			//Create the 2D array with the row and columns from the input file
			setRows(mapContentList.size());
			setColumns(mapContentList.get(0).length());
			mappedEnvironment = new Tile[getRows()][getColumns()];
			
			LOGGER.debug(String.format("%s::initMappedEnvironment - Setting up 2D array with rows: %d and columns: %d", this.getClass().getSimpleName(), getRows(), getColumns()));
			
			TileFactory factory = TileFactory.getInstance();
			int x = 0;
			for(String str: mapContentList) {
				char[] rowContent = str.toCharArray();
				int y=0;
				for(char chrVal: rowContent) {
					Tile tempTile = factory.getTile(chrVal, x, y);
//
					if (tempTile.isStartTile()) {
						setStartTile(tempTile);
					}
//					
					if (tempTile.isGoalTile()) {
						setGoalTile(tempTile);
					}
//					
					if (tempTile.isWalkable()) {
						tempTile.setCost(getTileCost(tempTile));
					}
					//Storing the represented character as a Tile in the mapped environment
					mappedEnvironment[x][y] = tempTile;
					//Go to the next element in the row
					y++;
				}
				//Go to the next row
				x++;
			}
		}

		LOGGER.info(String.format("%s::initMappedEnvironment - Finished initializing the environment", this.getClass().getSimpleName()));
	}
		
	/*
	 * Method to work out the cost to move to a tile.
	 * @return cost - is the actual cost that was resolved
	 */
	private int getTileCost(Tile tile) {
		
		int cost = 0;
		//only get the cost for walkable tiles
		if (tile.isWalkable()) {
			//switch on the different tiles to determine their type and cost
			switch (tile.getIcon()) {
				case TileType.START:
					cost = 0;
					break;
				case TileType.NORMAL:
				case TileType.GOAL:
					cost = ResourceFile.FLATLANDS;
					break;
				case TileType.FOREST:
					cost = ResourceFile.FOREST;
				break;
				case TileType.MOUNTAIN:
					cost = ResourceFile.MOUNTAIN;
					break;
			default:
				LOGGER.error(String.format("%s::getTileValue - Could not resolve tile: %s", this.getClass().getSimpleName(), tile.getIcon()));
				break;
			}
		}
		return cost;
	}
	
	
	
	public Tile[][] getMappedEnvironment() {
		return mappedEnvironment;
	}

	public void setMappedEnvironment(Tile[][] mappedEnvironment) {
		this.mappedEnvironment = mappedEnvironment;
	}
	
	public Tile getTile(int x, int y) {
		Tile tile = null;
		if (mappedEnvironment!=null && mappedEnvironment.length>-1) {
			tile = mappedEnvironment[x][y];
		}
		return tile;
	}
	
	public int getRows() {
		return rows;
	}

	private void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	private void setColumns(int columns) {
		this.columns = columns;
	}

	public Tile getStartTile() {
		return startTile;
	}

	private void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public Tile getGoalTile() {
		return goalTile;
	}

	private void setGoalTile(Tile goalTile) {
		this.goalTile = goalTile;
	}

}
