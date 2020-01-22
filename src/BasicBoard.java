public class BasicBoard {
	public boolean gameOver = false;					//Boolean used to check if the game is over
	public int totalTurns = 0;							/*@invariant 0<=totalTurns && totalTurns <=60; @*/
	public int arrState[][] = new int[8][8];			/*@invariant initialized with 0's. Constructor 
														places the 4 starting pieces.  @*/
	public boolean playerTurn = false; 					//Black always starts
	public final Coord dirUp = new Coord(0,-1);			//Relative coordinates
	public final Coord dirDown = new Coord(0,1);
	public final Coord dirLeft = new Coord(-1,0);
	public final Coord dirRight = new Coord(1,0);
	public final Coord dirUpLeft = new Coord(-1,-1);
	public final Coord dirUpRight = new Coord(1,-1);
	public final Coord dirDownLeft = new Coord(-1,1);
	public final Coord dirDownRight = new Coord(1,1);

	//Gather them in an array
	final Coord arrDirections[] = { dirUp, dirDown, 
			dirLeft, dirRight, dirUpLeft, dirUpRight, 
			dirDownLeft, dirDownRight };
		

	public int intPlayer(boolean bolPlayer){
		int result = bolPlayer?2:1;
		return result;
	}

	public int[] score(){
		int result[] = new int[2];						//Need to define an array, which
														//will contain the score.
		int scoreBlack = 0;								
		int scoreWhite = 0;								
		
		for(int y = 0; y < arrState.length; y++){		//Running through arrState	
			for(int x = 0; x < arrState[y].length; x++){
				if (arrState[x][y] == 1){
					scoreBlack++;						//We've found a black piece,
														//and adds +1 to his score!
				}
				if (arrState[x][y] == 2){
					scoreWhite++;						//Same goes for white.
				}	
			}
		}
		result[1] = scoreBlack;							//Score is being stored
		result[0] = scoreWhite;							//in the result-array 
				
		return result;									//Which we returns!
	}

	public int counter(){
		return totalTurns;
	}
	

	public void flip(Coord move, Boolean player){
		int enemy = intPlayer(!player);
		int me = intPlayer(player); 	
		
		int x = move.getX();							/*@invariant x>=0 || x<=7; @*/
		int y = move.getY();							/*@invariant y>=0 || y<=7; @*/
		
		for (int i=0; i<arrDirections.length;i++){
			Coord coordDirection = arrDirections[i];
			int xDir = coordDirection.getX();			/*@invariant xDir>=-1 || xDir<=1; @*/
			int yDir = coordDirection.getY();			/*@invariant yDir>=-1 || yDir<=1; @*/
			boolean potential = false;
			
			//If we are inside the board
			if ((y+yDir) > -1 && (y+yDir) < 8 && (x+xDir) < 8 && (x+xDir) > -1 ){
				//If we have an enemy next to us, in the direction we are going.
				if (arrState[x+xDir][y+yDir] == enemy){
					//Then the direction has potential
					potential = true;
				} 
			}
			if (potential == true){
				int jump = 2;							/*@invariant jump>=2 || jump<=7; @*/
				while ((y+(jump*yDir)) > -1 			//While within the board
						&& (y+(jump*yDir)) < 8 
						&& (x+(jump*xDir)) < 8 
						&& (x+(jump*xDir)) > -1 )
				{
					//Let's see if i can find a friend
					if (arrState[x+(jump*xDir)][y+(jump*yDir)] == 0){
						break;
					}
					if (arrState[x+(jump*xDir)][y+(jump*yDir)] == me){
						//everything between (X,Y), and
						//(x+jump*xDir,y+jump*yDir) is ours
						//K = 1, since 0 is our own button.
						for (int k = 1; k < jump; k++){
							arrState[x+k*xDir][y+k*yDir] = me;
						}
						break;
					}
				jump++;		
				}
			}
		}
	}

	public void isGameOver(){
		int count = 0;
		
		//Finding how many validMoves both players have,
		//We don't have the validMoves()-method yet!
		for(int y = 0; y<8;y++){
			for(int x = 0; x<8;x++){
				if (isValidMove(new Coord(x,y), false) == true){
					count++;
				}
				if (isValidMove(new Coord(x,y), true) == true){
					count++;
				}				
			}
		}
		if (count == 0){
			gameOver = true;
		}
	}

	public void play (Coord move, boolean player) throws IllegalMove{
		//Based on the boolean, we need to find out what integer that represents,
		//so that we may store it in the arrState-array.		
		int intPlayer = intPlayer(player);		
		
		//Checking if it's a valid move.
		//Call "isValidMove()"-method to see if our move-coord is valid		
		boolean bolValidMove = isValidMove(move, player);
		
		//He can only move, if the game isn't over,
		//and it isn't an invalid move.
		if (bolValidMove == false || gameOver == true){
			throw new IllegalMove(move);
		} else {
			arrState[move.getX()][move.getY()] = intPlayer;	//Storing
			
			flip(move, player);								//Flipping the buttons, which we now owe
			
			playerTurn = !playerTurn;						//Changing turn!
			
			totalTurns++;									//totalTurns gets +1,
															//since someone has taken a turn
					
			isGameOver();									//Is the game over?		
		}
	}

	public boolean isValidMove(Coord move, boolean player){
		boolean result = false;
		int enemy = intPlayer(!player);
		int me = intPlayer(player);
		
		int x = move.getX();
		int y = move.getY();
		
		//Check if the field is blank.
		if (arrState[x][y] != 0){
			return false;
		} else {
			//Okay so the field is blank.
			//Let's get the directions
			for (int i=0; i<arrDirections.length;i++){
				
				//The direction is stored, for use...
				Coord coordDirection = arrDirections[i];
				//Getting directions
				int xDir = coordDirection.getX();
				int yDir = coordDirection.getY();
				int jump=2;
				
				//Check in every direction, if there's an enemy
				if ((y+yDir) > -1 && (y+yDir) < 8 && (x+xDir) < 8 && (x+xDir) > -1)
				{
					if (arrState[x+xDir][y+yDir] == enemy)
					{
						//Search while inside the board-frame
						while ((y+(jump*yDir)) > -1 && (y+(jump*yDir)) < 8 && (x+(jump*xDir)) < 8 && (x+(jump*xDir)) > -1)
						{			
							//Looking for a friend
							//Empty space is no good
							if (arrState[x+jump*xDir][y+jump*yDir] == 0){
								break;
							}
							//Found friend, it's a legal move!
							if (arrState[x+jump*xDir][y+jump*yDir] == me){
								return true;
							}
						jump++;
						}
					}
				}
			}
		}
		return result;
	}
	

	public BasicBoard(){
		//Settings up the 4 startup-pieces.
		arrState[3][3] = 2;
		arrState[4][4] = 2;
		arrState[4][3] = 1;
		arrState[3][4] = 1;
		
		//totalTurns was initialized with the value 0.
	}

	public void printState(){	
		int arrScore[] = new int[2];						//Setting up score-array					
		//Finding who's turn it is (as Integer), 
		//with the help from intPlayer()-method
		String strPlayerTurn;
		if (playerTurn == false){
			strPlayerTurn = "Black";
		}else{
			strPlayerTurn = "White";
		}
		
		for (int y = 0; y < arrState.length; y++)			//8 lines total
		{
			for (int x = 0; x < arrState[y].length; x++)	//8 elements in each line 
			{						
				//Print a wall to begin with.
				System.out.print("|");
				//Print out what's in the array at [x][y], if the field is
				//owned by a player.
				if (arrState[x][y] == 1 || arrState[x][y] == 2){
					System.out.print(arrState[x][y]);
				}
				if (arrState[x][y] == 0){
					//If it isn't owned by a player, then it's a blank field.
					System.out.print(" ");
				}
			} 												//Let's loop to the next element!
			System.out.println("|");						//put up an ending-wall!
		}													//Let's loop to the next line!
		
		System.out.print("Current score: ");				//Print current score...
		arrScore = score();
		System.out.print("Black: " + arrScore[1]);			//Black
		System.out.println(", White: " +arrScore[0]);		//...and white
														
		System.out.println("Counter: " + counter());		//Print counter
		
		System.out.println("Turn: " + strPlayerTurn);		//Print who's turn it is
														
	}	
}
