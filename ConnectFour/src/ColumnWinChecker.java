public class ColumnWinChecker extends WinChecker {
	private int NO_WINNER;
	private int ROWS;
	private int COLUMNS;
	
	public ColumnWinChecker(int noWinner, int rows, int columns) {
		this.NO_WINNER = noWinner;
		this.ROWS = rows;
		this.COLUMNS = columns;
	}
	
	public int getWinner(Circle[][] grid, int id) {
		boolean unbrokenConsecUp = true;
		boolean unbrokenConsecDown = true;
		
		int consecUp = 0;
		int consecDown = 0;
		
		for (int col = 0; col < this.COLUMNS; col++) {
			if (grid[2][col].getId() == id) {
				unbrokenConsecUp = true;
				unbrokenConsecDown = true;
				consecUp = 0;
				consecDown = 0;
				int absValAway = 1;
				while (unbrokenConsecUp || unbrokenConsecDown) {
					if ((2 - absValAway >= 0) && (grid[2 - absValAway][col].getId() == id) && unbrokenConsecUp) {
						consecUp++;
					} else {
						unbrokenConsecUp = false;
					}
					if ((2 + absValAway < this.ROWS) && (grid[2 + absValAway][col].getId() == id) && unbrokenConsecDown) {
						consecUp++;
					} else {
						unbrokenConsecDown = false;
					}	
					absValAway++;
				}
				if (consecUp + consecDown + 1 >= 4) {
					return id;
				}
			}
		}
		return NO_WINNER;
	}
}
