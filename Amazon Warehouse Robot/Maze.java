import java.util.ArrayList;
import java.util.IllegalFormatException;

public class Maze {
	
	private final int[][] maze;
	private int startPosition[] = {-1 ,-1};
	
	public Maze(int[][] maze) {
		this.maze = maze;
	}
	
	public int[] getStartPosition() {
		//Check if we've already found start position.
		if(this.startPosition[0] != -1 && this.startPosition[1] != -1) {
			return this.startPosition;
		}
		
		//Default return value.
		int[] startPosition = {0, 0};
		
		//Loop over rows.
		for(int i = 0; i < this.maze.length; i++) {
			for(int j = 0; j < this.maze[i].length; j++) {
				if(this.maze[i][j] == 2) {
					this.startPosition = new int[] {j, i};
					return new int[] {j, i};
				}
			}
		}
		return startPosition;
	}
	
	public int getPositionValue(int x, int y) {
		if(x < 0 || y < 0 || x >= this.maze.length || y >= this.maze[0].length) {
			return 1;
		}
		return this.maze[y][x];
	}
	
	public boolean isWall(int x, int y) {
		return (this.getPositionValue(x, y) == 1);
	}
	
	public int getMaxX() {
		return this.maze[0].length - 1;
	}
	
	public int getMaxY() {
		return this.maze.length - 1;
	}
	
	public int scoreRoute(ArrayList<int[]> route) {
		int score = 0;
		boolean[][] visited = new boolean[this.getMaxY() + 1][this.getMaxX() + 1];
		
		//Loop over route and score each move.
		for(Object routeStep : route) {
			int[] step = (int[]) routeStep;
			if(this.maze[step[1]][step[0]] == 3 && visited[step[1]][step[0]] == false) {
				//Increase score for correct move
				score++;
				//Remove reward
				visited[step[1]][step[0]] = true;
			}
		}
		return score;
	}
}
