import java.awt.Color;

public class Player {
	private final int id = -1;
	private final Color color = Color.BLUE;
	private String name;
	
	public Player() {
		System.out.print("What's your name? ");
		this.name = TextIO.getlnString();
	}
	
	public void decide(Board board) {
		int nextColumn;
		boolean isSuccessfulMove = false;
		do {
			System.out.print("Which column do you choose? (1-7) ");
			nextColumn = TextIO.getlnInt();
			boolean isAnActualAddition = true;
			isSuccessfulMove = board.addMove(isAnActualAddition, nextColumn-1, id, color);
			if (!isSuccessfulMove) {
				System.out.print("Invalid move. ");
			}
		} while (nextColumn < 0 && nextColumn >= board.getColumns() || !isSuccessfulMove);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return this.color;
	}
}
