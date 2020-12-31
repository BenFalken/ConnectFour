public class DownDiagWinChecker extends WinChecker {
	private int NO_WINNER;
	private int ROWS;
	private int COLUMNS;
	
	public DownDiagWinChecker(int noWinner, int rows, int columns) {
		this.NO_WINNER = noWinner;
		this.ROWS = rows;
		this.COLUMNS = columns;
	}
	
	public int getWinner(Circle[][] grid, int id) {
		int start_row, start_col;
		int row, col;
		int consecutiveTiles;
		
		consecutiveTiles = 0;
		
		start_row = 0;
		start_col = 0;
		
		row = 0;
		col = 0;
		
		while (start_row <= this.ROWS-4) {
			consecutiveTiles = 0;
			col = 0;
			row = start_row;
			while (row < this.ROWS) {
				if (grid[row][col].getId() == id) {
					consecutiveTiles++;
				} else {
					consecutiveTiles = 0;
				}
				if (consecutiveTiles == 4) {
					return id;
				}
				row++;
				col++;
			}
			start_row++;
		}
		
		consecutiveTiles = 0;
		
		start_row = 0;
		start_col = 0;
		
		row = 0;
		col = 0;
		
		while (start_col <= this.COLUMNS-4) {
			consecutiveTiles = 0;
			row = 0;
			col = start_col;
			while (row < this.ROWS && col < this.COLUMNS) {
				if (grid[row][col].getId() == id) {
					consecutiveTiles++;
				} else {
					consecutiveTiles = 0;
				}
				if (consecutiveTiles == 4) {
					return id;
				}
				row++;
				col++;
			}
			start_col++;
		}
		
		return this.NO_WINNER;
	}
}
