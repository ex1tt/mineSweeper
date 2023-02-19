package mineSweeperV4;

import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Board board;
	
	Game() { 	// creating the frame that the board will sit on
		
		this.setTitle("MineSweeper");
		this.setSize(new Dimension(700, 700));
		this.setVisible(true);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.black);
		
		board = new Board();	// instantiating a new board object
		add(board);				// adding said object.
		
	}

}
