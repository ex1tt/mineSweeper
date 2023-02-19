package mineSweeperV3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private Board board;
	
	Game() {
		
		this.setTitle("MineSweeper");
		this.setSize(new Dimension(700, 700));
		this.setVisible(true);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(new Color(40, 38, 38));
		
		board = new Board();
		add(board);
		
	}

}
