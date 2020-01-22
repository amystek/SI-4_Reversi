import java.util.ArrayList;
import javax.swing.JFrame;

public class Player {
	
	public boolean playerColor;
	public int enemyInt;
	public int meInt;
	public String playerName = "HumanPlayer";
	public ReversiBoard GUI;
	public Board currentBoard = new Board();
	

//	player, false - black, and true - white.
	public void init (boolean color){
		playerColor = color;	
		meInt = color?0:1;
		enemyInt = !color?0:1;
		
		//Updating the board
		//Send game information
		GUI = new ReversiBoard(playerColor);
		sendInfoToGUI();
		GUI.buildGUI();
		GUI.setTitle("Reversi");
		GUI.pack();
		GUI.setResizable(false);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setVisible(true);

	}

	public String name (){
		return playerName;
	}

	public Coord move () throws NoPossibleMove, InterruptedException{
		GUI.playerMove = null;
		ArrayList<Coord> arrValidMoves = null;

		//Calling ValidMoves(), to find out
		//if we have any valid moves.
		arrValidMoves = currentBoard.validMoves(playerColor);
		if (arrValidMoves.size() == 0){
			throw new NoPossibleMove();
		}else{
			while (GUI.playerMove == null){
                                Thread.sleep(1000);
				//waiting for the user to 
				//give us a coordinate!
			}
			try {
				currentBoard.play(GUI.playerMove, playerColor);
			} catch (IllegalMove e) {
				e.printStackTrace();
			}
			sendInfoToGUI();
			GUI.redrawBoard();
			
			return GUI.playerMove;
		}
	}

	public void notify (Coord move){
			try {
				currentBoard.play(move, !playerColor);
			} catch (IllegalMove e) {
				e.printStackTrace();
			}
		
		sendInfoToGUI();
		GUI.redrawBoard();
	}

	public void sendInfoToGUI(){
		GUI.currentBoard = this.currentBoard;
		
		if (playerColor == false){
			GUI.blackName = playerName;
			GUI.whiteName = "Opponent";
		} else {
			GUI.blackName = "Opponent";
			GUI.whiteName = playerName;				
		}
	}

}