package za.co.multichoice.environment;

public class Tile {
	
	//x denotes the x axis value
	private int x = 0;
	//y denotes the y axis value
	private int y = 0;
	//name of the tile
	protected String name = "";
	//icon stores the character that represents the environment
	protected char icon;
	//cost to choose the selected tile
	private int cost = 0;

	private int distanceToGoal = -1;
	private int score = -1;
	
	//flag to determine if tile is walkable
	private boolean isWalkable = true;
	//flag to determine if tile is a goal
	private boolean isGoalTile = false;
	//flag to determine if tile is start tile
	private boolean isStartTile = false;
	//flag to check if the tile was checked
	private boolean checked = false; 
	
	
	//Getters and Setters
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getIcon() {
		return icon;
	}

	public void setIcon(char icon) {
		this.icon = icon;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDistanceToGoal() {
		return distanceToGoal;
	}

	public void setDistanceToGoal(int distanceToGoal) {
		this.distanceToGoal = distanceToGoal;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isWalkable() {
		return isWalkable;
	}

	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}

	public boolean isGoalTile() {
		return isGoalTile;
	}

	public void setGoalTile(boolean isGoalTile) {
		this.isGoalTile = isGoalTile;
	}

	public boolean isStartTile() {
		return isStartTile;
	}

	public void setStartTile(boolean isStartTile) {
		this.isStartTile = isStartTile;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public String toString() { 
		return String.valueOf(getIcon());
	}
}
