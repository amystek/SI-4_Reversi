/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class Square extends JPanel {
	public Color bgColor = null;
	public Color plColor = null;
	public int x;
	public int y; 
	public int squareStatus;
	public boolean validMove = false;
	
	public Square(Color background, int status, boolean vMove, int xCoord, int yCoord) {
		validMove = vMove;
		bgColor = background;
		squareStatus = status;
		x = xCoord;
		y = yCoord;
		
		if (squareStatus == 1){
			//Black
			plColor = new Color(0, 0, 0);
		}
		if (squareStatus == 2){
			//White
			plColor = new Color(255, 255, 255);
		}
	}
	public void paintComponent(Graphics g){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D)g;   	
	    
	    g2.setColor(bgColor);
	    g2.fillRect(1,1,500,500);
	    
	    if (squareStatus > 0){
	    	g2.setColor(plColor); 
	    	g2.fillOval(dim.height/75, dim.height/75, dim.height/35, dim.height/35);
	    }
	    
		if (validMove == true){
			g2.setColor(new Color(255,0,0)); 
	    	g2.fillOval(dim.height/45, dim.height/45, 10, 10);
		}
	    
	  }
}
