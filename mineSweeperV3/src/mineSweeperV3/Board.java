package mineSweeperV3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private final int PANEL_WIDTH = 500;
	private final int PANEL_HEIGHT = 550;
	private final int CELL_AMMOUNT = 10;
	private final int BOMB_AMMOUNT = 10;
	private final int UNIT_SIZE = PANEL_WIDTH / CELL_AMMOUNT;
	private final Color BG_COLOR = new Color(208, 181, 181);
	private boolean gameRunning = false;
	private int flaggedBombCells;
	private String screenMessage;
	private int ranCoordX;
	private int ranCoordY;
	private boolean[][] board = new boolean[CELL_AMMOUNT][CELL_AMMOUNT];
	private boolean[][] openedCells = new boolean[CELL_AMMOUNT][CELL_AMMOUNT];
	private boolean[][] flaggedCells = new boolean[CELL_AMMOUNT][CELL_AMMOUNT];
	private Image CLOSED_TILE;
	private Image FLAGGED_TILE;
	private Image BOMB_TILE;
	private Image GAME_BUTTON_IMAGE;
	private Image MESSAGE_IMAGE;
	private Image[] openedTileIcons;
	Random random;
	

	Board() {
		
		this.setBackground(BG_COLOR);
		this.setBounds(80, 60, PANEL_WIDTH, PANEL_HEIGHT);
		this.addMouseListener(this);
		startGame(); 
		
	}

	
	private void startGame() {
		
		gameRunning = true;
		flaggedBombCells = 0;
		screenMessage = "game";
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				board[i][j] = false;				// empties all lists
				openedCells[i][j] = false;
				flaggedCells[i][j] = false;
			}
		}
		
		fillBoard(BOMB_AMMOUNT);
		
		repaint();
	}
	
	private void setIcons() {
		
		CLOSED_TILE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV3\\src\\mineSweeperIcons\\closed_tile.png").getImage();
		BOMB_TILE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV3\\src\\mineSweeperIcons\\bomb_tile.png").getImage();
		FLAGGED_TILE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV3\\src\\mineSweeperIcons\\flagged_tile.png").getImage();
		GAME_BUTTON_IMAGE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV3\\src\\mineSweeperIcons\\new_game_button.png").getImage();
		MESSAGE_IMAGE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV3\\src\\mineSweeperIcons\\" + screenMessage + "_message.png").getImage();
		openedTileIcons = new Image[9];
		
		for(int i=0; i<openedTileIcons.length; i++) {
			openedTileIcons[i] = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV3\\src\\mineSweeperIcons\\opened_tile_" + Integer.toString(i) +".png").getImage();
		}
	}
	
	private void fillBoard(int bAmmount) {
		
		int i=0;
		
		while(i<bAmmount) {
			generateBomb();
			if(!(board[ranCoordX][ranCoordY])) {
				board[ranCoordX][ranCoordY] = true;
				i++;
				
			}
		}
	}

	private void generateBomb() {
		
		random = new Random();
		
		ranCoordX =  random.nextInt(0, CELL_AMMOUNT);
		ranCoordY = random.nextInt(0, CELL_AMMOUNT);
	}
	
	private boolean checkForBomb(int x, int y) {
		
		if(board[x][y]) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private int cellCount(int x, int y) {
		
		int userX = (int)Math.floor(x / UNIT_SIZE);
		int userY = (int)Math.floor(y / UNIT_SIZE);
		int cellCount = 0;
		
		if(!(checkForBomb(userX, userY))) {
			
			if(userX>0 && userY>0 && checkForBomb(userX-1, userY-1)) {
				cellCount+=1;
			}
			if(userX>0 && checkForBomb(userX-1, userY)) {
				cellCount+=1;
			}
			if(userX>0 && userY<(CELL_AMMOUNT-1) && checkForBomb(userX-1, userY+1)) {
				cellCount+=1;
			}
			if(userY>0 && checkForBomb(userX, userY-1)) {
				cellCount+=1;
			}
			if(userY<(CELL_AMMOUNT-1) && checkForBomb(userX, userY+1)) {
				cellCount+=1;
			}
			if(userX<(CELL_AMMOUNT-1) && userY>0 && checkForBomb(userX+1, userY-1)) {
				cellCount+=1;
			}
			if(userX<(CELL_AMMOUNT-1) && checkForBomb(userX+1, userY )) {
				cellCount+=1;
			}
			if(userX<(CELL_AMMOUNT-1) && userY<(CELL_AMMOUNT-1) && checkForBomb(userX+1, userY+1)) {
				cellCount+=1;
			}
		}
		return cellCount;
	}
	

	private void openCell(int x, int y) {
		
		int userX = (int)Math.floor(x / UNIT_SIZE);
		int userY = (int)Math.floor(y / UNIT_SIZE);
		
		if((checkForBomb(userX, userY))) {
			loseGame();
		}
		else {
			openedCells[userX][userY] = true;
			repaint();
		}
		
	}
	
	private void winGame() {
		
		gameRunning = false;
		System.out.println("You WIN");
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				if(!(checkForBomb(i, j))) {
					openedCells[i][j] = true;
				}
			}
		}
		screenMessage = "winning";
		repaint();
	}
	
	private void loseGame() {
		
		gameRunning = false;
		System.out.println("You LOSE");
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				if(checkForBomb(i, j)) {
					openedCells[i][j] = true;
				}
			}
		}
		screenMessage = "losing";
		repaint();
	}


	private void flagCell(int x, int y) {
		
		int userX = (int)Math.floor(x / UNIT_SIZE);
		int userY = (int)Math.floor(y / UNIT_SIZE);
		
		if(flaggedCells[userX][userY]) {
			flaggedCells[userX][userY] = false;
			if(checkForBomb(userX, userY)) {
				flaggedBombCells -=1;
			}
		}
		else {
			flaggedCells[userX][userY] = true;
			if(checkForBomb(userX, userY)) {
				flaggedBombCells +=1;
			}
		}
		
		if(flaggedBombCells == BOMB_AMMOUNT) {
			winGame();
		}
		repaint();
	}


	@Override
	   public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		setIcons();
	     g.setFont(new Font("Ariel", Font.CENTER_BASELINE, UNIT_SIZE));
	      for(int i=0; i<board.length; i++) {
	    	  for(int j=0; j<board[i].length; j++) {
	    		  if(openedCells[i][j]) {
	    			  if(checkForBomb(i, j)) {
	    				  g.drawImage(BOMB_TILE, i*UNIT_SIZE, j*UNIT_SIZE, null);
	    			  }
	    			  else {
	    				  g.drawImage(openedTileIcons[cellCount(i*UNIT_SIZE, j*UNIT_SIZE)], i*UNIT_SIZE, j*UNIT_SIZE, null);
	    			  }
	    		  }
	    		  else if(flaggedCells[i][j]) {
	    			  g.drawImage(FLAGGED_TILE, i*UNIT_SIZE, j*UNIT_SIZE, null);
	    		  }
	    		  else {
	    			  g.drawImage(CLOSED_TILE, i*UNIT_SIZE, j*UNIT_SIZE, null);
	    		  }
	    	  }
	      }
	      g.drawImage(GAME_BUTTON_IMAGE, 0, 500, null);
	      g.drawImage(CLOSED_TILE, 150, 500, null);
	      g.drawImage(MESSAGE_IMAGE, 200, 500, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(e.getX() < 500 && e.getY() < 500) {
			if(gameRunning) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					openCell(e.getX(), e.getY());
				}
				if(e.getButton() == MouseEvent.BUTTON3) {
					flagCell(e.getX(), e.getY());
				}
			}
		}
		
		if((int)Math.floor(e.getX() / UNIT_SIZE ) < 3 && (int)Math.floor(e.getY() / UNIT_SIZE ) == 10) {
			startGame();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}
	
}
