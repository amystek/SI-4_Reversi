import javax.swing.*;

public class MainMenu extends JFrame{
	public Boolean actionOn = false;
//	public Player player1 = new Player();
	public ComputerPlayer player1 = new ComputerPlayer();
	public ComputerPlayer player2 = new ComputerPlayer();
	
	public void start(){
		while (player2.currentBoard.gameOver == false && player1.currentBoard.gameOver == false){
			if (actionOn == player1.playerColor){	
				try {
					Coord move = player1.move();
					player2.notify(move);
					actionOn = !actionOn;					
				} catch (Exception e) {
					actionOn = !actionOn;	
					e.printStackTrace();
				}
			}
			if (actionOn == player2.playerColor){
				try {
					Coord move = player2.move();
					player1.notify(move);
					actionOn = !actionOn;					
				} catch (Exception e) {
					actionOn = !actionOn;
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		MainMenu y = new MainMenu();
		
		y.player1.init(true);
		y.player2.init(false);
		y.start();
	}
}