import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReversiBoard extends JFrame implements MouseListener{ 
	public JPanel gfxBoard = new JPanel();
	public Board currentBoard = new Board();	
	public String selectedX;
	public String selectedY;
	public String blackName = "";
	public String whiteName = "";
	public Coord playerMove = null;
	public boolean playerColor = false;

	public ReversiBoard(boolean playerC){
		playerColor = playerC;
	}

	public void invalidMoveMessage(){
		JOptionPane.showMessageDialog(this,
			"Sorry, but that's an invalid move. Try again!",
			"Invalid move!",
			JOptionPane.INFORMATION_MESSAGE);
	}

	public void buildGUI(){ 
		//Creating the board itself
		LayoutManager layout = new GridLayout(8, 8);
		
		gfxBoard.setLayout(layout);

		for(int y=0; y<8; y++) {
			for(int x=0; x<8; x++) {
				Color bgColor = null;
				//Background of square
				if ((x+y) % 2 == 0){
					bgColor = new Color(150,150,255);
				}else{
					bgColor = new Color(200,200,255);
				}
				
				boolean validMove = currentBoard.isValidMove(new Coord(x,y), playerColor);

				Square square = new Square(bgColor, currentBoard.arrState[x][y], validMove, x, y);
				square.addMouseListener(this);
				gfxBoard.add(square);
			}
		}		
		JPanel scorePanel = new JPanel();
		JPanel toActPanel = new JPanel();
		JPanel scorePanelRight = new JPanel();
		JPanel scorePanelLeft = new JPanel();
		
		int[] arrScore = currentBoard.score();
		scorePanelLeft.add(new JLabel("[" + blackName +"] Black score: " + arrScore[1]));
	    scorePanelRight.add(new JLabel("[" + whiteName +"] White score: " + arrScore[0]));

	    String actionOnPlayer = "";
	    if (currentBoard.playerTurn == false){
	    	actionOnPlayer = "Black"; 
	    } else{
	    	actionOnPlayer = "White";
	    }
	    toActPanel.add(new JLabel(actionOnPlayer + " is next to act"));

	    scorePanel.add(scorePanelLeft);
	    scorePanel.add(scorePanelRight);
	    
	    getContentPane().add(gfxBoard, BorderLayout.CENTER);
		getContentPane().add(toActPanel, BorderLayout.SOUTH);
		getContentPane().add(scorePanel, BorderLayout.NORTH);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension preferredSize = new Dimension(dim.height / 2, dim.height / 2);

		setPreferredSize(preferredSize);
	}
	

	public void actionPerformed(ActionEvent e){}

	public void mouseClicked(MouseEvent e) {
		playerMove = null;
		
		int x = ((Square) e.getSource()).x;
		int y = ((Square) e.getSource()).y;
		
		Coord move = new Coord(x,y);
		
		boolean validMove = currentBoard.isValidMove(move, playerColor);
		
		if (validMove == false){
			invalidMoveMessage();
		}else{
			playerMove = move;
		}	
	}

	public void redrawBoard(){
		gfxBoard.removeAll();
		buildGUI();
		pack();
		
		if (currentBoard.gameOver == true){
			gameoverMessage();
		}
	}

	public void gameoverMessage(){
		String msg = "";
		int[] arrScore = currentBoard.score(); 
		
		if (arrScore[1] > arrScore[0]){
			msg = "Winner is black!";
		}
		if ((arrScore[1] < arrScore[0])){
			msg = "Winner is white!";
		}

		if ((arrScore[1] == arrScore[0])){
			msg = "It's a draw!";
		}
	
		JOptionPane.showMessageDialog(this,
			msg,
			"Game Over!",
			JOptionPane.INFORMATION_MESSAGE);
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
