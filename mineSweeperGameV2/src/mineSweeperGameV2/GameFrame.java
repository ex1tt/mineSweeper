package mineSweeperGameV2;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GamePanel gamePanel;
	
	GameFrame() {
		
		this.setTitle("MineSweeper");
		this.setSize(new Dimension(700, 700));
		this.setVisible(true);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.black);
		
		gamePanel = new GamePanel();
		
		this.add(gamePanel);
		
	}

}
