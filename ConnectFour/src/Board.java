import java.awt.Color;
import java.awt.Graphics;

public class Board {
	private final int ROWS = 6;
	private final int COLUMNS = 7;
	
	private final int INVALID_MOVE = -10000;
	private final int NO_WINNER = 0;
	
	private Circle[][] grid;
	private Circle[][] exampleGrid;
	
	private WinChecker[] winCheckerList = new WinChecker[4];
	
	/**
	 * Make the boards and arrays
	 * Sets up the game for the first move
	 */
	public Board() {
		this.grid = this.makeEmptyGrid();
		this.exampleGrid = this.makeEmptyGrid();
		this.makeWinCheckerList();
	}
	
	/**
	 * Make an empty grid of circles
	 */
	private Circle[][] makeEmptyGrid() {
		Circle[][] emptyGrid = new Circle[ROWS][COLUMNS];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				emptyGrid[row][col] = new Circle(Color.GRAY, 0);
			}
		}
		return emptyGrid;
	}
	
	/**
	 * Fill your array with each win checker class
	 */
	private void makeWinCheckerList() {
		this.winCheckerList[0] = new RowWinChecker(this.NO_WINNER, this.ROWS, this.COLUMNS);
		this.winCheckerList[1] = new ColumnWinChecker(this.NO_WINNER, this.ROWS, this.COLUMNS);
		this.winCheckerList[2] = new UpDiagWinChecker(this.NO_WINNER, this.ROWS, this.COLUMNS);
		this.winCheckerList[3] = new DownDiagWinChecker(this.NO_WINNER, this.ROWS, this.COLUMNS);
	}
	
	/**
	 * Deep copies the current grid to your hypothetical example grid
	 * Is used by the opponent's decision method for simplicity's sake
	 */
	public void makeGridToTestScenarios() {
		Circle reference;
		for (int row = 0; row < this.ROWS; row++) {
			for (int col = 0; col < this.COLUMNS; col++) {
				reference = this.grid[row][col];
				this.exampleGrid[row][col] = new Circle(reference.getColor(), reference.getId());
			}
		}
	}
	
	/**
	 * Shows the current grid
	 * Is used by the game's main method
	 */
	public void show(Graphics g, Player player, Opponent opponent) {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				this.grid[row][col].draw(g, row, col);
			}
        }
	}
	
	/**
	 * Adds a real or predicted move 
	 * Returns true if it is successful, false if not
	 * Is used by the player and opponent
	 */
	public boolean addMove(boolean isAnActualAddition, int col, int id, Color color) {
		Circle[][] unknownGrid;
		if (isAnActualAddition) {
			unknownGrid = this.grid;
		} else {
			unknownGrid = this.exampleGrid;
		}
		try {
			for (int row = 0; row < this.ROWS; row++) {
				if (unknownGrid[row][col].getId() != 0 && row != 0 && unknownGrid[row-1][col].getId() == 0) {
					unknownGrid[row - 1][col].setId(id);
					unknownGrid[row - 1][col].setColor(color);
					return true;
				} else if (row + 1 == this.ROWS && unknownGrid[row-1][col].getId() == 0) {
					unknownGrid[row][col].setId(id);
					unknownGrid[row][col].setColor(color);
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Tests a move to see if it has a winner
	 * If no winner, returns the NO_WINNER constant
	 * If winner, returns the id of who won divided 
	 * by the moves it took to win
	 * Is used by opponent's decision method for simplicity's sake
	 */
	public double testMoves(int[] scenarioArray, Player player, Opponent opponent) {
		int nextMove;
		
		int id;
		Color color;
		
		boolean isSuccessfulMove;
		int winningScenario = NO_WINNER;
		
		for (int column = 0; column < scenarioArray.length; column++) {
			if (column%2 == 0) {
				id = opponent.getId();
				color = player.getColor();
			} else {
				id = player.getId();
				color = opponent.getColor();
			}
			nextMove = scenarioArray[column];
			isSuccessfulMove = this.addMove(false, nextMove, id, color);
			if (isSuccessfulMove) {
				winningScenario = this.getWinner(false, player.getId(), opponent.getId());
				if (winningScenario != NO_WINNER) {
					return (double)(100*winningScenario/(column+1));
				}
			} else if (column == 0){
				return INVALID_MOVE;
			} else {
				return NO_WINNER;
			}
		}
		return NO_WINNER;
	}
	
	/**
	 * Returns who won more given the current grid
	 * Is used by the game's main method to check winner in real time
	 */
	public int getWinner(boolean findActualWinner, int playerId, int opponentId) {
		Circle[][] unknownGrid;
		WinChecker newCheck;
		int winnerSum = 0;
		if (findActualWinner) {
			unknownGrid = this.grid;
		} else {
			unknownGrid = this.exampleGrid;
		}
		for (int check = 0; check < this.winCheckerList.length; check++) {
			newCheck = winCheckerList[check];
			winnerSum = winnerSum + (newCheck.getWinner(unknownGrid, playerId)+newCheck.getWinner(unknownGrid, opponentId));
		}
		return winnerSum;
	}
	
	public int getColumns() {
		return this.COLUMNS;
	}
	
	public int getRows() {
		return this.ROWS;
	}
	
	public int getInvalidMoveValue() {
		return this.INVALID_MOVE;
	}
}
