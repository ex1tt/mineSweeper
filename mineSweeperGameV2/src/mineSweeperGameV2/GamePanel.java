package mineSweeperGameV2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private Random random;
	private final int PANELWIDTH = 500;
	private final int PANELHEIGHT = 500;
	private int tileAmmount = 10;
	private int unitSize = PANELWIDTH / tileAmmount;
	private int numOfBombs = 18;
	private boolean isABomb = false;
	ArrayList<Integer> bombXCoords;
	ArrayList<Integer> bombYCoords;
	ArrayList<Integer> bombXYCoords;
	ArrayList<Integer> openedTileCoordsX;
	ArrayList<Integer> openedTileCoordsY;
	ArrayList<Integer> flaggedTileCoordsX;
	ArrayList<Integer> flaggedTileCoordsY;
	Set<Integer> hashBombCoords;
	
	GamePanel() {
		
		this.setBackground(Color.DARK_GRAY);
		this.setBounds(80, 80, PANELWIDTH, PANELHEIGHT);
		this.addMouseListener(this);
		
		openedTileCoordsX = new ArrayList<Integer>();
		openedTileCoordsY = new ArrayList<Integer>();
		flaggedTileCoordsX = new ArrayList<Integer>();
		flaggedTileCoordsY = new ArrayList<Integer>();
		
		genBombCoords(numOfBombs);
		
	}
	
	private void genBombCoords(int num) {
		
		bombXCoords = genBombXCoords(num);
		bombYCoords = genBombYCoords(num);
		
		bombXYCoords = new ArrayList<Integer>();
		
		for(int i = 0; i < bombXCoords.size(); i++) {
			bombXYCoords.add(bombXCoords.get(i) - (bombYCoords.get(i)*2));
		}
		
		hashBombCoords = new HashSet<Integer>(bombXYCoords);
		
		while(hashBombCoords.size() < bombXYCoords.size()) {
			bombXCoords = genBombXCoords(num);
			bombYCoords = genBombYCoords(num);
			
			bombXYCoords = new ArrayList<Integer>();
			
			for(int i = 0; i < bombXCoords.size(); i++) {
				bombXYCoords.add(bombXCoords.get(i) - (bombYCoords.get(i)*2));
			}
			hashBombCoords = new HashSet<Integer>(bombXYCoords);
		}
		
	}
	
	private ArrayList<Integer> genBombXCoords(int ammount) {
		
		random = new Random();
		
		ArrayList<Integer> coords = new ArrayList<Integer>();
		
		for(int i = 0; i < ammount; i++) {
			coords.add(random.nextInt(0, tileAmmount) * unitSize);
		}
		
		return coords;
		
	}
	
	private ArrayList<Integer> genBombYCoords(int ammount) {
		
		random = new Random();
		
		ArrayList<Integer> coords = new ArrayList<Integer>();
		
		for(int i = 0; i < ammount; i++) {
			coords.add(random.nextInt(0, tileAmmount) * unitSize);
		}
		
		return coords;
		
	}
	
	private boolean checkForBomb(int x, int y) {	// returns boolean state on whether square is a bomb
		
		int clickedX = (int)(Math.floor(x / unitSize) * unitSize);
		int clickedY = (int)(Math.floor(y / unitSize) * unitSize);
		
		for(int i=0; i<bombXCoords.size();i++) {
			if((bombXCoords.get(i)).equals(clickedX) && (bombYCoords.get(i)).equals(clickedY)) {
				isABomb = true;
				break;
			}
			else {
				isABomb = false;
			}
		}
		return isABomb;
	}
	
	
	private int checkSquare(int x, int y) {	// returns amount of a bombs current square is touching
		
		int sqrXCoords = (int)(Math.floor(x / unitSize) * unitSize);
		int sqrYCoords = (int)(Math.floor(y / unitSize) * unitSize);
		int count = 0;
		
		if(!(checkForBomb(sqrXCoords, sqrYCoords))) {	// check to see if current square is a bomb
			
			for(int i=0; i<bombXCoords.size(); i++) {
				
				if((bombXCoords.get(i)).equals(sqrXCoords-unitSize) && (bombYCoords.get(i)).equals(sqrYCoords-unitSize)) {
					count +=1;
				}
				if((bombXCoords.get(i)).equals(sqrXCoords-unitSize) && (bombYCoords.get(i)).equals(sqrYCoords)) {
					count +=1;
				}
				if((bombXCoords.get(i)).equals(sqrXCoords-unitSize) && (bombYCoords.get(i)).equals(sqrYCoords+unitSize)) {
					count +=1;
				}
				if((bombXCoords.get(i)).equals(sqrXCoords) && (bombYCoords.get(i)).equals(sqrYCoords-unitSize)) {
					count +=1;
				}
				if((bombXCoords.get(i)).equals(sqrXCoords) && (bombYCoords.get(i)).equals(sqrYCoords+unitSize)) {
					count +=1;
				}
				if((bombXCoords.get(i)).equals(sqrXCoords+unitSize) && (bombYCoords.get(i)).equals(sqrYCoords+unitSize)) {
					count +=1;
				}
				if((bombXCoords.get(i)).equals(sqrXCoords+unitSize) && (bombYCoords.get(i)).equals(sqrYCoords)) {
					count +=1;
				}
				if((bombXCoords.get(i)).equals(sqrXCoords+unitSize) && (bombYCoords.get(i)).equals(sqrYCoords-unitSize)) {
					count +=1;
				}
			}
		}
			
			return count;
	}
	
	@Override
	   public void paintComponent(Graphics g) {
		
	      super.paintComponent(g);
	      g.setColor(Color.white);
	      
	      for(int i = 0; i <= tileAmmount; i++) {
	    	  g.drawLine(unitSize * i, 0, unitSize * i, PANELHEIGHT);
	    	  g.drawLine(0, unitSize * i, PANELWIDTH, unitSize * i);
	      }
	      
	      // open square
	      for(int i = 0; i < openedTileCoordsX.size(); i++) {
	    	  if(checkForBomb(openedTileCoordsX.get(i), openedTileCoordsY.get(i))) {
	    		  g.setColor(Color.red);
	    		  g.fillOval(openedTileCoordsX.get(i), openedTileCoordsY.get(i), unitSize, unitSize);
	    	  }
	    	  else {
	    		  g.setColor(Color.gray);
	    	      g.setFont(new Font("Ariel", Font.CENTER_BASELINE, unitSize));
	    	      
	    	      if(checkSquare(openedTileCoordsX.get(i), openedTileCoordsY.get(i)) == 0) {
	    	    	  g.setColor(Color.gray);
	    	      }
	    	      else if(checkSquare(openedTileCoordsX.get(i), openedTileCoordsY.get(i)) == 1) {
	    	    	  g.setColor(Color.blue);
	    	      }
	    	      else if(checkSquare(openedTileCoordsX.get(i), openedTileCoordsY.get(i)) == 2) {
	    	    	  g.setColor(Color.yellow);
	    	      }
	    	      else {
	    	    	  g.setColor(Color.orange);
	    	      }
	    		  g.drawString(Integer.toString(checkSquare(openedTileCoordsX.get(i), openedTileCoordsY.get(i))), openedTileCoordsX.get(i), (openedTileCoordsY.get(i) + unitSize));
	    	  }
	      }
	      
	      // flag square
	      for(int i = 0; i < flaggedTileCoordsX.size(); i++) {
	    	  g.setColor(Color.red);
    	      g.setFont(new Font("Ariel", Font.CENTER_BASELINE, unitSize));
    		  g.drawString("F", flaggedTileCoordsX.get(i), flaggedTileCoordsY.get(i) + unitSize);
    		  
	      }
	}
	
	private void openSquare(int x, int y) {
		
		if(checkForBomb(x, y)) {
			openedTileCoordsX.add((int)(Math.floor(x / unitSize) * unitSize));	// check if opened square is bomb
			openedTileCoordsY.add((int)(Math.floor(y / unitSize) * unitSize));
			repaint();
			gameOver();
		}
		else {
			openedTileCoordsX.add((int)(Math.floor(x / unitSize) * unitSize));	// opens square and displays how many bombs next to it
			openedTileCoordsY.add((int)(Math.floor(y / unitSize) * unitSize));
			repaint();
			if(openedTileCoordsX.size() == Math.pow(tileAmmount, 2) - numOfBombs) {	// check if player has won
				System.out.println("You WON!");
			}
		}
		
	}
	
	private void flagSquare(int x, int y) {
		
		int sqrXCoords = (int)(Math.floor(x / unitSize) * unitSize);
		int sqrYCoords = (int)(Math.floor(y / unitSize) * unitSize);
		boolean flagged = false;
		
		for(int i = 0; i < flaggedTileCoordsX.size(); i++) {
			if(flaggedTileCoordsX.get(i).equals(sqrXCoords) && flaggedTileCoordsY.get(i).equals(sqrYCoords)) {	// check to see if square is already flagged
				flagged = true;
			}
			else {
				flagged = false;
			}
		}
		
		if(flagged) {	// if square is flagged then remove flag
			flaggedTileCoordsX.remove(flaggedTileCoordsX.indexOf(sqrXCoords));
			flaggedTileCoordsY.remove(flaggedTileCoordsY.indexOf(sqrYCoords));
		}
		else {
			flaggedTileCoordsX.add(sqrXCoords);	// else flag current square
			flaggedTileCoordsY.add(sqrYCoords);
		}
		repaint();
	}
	
	private void gameOver() {
		
		System.out.println("GAME OVER");	// game over message
		
	}
	      

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if(e.getButton() == MouseEvent.BUTTON1) {
			openSquare(e.getX(), e.getY());
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			flagSquare(e.getX(), e.getY());
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
