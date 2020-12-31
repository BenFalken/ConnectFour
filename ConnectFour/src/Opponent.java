import java.awt.Color;

public class Opponent {
	private final int id = 1;
	private final Color color = Color.RED;
	
	private int INVALID_MOVE;
	private int recursionDepth = 5;
	
	public Opponent(int invalidMove) {
		this.INVALID_MOVE = invalidMove;
	}
	
	public void decide(Board board, Player player) {
		// Main decision method
		boolean isAnActualAddition = true;
		int[][] allScenarios = this.getAllScenarios(board, recursionDepth);
		int bestMove = getBestMove(board, allScenarios, player);
		board.addMove(isAnActualAddition, bestMove, id, this.color);
	}
	
	private int[][] getAllScenarios(Board board, int recursionDepth) {
		// Make an array with all hypothetical moves
		int allScenarios = (int)(Math.pow(board.getColumns(), recursionDepth));
		int[][] allScenariosArray = new int[allScenarios][recursionDepth];
		int magnitude = 1;
		int columns = board.getColumns();
		int value;
		
		int[] columnChoices = generateColumnChoices(columns);
		
		for (int depth = 0; depth < recursionDepth; depth++) {
			for (int scenario = 0; scenario < allScenarios; scenario++) {
				value = (int)((magnitude*columns*scenario/(allScenarios))%columns);
				allScenariosArray[scenario][depth] = columnChoices[value];
			}
			magnitude*=columns;
		}
		return allScenariosArray;
	}
	
	private int[] generateColumnChoices(int columns) {
		// Make an array with all the columns
		int[] returnArray = new int[columns];
		for (int column = 0; column < columns; column++) {
			returnArray[column] = column;
		}
		return returnArray;
	}
	
	private int getBestMove(Board board, int[][] allScenariosArray, Player player) {
		int columns = board.getColumns();
		double[] optimizedChoiceArray = getOptimizedChoiceArray(board, allScenariosArray, player, columns);
		double bestChoiceValue = INVALID_MOVE;
		double currentChoiceValue;
		int bestMove = 0;
		for (int column = 0; column < columns; column ++) {
			currentChoiceValue = optimizedChoiceArray[column];
			if (currentChoiceValue > bestChoiceValue) {
				bestChoiceValue = currentChoiceValue;
				bestMove = column;
			}
		}
		return bestMove;
	}
	
	private double[] getOptimizedChoiceArray(Board board, int[][] allScenariosArray, Player player, int columns) {
		int allScenariosForColumn = (int)(Math.pow(columns, this.recursionDepth-1));
		double[] optimizedChoiceArray = new double[columns];
		int scenario;
		for (int column = 0; column < columns; column++) {
			for (int columnScenario = 0; columnScenario < allScenariosForColumn; columnScenario++) {
				board.makeGridToTestScenarios();
				scenario = (int)(columnScenario+(allScenariosForColumn*column));
				optimizedChoiceArray[column] += board.testMoves(allScenariosArray[scenario], player, this);
			}
			if (optimizedChoiceArray[column] != 0) {
				optimizedChoiceArray[column] /= allScenariosForColumn;
			}
		}
		return optimizedChoiceArray;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Color getColor() {
		return this.color;
	}
}
