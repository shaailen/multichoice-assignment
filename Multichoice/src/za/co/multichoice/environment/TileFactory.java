package za.co.multichoice.environment;

public class TileFactory {

	private static TileFactory instance = null;
	
	public static TileFactory getInstance() {
		if (instance == null) {
			instance = new TileFactory();
		}
		return instance;
	}
	
	public Tile getTile(char tileType, int x, int y) {
		Tile factoryFile = null;
		
		/*
		 * This method could have throw an exception/try and catch block if the tile specified was incorrect, i.e., bad data in the input file
		 * However, for the purpose of this assignment I think it is unnecessary.
		 * An empty default will suffice.
		 */
		
		switch(tileType){
			case TileType.START:
				factoryFile = new FlatLand(x,y);
				factoryFile.setStartTile(true);
				factoryFile.setIcon(TileType.START);
				break;
			case TileType.GOAL:
				factoryFile = new FlatLand(x,y);
				factoryFile.setGoalTile(true);
				factoryFile.setIcon(TileType.GOAL);
				break;
			case TileType.NORMAL:
				factoryFile = new FlatLand(x,y);
				factoryFile.setIcon(TileType.NORMAL);
				break;
			case TileType.FOREST:
				factoryFile = new Forest(x,y);
				break;
			case TileType.MOUNTAIN:
				factoryFile = new Mountain(x,y);
				break;
			case TileType.WATER:
				factoryFile = new Water(x,y);
				break;
			default:
				break;
		}
		return factoryFile;
	}
}
