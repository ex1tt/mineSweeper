	                             	 	 
//	--+-- MINE SWEEPER (remake) --+--
//	--+-- 	 github.com/ex1tt 	--+--

// 	Published on 19/02/2023 
//  Program Version: V4
//  Language Version: Java 11
//  IDE: Eclipse Version: 2022-06 (4.24.0)
//  Coding: utf-8

package mineSweeperV4;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Board extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private final int PANEL_WIDTH = 500;
	private final int PANEL_HEIGHT = 500;
	private final int CELL_AMMOUNT = 10;
	private final int BOMB_AMMOUNT = 16;
	private final int UNIT_SIZE = PANEL_WIDTH / CELL_AMMOUNT;
	private boolean gameRunning = false;
	private int flaggedAllCells;
	private int flaggedBombCells;
	private int flaggedEmptyCells;
	private String screenMessage;
	private int randomBombCoordX;
	private int randomBombCoordY;
	private boolean[][] bombCells = new boolean[CELL_AMMOUNT][CELL_AMMOUNT];
	private boolean[][] openedCells = new boolean[CELL_AMMOUNT][CELL_AMMOUNT];
	private boolean[][] flaggedCells = new boolean[CELL_AMMOUNT][CELL_AMMOUNT];
	private Image CLOSED_TILE;
	private Image FLAGGED_TILE;
	private Image BOMB_TILE;
	private Image GAME_BUTTON_IMAGE;
	private Image MESSAGE_IMAGE;
	private Image[] openedTileIcons;
	private Image[] bombsLeftIcons;
	private Random random;

	Board() {
		
		this.setBounds(80, 60, PANEL_WIDTH, PANEL_HEIGHT + 50);
		this.addMouseListener(this);
		
		startGame(); // calling the startGame() method in the constructor
	}

	
	private void startGame() {	// sets game running to true and resets all variables
		
		gameRunning = true;	
		flaggedAllCells = BOMB_AMMOUNT;
		flaggedBombCells = 0;
		flaggedEmptyCells = 0;
		screenMessage = "game";
		
		for(int i=0; i<bombCells.length; i++) {
			for(int j=0; j<bombCells[i].length; j++) {
				bombCells[i][j] = false;				// empties all lists
				openedCells[i][j] = false;
				flaggedCells[i][j] = false;
			}
		}
		
		fillBoard(BOMB_AMMOUNT);
		
		repaint();	// calls repaint() method to repaint the board after resetting all variables and lists
	}
	
	private void setIcons() {
		
		CLOSED_TILE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV4\\src\\mineSweeperIcons\\closed_tile.png").getImage();
		BOMB_TILE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV4\\src\\mineSweeperIcons\\bomb_tile.png").getImage();
		FLAGGED_TILE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV4\\src\\mineSweeperIcons\\flagged_tile.png").getImage();
		GAME_BUTTON_IMAGE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV4\\src\\mineSweeperIcons\\new_game_button.png").getImage();
		MESSAGE_IMAGE = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV4\\src\\mineSweeperIcons\\" + screenMessage + "_message.png").getImage();
		openedTileIcons = new Image[9];
		bombsLeftIcons = new Image[30];
		
		// adding all the openedTileIcons to a list to access using the count method later on
		
		for(int i=0; i<openedTileIcons.length; i++) {
			openedTileIcons[i] = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV4\\src\\mineSweeperIcons\\openedTileIcons\\opened_tile_" + Integer.toString(i) +".png").getImage();
		}
		
		// adding all the bombsLeftIcons to a list to access later on
		
		for(int i=0; i<bombsLeftIcons.length; i++) {
			bombsLeftIcons[i] = new ImageIcon("C:\\Users\\alber\\eclipse-workspace\\mineSweeperV4\\src\\mineSweeperIcons\\bombsLeftIcons\\bombsLeft_" + Integer.toString(i) +".png").getImage();
		}
	}
	
	private void fillBoard(int bAmmount) {	// generating bAmmount of X and Y coordinates using the generateBomb() method
											// and setting the corresponding coordinates of bombCells[][] to true
		int i=0;
											
		while(i<bAmmount) {												// this allows us to check if there should be a bomb in this coordinate
			generateBomb();												// coordinate system works from X: 0 to CELL_AMMOUNT Y:0 to CELL_AMMOUNT
			if(!(bombCells[randomBombCoordX][randomBombCoordY])) {
				bombCells[randomBombCoordX][randomBombCoordY] = true;	// eg: bombCells[0][8] == true; meaning that cell X:0,Y:8 contains a bomb
				i++;
				
			}
		}
	}

	private void generateBomb() {	// generating 1 X coordinate between 0 and CELL_AMMOUNT
									// generating 1 Y coordinate between 0 and CELL_AMMOUNT
		random = new Random();
		
		randomBombCoordX =  random.nextInt(0, CELL_AMMOUNT);
		randomBombCoordY = random.nextInt(0, CELL_AMMOUNT);
	}
	
	private boolean checkForBomb(int x, int y) {	// checks if a cell is a bomb
													// by taking inputs of X and Y and checking if the corresponding index
		if(bombCells[x][y]) {						// of bombCells[][] == true
			return true;							// if so returning true
		}											// used later on to check if a user has clicked on a bomb square
		else {
			return false;
		}
	}
	
	private int cellCount(int x, int y) {	// counts how many bombs a clicked cell is touching
		
		int userX = (int)Math.floor(x / UNIT_SIZE);	// takes an X and Y input and changes them to a readable coordinate: 0 to CELL_AMMOUNT
		int userY = (int)Math.floor(y / UNIT_SIZE);	// eg: X = 356 and Y = 245 (PANEL COORDINATE SYSTEM) 
		int cellCount = 0;							// this will be changed into X = 7 and Y = 4 if UNIT_SIZE = 50
		
		if(!(checkForBomb(userX, userY))) {	// uses checkForBomb() method to check if cell contains a bomb
			
			if(userX>0 && userY>0 && checkForBomb(userX-1, userY-1)) {
				cellCount+=1;
			}
			if(userX>0 && checkForBomb(userX-1, userY)) {
				cellCount+=1;
			}
			if(userX>0 && userY<(CELL_AMMOUNT-1) && checkForBomb(userX-1, userY+1)) {
				cellCount+=1;
			}																// using 8 seperate if statements we check if a cell next to current cell contains a bomb
			if(userY>0 && checkForBomb(userX, userY-1)) {					// if it does we increment a count variable by 1 which is used later to paint the cells onto the screen
				cellCount+=1;												// 
			}																// to visualise:			   				      xxx
			if(userY<(CELL_AMMOUNT-1) && checkForBomb(userX, userY+1)) {	// o = current cell   			---->		      xox
				cellCount+=1;												// x = all cells that are being checked           xxx
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
	

	private void openCell(int x, int y) {	// when method is called it opens the cell corosponding to X and Y coordinate inputs
		
		int userX = (int)Math.floor(x / UNIT_SIZE);
		int userY = (int)Math.floor(y / UNIT_SIZE);
		
		if(!(flaggedCells[userX][userY])) {
			if((checkForBomb(userX, userY))) {	// checks if cell is a bomb and runs loseGame() method if true
				loseGame();						// (user loses the game if they hit a bomb)
			}

			else if(cellCount(userX*UNIT_SIZE, userY*UNIT_SIZE) == 0 && !(openedCells[userX][userY])) {	// using the cellCount() method we check if count == 0 which means cell is blank (touching no bombs)																
																										// and that the cell hasnt already been opened
				openedCells[userX][userY] = true;														// we open the cell by adding the current coordinates
																										// to openCells[][]
				if(userX>0 && userY>0) {								// using 8 if statements similar to the checkCell() method	
					openedCells[userX-1][userY-1] = true;				// we check the values of userX and userY to make sure that we do not attempt																
				}														// to add coordinates that are out of index range of openCells[][]
				if(userX>0) {											// eg: openedCells[-1][10] would throw an (out of index range) error
					openedCells[userX-1][userY] = true;					// if the paramiters of the list were [0][10] 
				}														
				if(userX>0 && userY<(CELL_AMMOUNT-1)) {	
					openedCells[userX-1][userY+1] = true;				// we can then open up the 8 cells around it as they contain no bombs
				}														// this stops users from having to remove lots of blank cells once															
				if(userY>0) {											// and makes the user experience more efficient
					openedCells[userX][userY-1] = true;				
				}
				if(userY<(CELL_AMMOUNT-1)) {
					openedCells[userX][userY+1] = true;				
				}
				if(userX<(CELL_AMMOUNT-1) && userY>0) {
					openedCells[userX+1][userY-1] = true;			
				}
				if(userX<(CELL_AMMOUNT-1)) {
					openedCells[userX+1][userY] = true;
				}
				if(userX<(CELL_AMMOUNT-1) && userY<(CELL_AMMOUNT-1)) {
					openedCells[userX+1][userY+1] = true;
				}
			}
			
			else {
				openedCells[userX][userY] = true;	// if the current cell is not a bomb or blank then it must be touching at least 1 bomb 						
			}										// therefore we just add the current userX and userY to openCells[][]
		}
		
		repaint();
	}
	
	private void flagCell(int x, int y) {		// method to flag a cell by taking X and Y coorinates
		
		int userX = (int)Math.floor(x / UNIT_SIZE);		// takes an X and Y input and changes them to a readable coordinate: 0 to CELL_AMMOUNT
		int userY = (int)Math.floor(y / UNIT_SIZE);		// like used many times above
		
		if(!(openedCells[userX][userY])) {				// stops user from flagging the cell if the cell has already been opened
			if(flaggedCells[userX][userY]) {			// if the current cell is already flagged
				flaggedCells[userX][userY] = false;		// unflag the cell by adding the current userX and userY to openCells[][]
				if(checkForBomb(userX, userY)) {
					flaggedBombCells -=1;				// decrements flaggedBombCells by 1 (keeps track of how many bomb cells are flagged)
				}
				else {
					flaggedEmptyCells -=1;				// decrements flaggedEmptyCells by 1 (keeps track of how many empty cells are flagged)
				}
				flaggedAllCells +=1;					// increments flaggedAllCells by 1 (keeps track of how many bombs are left to be flagged)
			}
			else if(!(flaggedCells[userX][userY])){		// if the current cell is not flagged
				flaggedCells[userX][userY] = true;		// flag the cell by setting flaggedCells 2d Array at the X and Y indexes to true
				if(checkForBomb(userX, userY)) {
					flaggedBombCells +=1;				// incremenets flaggedBombCells by 1 (keeps track of how many bomb cells are flagged)
				}										
				else {
					flaggedEmptyCells +=1;				// increments flaggedEmptyCells by 1 (keeps track of how many empty cells are flagged)
				}
				flaggedAllCells -=1;					// decrements flaggedAllCells by 1 (keeps track of how many bombs are left to be flagged)
			}

			if(flaggedBombCells == BOMB_AMMOUNT && flaggedEmptyCells == 0) {	// if statement to check if user has flagged all the bomb cells
				winGame();														// it also checks that the flaggedEmptyCells == 0
			}																	// this is to stop users by abusing the game by flagging every cell
		}																		// and winning the game
																				// if this evaluates to true it runs the winGame() method
		repaint();		
	}

	private void winGame() {	// method that is called when user has won the game (flagged all the bomb cells)
		
		gameRunning = false;	// setting gameRunning = false (this stops users from being able to click on the board/panel
		
		for(int i=0; i<bombCells.length; i++) {
			for(int j=0; j<bombCells[i].length; j++) {	// loops through and opens the rest of the empty cells
				if(!(checkForBomb(i, j))) {				// to clearly show all the bomb tiles
					openedCells[i][j] = true;
				}
			}
		}
		screenMessage = "winning";	// sets the screen message = "winning" which when repaint()
		repaint();					// is called it will update the minesweeper icon at the bottom to a winning message
	}
	
	private void loseGame() {	// method that is called when user has lost the game (clicked on a bomb cell)
		
		gameRunning = false;	// setting gameRunning = false (this stops users from being able to click on the board/panel
		
		for(int i=0; i<bombCells.length; i++) {
			for(int j=0; j<bombCells[i].length; j++) {
				if(checkForBomb(i, j)) {
					openedCells[i][j] = true;	// loops through and opens all bomb cells to clearly show all bomb cells
				}
			}
		}
		screenMessage = "losing";	// sets the screen message = "losing" which when repaint()
		repaint();					// is called it will update the minesweeper icon at the bottom to a losing message
	}

	@Override
	   public void paintComponent(Graphics g) {	// this paints everything to the panel

		super.paintComponent(g);
		
		setIcons();	// sets all icons when paintComponent is called to prevent graphical errors

	      for(int i=0; i<bombCells.length; i++) {					// if a cell is added to openedCells[][] and it is a bomb
	    	  for(int j=0; j<bombCells[i].length; j++) {			// it draws the BOMB_TILE image to the board
	    		  if(openedCells[i][j]) {							// at the corresponding coordinates
	    			  if(checkForBomb(i, j)) {						
	    				  g.drawImage(BOMB_TILE, i*UNIT_SIZE, j*UNIT_SIZE, null); 
	    			  }
	    			  else {																									// if the cell is not a bomb then 
	    				  g.drawImage(openedTileIcons[cellCount(i*UNIT_SIZE, j*UNIT_SIZE)], i*UNIT_SIZE, j*UNIT_SIZE, null);	// it draws openedTileIcons[]
	    				  																										// at the index[cellCount] at the corresponding coordiantes
	    			  }
	    		  }
	    		  else if(flaggedCells[i][j]) {									// else if a cell is added to flaggedCells[][]
	    			  g.drawImage(FLAGGED_TILE, i*UNIT_SIZE, j*UNIT_SIZE, null);// it draws the FLAGGED_TILE image to the board at the corresponding coordinates
	    		  }
	    		  else {
	    			  g.drawImage(CLOSED_TILE, i*UNIT_SIZE, j*UNIT_SIZE, null);	// else loops through and draws the CLOSED_TILE image 
	    		  }
	    	  }
	      }
	      g.drawImage(GAME_BUTTON_IMAGE, 0, 500, null);						// draws the GAME_BUTTON_IMAGE to the screen
	      g.drawImage(bombsLeftIcons[flaggedAllCells], 150, 500, null);		// draws the bombsLeftIcons[] at index flaggedAllCells
	      g.drawImage(MESSAGE_IMAGE, 200, 500, null);						// draws the MESSAGE_IMAGE to the screen
	}

	@Override
	public void mousePressed(MouseEvent e) {	// method that is run when mouse is pressed on the panel
		
		if(e.getX() < PANEL_WIDTH && e.getY() < PANEL_HEIGHT) {	// checks if X and Y coordinate of the mouse are within the board paramiters
			if(gameRunning) {									// this is to stop users trying to open or flag squares when you click on the icons 
				if(e.getButton() == MouseEvent.BUTTON1) {		// below the game board (NEW_GAME_BUTTON, GAME_MESSAGE, bombsLeftIcons[])
					openCell(e.getX(), e.getY());				// if user clicks left mouse button run openCell() method (open the cell)
				}
				if(e.getButton() == MouseEvent.BUTTON3) {		// if user clicks right mouse button run flagCell() method (flag the cell)
					flagCell(e.getX(), e.getY());
				}
			}
		}
		if((int)Math.floor(e.getX() / UNIT_SIZE ) < 3 && (int)Math.floor(e.getY() / UNIT_SIZE ) == CELL_AMMOUNT) {	
			startGame();	// if the X and Y coordinates of the mouse are in range of the NEW_GAME_BUTTON (clicked the new game button)
		}					// run startGame() method (restarts the game)
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}
