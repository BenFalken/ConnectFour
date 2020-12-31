import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	
	private Board board = new Board();
	private Player player = new Player();
	private Opponent opponent = new Opponent(board.getInvalidMoveValue());
	
	private int moves = 0;
	private boolean RUNNING = true;
	private boolean findActualWinner = true;
	private int winnerId;
	
	public Game() {
		SwingUtilities.invokeLater(() -> {
            this.setBackground(Color.LIGHT_GRAY);
            this.setupFrame();
            this.paintComponent(getGraphics());
            this.run();
        });
	}
	
	private void setupFrame() {
		var frame = new JFrame("Connect Four");
        frame.setSize(100*this.board.getColumns(), (100*this.board.getRows())+10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setVisible(true);
	}
	
	private void run() {
		this.welcome();
		while (this.RUNNING) {
        	this.paintComponent(getGraphics());
        	this.player.decide(this.board);
        	this.RUNNING = !this.checkForWinner();
        	if (RUNNING == false) {
        		this.paintComponent(getGraphics());
        		break;
        	}
        	this.opponent.decide(this.board, this.player);
			this.RUNNING = !this.checkForWinner();
			if (this.moves == board.getRows()*board.getColumns()) {
				break;
			}
			this.moves++;
		}
	}
	
	private void welcome() {
		System.out.println();
		System.out.println("***Greetings!***");
		System.out.println("Welcome to Connect Four. The rules are simple:");
		System.out.println("* Pick a column 1-7 for each turn");
		System.out.println("* Get a 4-in-row win:");
		System.out.println("** Horizontally");
		System.out.println("** Vertically");
		System.out.println("** Diagonally");
		System.out.println("* Make sure the computer doesn't win!");
		System.out.println();
	}
	
	private boolean checkForWinner() {
		this.winnerId = this.board.getWinner(this.findActualWinner, this.player.getId(), this.opponent.getId());
		if (this.winnerId == this.player.getId()) {
			this.paintComponent(getGraphics());
			System.out.println("Player '" + this.player.getName() + "' won!");
			return true;
		} else if (this.winnerId == this.opponent.getId()) {
			this.paintComponent(getGraphics());
			System.out.println("The computer won.");
			return true;
		}
		return false;
	}
	
	public void paintComponent(Graphics g) {
		this.board.show(g, this.player, this.opponent);
	}
}
