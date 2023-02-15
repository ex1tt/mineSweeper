package mineSweeperGame;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private final int PANELWIDTH = 500;
	private final int PANELHEIGHT = 500;
	private int tileAmmount = 10;
	private int unitSize = PANELWIDTH / tileAmmount;
	int bombX;
	int bombY;
	int clickedX;
	int clickedY;
	
	Random random;
	
	GamePanel() {
		
		this.setBackground(Color.DARK_GRAY);
		this.setBounds(80, 80, PANELWIDTH, PANELHEIGHT);
		this.addMouseListener(this);
		
		bombX = genBombCoordsX();
		bombY = genBombCoordsY();
		
		System.out.println(bombX);
		System.out.println(bombY);
		
	}
	
	private int genBombCoordsX() {	// generates x coordinates for bomb
		random = new Random();
		int x = (random.nextInt(0, tileAmmount) * unitSize);
		
		return x;
	}
	private int genBombCoordsY() {	// generates y coordinates for bomb
		
		random = new Random();
		
		int y = random.nextInt(0, tileAmmount) * unitSize;
		
		return y;
	}
	
	private boolean checkForBomb(int x, int y) {	// returns boolean state on whether square is a bomb
		
		clickedX = (int)(Math.floor(x / unitSize) * unitSize);
		clickedY = (int)(Math.floor(y / unitSize) * unitSize);
		
		if(clickedX == bombX && clickedY == bombY) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	private int checkSquare(int x, int y) {	// returns amount of a bombs current square is touching
		
		int sqrXCoords = (int)(Math.floor(x / unitSize));
		int sqrYCoords = (int)(Math.floor(y / unitSize));
		int bombXCoords = bombX / unitSize;
		int bombYCoords = bombY / unitSize;
		int count = 0;
		
		if(!(checkForBomb(sqrXCoords *unitSize, sqrYCoords*unitSize))) {	// check to see if current square is a bomb
			
			if(sqrXCoords-1 == bombXCoords && sqrYCoords-1 == bombYCoords) {
				count +=1;
			}
			if(sqrXCoords-1 == bombXCoords && sqrYCoords == bombYCoords) {
				count +=1;
			}
			if(sqrXCoords-1 == bombXCoords && sqrYCoords+1 == bombYCoords) {
				count +=1;
			}
			if(sqrXCoords == bombXCoords && sqrYCoords-1 == bombYCoords) {
				count +=1;
			}
			if(sqrXCoords == bombXCoords && sqrYCoords+1 == bombYCoords) {
				count +=1;
			}
			if(sqrXCoords+1 == bombXCoords && sqrYCoords-1 == bombYCoords) {
				count +=1;
			}
			if(sqrXCoords+1 == bombXCoords && sqrYCoords == bombYCoords) {
				count +=1;
			}
			if(sqrXCoords+1 == bombXCoords && sqrYCoords+1 == bombYCoords) {
				count +=1;
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
	      
	      g.setColor(Color.red);
	      g.fillOval((bombX), (bombY), unitSize, unitSize);

	      g.setColor(Color.blue);
	      g.setFont(new Font("Ariel", Font.CENTER_BASELINE, unitSize));
	      for(int i = 0; i<tileAmmount;i++) {
	    	  for(int z = 0; z<tileAmmount; z++) {
	    		   if(checkSquare(i*unitSize,z*unitSize) > 0) {
	    			   //g.fillOval(i*unitSize, z*unitSize, unitSize, unitSize);
	    			   g.drawString(Integer.toString(checkSquare(i*unitSize,z*unitSize)), (i)*unitSize, (z+1)*unitSize);
	    		   }
		      	}
	      }
	}
	      

	@Override
	public void mouseClicked(MouseEvent e) {
		
		System.out.println(checkForBomb(e.getX(), e.getY()));
		System.out.println(checkSquare(e.getX(), e.getY()));
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
