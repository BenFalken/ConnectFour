public class RowWinChecker extends WinChecker {
	private int NO_WINNER;
	private int ROWS;
	private int COLUMNS;
	
	public RowWinChecker(int noWinner, int rows, int columns) {
		this.NO_WINNER = noWinner;
		this.ROWS = rows;
		this.COLUMNS = columns;
	}
	
	public int getWinner(Circle[][] grid, int id) {
		boolean unbrokenConsecLeft = true;
		boolean unbrokenConsecRight = true;
		int consecLeft = 0;
		int consecRight = 0;
		for (int row = 0; row < this.ROWS; row++) {
			if (grid[row][3].getId() == id) {
				unbrokenConsecLeft = true;
				unbrokenConsecRight = true;
				consecLeft = 0;
				consecRight = 0;
				int absValAway = 1;
				while (unbrokenConsecLeft || unbrokenConsecRight) {
					if ((3 - absValAway >= 0) && (grid[row][3 - absValAway].getId() == id) && unbrokenConsecLeft) {
						consecLeft++;
					} else {
						unbrokenConsecLeft = false;
					}
					if ((3 + absValAway < this.COLUMNS) && (grid[row][3 + absValAway].getId() == id) && unbrokenConsecRight) {
						consecRight++;
					} else {
						unbrokenConsecRight = false;
					}	
					absValAway++;
				}
				if (consecLeft + consecRight + 1 >= 4) {
					return id;
				}
			}
		}
		return this.NO_WINNER;
	}
}
