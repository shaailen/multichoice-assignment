package za.co.multichoice.environment;

public class Water extends Tile {
	
	public Water(int x, int y) {
		setName("Water Tile");
		setWalkable(false);
		setIcon('~');
		setX(x);
		setY(y);
	}
}
