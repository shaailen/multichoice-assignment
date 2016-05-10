package za.co.multichoice.pathfinder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import za.co.multichoice.environment.Tile;
import za.co.multichoice.environment.TileType;
import za.co.multichoice.util.ManhattanDistance;
import za.co.multichoice.util.ScoreComparator;
import za.co.multichoice.resource.ResourceFile;

public class PathFinder {

	//environment object used to initialize all the tiles from the input map/file
	private static Environment environment = null;
	//the actual mapped tile environment
	private Tile[][] mappedEnvironment = null;
	//the best tile to move to
	private Tile bestTile = null;
	
	private static final Log LOGGER;
    static {
        LOGGER = LogFactory.getLog(Environment.class);
    }
    
	public PathFinder() {
		//Initialize the environment
		environment = Environment.getInstance();
		if (environment!=null && environment.getMappedEnvironment()!=null) {
			mappedEnvironment = environment.getMappedEnvironment();
		}
	}

	public static void main(String[] args) {
		PathFinder finder = new PathFinder();
	
		//only create output file if the goal was found
		if (finder.processEnvironment()) {
			finder.createOuputFile();
		}
		
	}

	/*
	 * Method to actually find the best path
	 * return @flag - returns true if the last tile landed on is a goal tile
	 */
	public boolean processEnvironment() {
		LOGGER.info(String.format("%s::processEnvironment - Start searching for the best path", this.getClass().getSimpleName()));
		boolean flag = false;
		if (mappedEnvironment!=null) {
			//bestTile will always start from the start tile
			bestTile = environment.getStartTile();
			bestTile.setIcon(TileType.CHOSEN);

			int row=environment.getStartTile().getX();
			while(!bestTile.isGoalTile() && row < environment.getRows()){
				int col=environment.getStartTile().getY();
				boolean checkColumn = true;
				while(!bestTile.isGoalTile() && col < environment.getColumns() && checkColumn){
					//Get all the possible choices based on the current bestTile selected
					List<Tile> choiceTiles = findChoices(bestTile);
					//Choose the bestTile based on the possible choices
					if (choiceTiles!=null && !choiceTiles.isEmpty()) {
						bestTile = selectNextTile(choiceTiles, bestTile);
						if (bestTile!=null) {
							bestTile.setIcon(TileType.CHOSEN);
							row = bestTile.getX();
							col = bestTile.getY();
						}
					}
					col++;					
				}
				row++;
			}
			
			if(bestTile.isGoalTile()){
			 flag = true;	
			}
		}
		LOGGER.info(String.format("%s::processEnvironment - END", this.getClass().getSimpleName()));
		return flag;
	}
	
	/*
	 * Method to find the possible choices for the next tile movement
	 * @return choices - the list of possible choices for the next tile movement
	 */
	private List<Tile> findChoices(Tile bestTile) {

		List<Tile> choices = new ArrayList<Tile>();
		
		//Only check for choices if the current bestTile is walkable
		if (bestTile.isWalkable()) {
			
			//Get the current bestTile co-ordinates as point of reference
			int currentX = bestTile.getX();
			int currentY = bestTile.getY();
			
			/*
			 * Current implementation does not allow the search to go backward.
			 */
			addToChoice(choices, getSpecifiedTile(currentX, (currentY - 1)));
			addToChoice(choices, getSpecifiedTile(currentX, (currentY + 1)));
//			
			addToChoice(choices, getSpecifiedTile((currentX + 1), (currentY - 1)));
			addToChoice(choices, getSpecifiedTile((currentX + 1), (currentY)));
			addToChoice(choices, getSpecifiedTile((currentX + 1), (currentY + 1)));	
		}
		return choices;
	}
	
	/*
	 * Method used to get a tile with the specified x and y co-ordinates 
	 * It will check that the selected tile is within the boundaries of the environment
	 */
	private Tile getSpecifiedTile(int row, int col) {
		Tile expectedTile = null;
		/*
		 * boundaries:
		 * x >= 0 and x < number of rows in the environment
		 * y >= 0 and y < number of columns in the environment
		 */
		if ((row >= 0) && row < environment.getRows() && (col >= 0 && col < environment.getColumns())) {
			expectedTile = mappedEnvironment[row][col];
		}
		
		return expectedTile;
	}
	
	/*
	 * Method to check that the next available choice tile is not null, is walkable and not a tile that has already been selected
	 */
	private void addToChoice(List<Tile> choiceTiles, Tile specifiedTile) {
		if (specifiedTile!=null 
		&& specifiedTile.isWalkable()
		&& specifiedTile.getIcon()!=TileType.CHOSEN) {
				choiceTiles.add(specifiedTile);
				specifiedTile.setChecked(true);
		}
	}
	
	/*
	 * Method that will work out the score for each possible choice movement and determine which is the next tile that should be chosen.
	 * return chosenTile - the next tile to move to
	 */
	private Tile selectNextTile(List<Tile> list, Tile bestTile) {
		Tile chosenTile = null;
		
		//The initial cost is the cost of the path already done
		//start tiles will always be zero
		int totalCost = bestTile.getCost();
		
		final Tile goalTile = environment.getGoalTile();
		
		if (!list.isEmpty()) {
			for(Tile t: list) {
				int distanceToGoal = ManhattanDistance.getInstance().calculateDistance(t.getX(), t.getY(), goalTile.getX(), goalTile.getY());
				t.setDistanceToGoal(distanceToGoal);
	
				//Calculating the tile's score
				t.setScore(Math.abs(totalCost + t.getCost()) + Math.abs(t.getDistanceToGoal()));
			}
			
			//Sort the list from lowest to highest
			Collections.sort(list, new ScoreComparator());
			//choose the tile with the smallest score
			chosenTile = list.get(0);
		}
		
		return chosenTile;
	}
	
	/*
	 * Method to create the output file with the best path
	 */
	private void createOuputFile() {
		String fileName = ResourceFile.outputfile;
		
		LOGGER.info(String.format("%s::createOuputFile - Start writing out data to file: %s", this.getClass().getSimpleName(), fileName));
		
		FileOutputStream foStream = null;
		OutputStreamWriter writer = null;
		
		try {		
			File file = new File(fileName);
			foStream = new FileOutputStream(file);
			writer = new OutputStreamWriter(foStream);
			
			if (mappedEnvironment!=null && mappedEnvironment.length>0) {			
				for(int row=0; row<environment.getRows(); row++) {
					for(int col=0; col < environment.getColumns(); col++) {
						Tile printTile = mappedEnvironment[row][col];
						writer.write(String.format("%s", printTile));
					}
					writer.write("\n");
				}	
			}
			LOGGER.info(String.format("%s::createOuputFile - File created!", this.getClass().getSimpleName()));
		} catch (FileNotFoundException fnfe) {
			LOGGER.error(String.format("%s::createOuputFile - Error Message: %s", this.getClass().getSimpleName(), fnfe.getMessage()));
		} catch (IOException ioe) {
			LOGGER.error(String.format("%s::createOuputFile - Error while creating file created!"));
			LOGGER.error(String.format("%s::createOuputFile - Error Message: %s", this.getClass().getSimpleName(), ioe.getMessage()));
		} finally {
			if (writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
			if (foStream!=null) {
				try {
					foStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
