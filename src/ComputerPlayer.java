import java.util.ArrayList ;
												
public class ComputerPlayer { 												
public Board currentBoard = new Board () ;
public Coord bestPossibleMove ; 												
public boolean playerColor ; 												
public String playerName = "Bot" ;

public final int myMaxDepth =  6 ;
public final int hisMaxDepth = 6 ;
public boolean alfa_beta = false;
												
public void init (boolean color ){ 									
    playerColor = color ; 												
} 												
												
public String name (){ 
    return playerName ; 												
} 												
												
public void notify(Coord move){ 											
    try { 												
        currentBoard.play (move, !playerColor); 											
        currentBoard.printState () ;  } catch(IllegalMove e) { 												
        e.printStackTrace () ; 												
    } 												
} 												

public Coord move() throws NoPossibleMove { 												
    bestPossibleMove=null ; 																								
    if(currentBoard.validMoves(playerColor ). size () == 0){ 												
        throw new NoPossibleMove () ; 												
    }else{ 												
        try { 												
            myTurn( currentBoard , playerColor , 0 , -1000000, +1000000) ; 												
            currentBoard.play(bestPossibleMove , playerColor); 												
        } catch(IllegalMove e) {  e.printStackTrace () ; 												
        } 												
    } 												
    currentBoard.printState();
    return bestPossibleMove ; 												
} 												

public int myTurn(Board board, boolean player, int currentDepth, int alpha, int beta){ 												
    int value = 0 ; 												
    if (board.gameOver == true ||	currentDepth == myMaxDepth){
        return boardValue2(board);
    } 																								
//Find	valid	moves	for	the	board 							
    ArrayList<Coord> arrValidMoves = null ;
    arrValidMoves = board.validMoves(player);
												
//For every valid move 										
    for(int q = 0; q < arrValidMoves.size (); q++){
        Coord move = arrValidMoves.get (q); 												
												
        //Clone	the board
        Board boardClone = new Board () ; 												
        for(int y=0; y<board.arrState.length ; y++){ 												
            for(int x=0; x<board.arrState[y ].length ; x++){ 												
                boardClone.arrState[x ][y]= board.arrState[x ][y]; 												
            } 												
        } 												
        //Perform	the move
        try { 												
        boardClone.play (move,	player); 											
        } catch(IllegalMove e) { 												
        e.printStackTrace () ; 												
        }
												
        //Take the	value	of	our	child
        value = hisTurn (boardClone , ! player , currentDepth+1, beta , alpha);

        //Picking	the	best	child	( maximizing)
        if(value >alpha ){
            alpha = value ;
            if(currentDepth == 0){
                bestPossibleMove = move;
            }
        }
    //Pruning
    //    if (alfa_beta)
            if(alpha >= beta ){
                return alpha ;
            }
    }

    //Return the best child
    return alpha ;
} 												

public int hisTurn(Board board, boolean player, int currentDepth, int alpha, int beta){ 												
    int value = 0 ; 												
    if (board.gameOver == true || currentDepth == hisMaxDepth){
        return boardValue(board);
//        return boardValue2(board);
    } 												
												
//Find	valid	moves	for	the	board 							
    ArrayList<Coord>arrValidMoves = null;												
    arrValidMoves = board.validMoves(player); 												
												
//For every	valid	move 										
    for(int q=0; q<arrValidMoves.size (); q++){ 												
        Coord move = arrValidMoves.get (q); 												
												
//Cloning	the	board 										
    Board boardClone = new Board () ; 												
    for(int y=0; y<board.arrState.length ; y++){ 												
        for(int x=0; x<board.arrState[y ].length ; x++){ 												
            boardClone.arrState[x ][y]= board.arrState[x ][y]; 												
        } 												
    } 												
//Perform	the move 											
    try { 												
        boardClone.play (move,	player); 											
    } catch(IllegalMove e) { 												
        e.printStackTrace () ; 												
    } 												
//Take the	value	of	our	child 								
    value = myTurn(boardClone , ! player , currentDepth+1, beta , alpha); 												
												
//Find	the	weakest	child	( minimizing)								
    if(value <alpha ){
        alpha = value ; 											
    } 												
//Pruning
//    if (alfa_beta)
        if(alpha <= beta ){
        return alpha ; 												
    }
} 											
return alpha ; 												
} 												
												

private int boardValue( Board board) { 												
    int result = 0 ; 												
    int[] score = board.score () ; 											
												
//Checking	if	the game	is	over
    if (board.gameOver == true){ 												
// If	black	is	the	winner 								
        if(score [1] >score [0]){ 												
// If	we are	black 										
            if(playerColor == false ){ 
                return 100000 ; 
            }else{ 										
//We are	white 
                return -100000 ; 										
            } 												
        } 												
// If	white	is	the	winner 								
        if(score [1] >score [0]){ 												
// If	we are	black 										
            if(playerColor == true){
                return -100000 ; 	
            }else{ 										
//We are	white 
                return 100000 ; 										
            }
        }
    } 										
												
//Score 												
    if(playerColor == false ){ 	
        result = result + score[1]; 	
    }
    else{ 										
        result = result + score[0]; 												
    } 												

    ArrayList<Coord>validMoves = board.validMoves(playerColor); 												
//    int mobility = validMoves.size () ;
//    result = result +(mobility * 3) ;
// Fields	next	to	corners.
    Coord arrBadTopLeft[] = { new Coord (1 ,0) , new Coord (1 ,1) , 												
    new Coord(0 ,1) }; 												
    Coord arrBadTopRight[] = { new Coord (6 ,0) , new Coord (6 ,1) , 												
    new Coord(7 ,1) }; 												
    Coord arrBadDownLeft[] = { new Coord (0 ,6) , new Coord (1 ,6) , 												
    new Coord(1 ,7) }; 												
    Coord arrBadDownRight[] = { new Coord (6 ,6) , new Coord (6 ,7) , 												
    new Coord(7 ,6) }; 												
    for(int i =0;i<arrBadTopLeft.length ; i++){ 												
        if (board.arrState[0 ][0]== 0 												
        && board.arrState[arrBadTopLeft[i ].getX() ][arrBadTopLeft[i ].getY ()] 												
        == board.intPlayer(playerColor )){ 												
            result = result - 10 ; 												
        }
    } 											
												
    for(int i =0;i<arrBadTopRight.length ; i++){ 												
        if (board.arrState[7 ][0]== 0 												
        && board.arrState[arrBadTopRight[i ].getX() ][arrBadTopRight[i ].getY ()] 												
        == board.intPlayer(playerColor )){ 												
            result = result - 10 ; 												
        }
    } 											
    for(int i =0;i<arrBadDownLeft.length ; i++){ 												
        if (board.arrState[0 ][7]== 0 												
        && board.arrState[arrBadDownLeft[i ].getX() ][arrBadDownLeft[i ].getY ()] 												
        == board.intPlayer(playerColor )){ 												
        result = result - 10 ; 												
        }
    } 											
    for(int i =0;i<arrBadDownRight.length ; i++){ 												
        if (board.arrState[7][7]== 0 												
        && board.arrState[arrBadDownRight[i ].getX() ][arrBadDownRight[i ].getY ()] 												
        == board.intPlayer(playerColor )){ 												
            result = result - 10 ; 												
        }
    } 											
												
//Corners 												
    Coord arrCorners[] ={ new Coord (0 ,0) , new Coord (7 ,7) , 												
    new Coord (7 ,0) , new Coord(0 ,7) }; 												
    for(int i =0; i<arrCorners.length ; i++){ 												
        if (board.arrState[arrCorners[i ].getX() ][arrCorners[i ].getY ()] 												
        == board.intPlayer(playerColor )){ 												
            result = result + 50 ; 												
        }
    } 											
												
//Sides 												
    Coord arrSides[] = { new Coord (0 ,2) , new Coord (0 ,3) , 												
    new Coord (0 ,4) , new Coord (0 ,5) , new Coord (7 ,2) , 												
    new Coord (7 ,3) , new Coord (7 ,4) , new Coord (7 ,5) , 												
    new Coord (2 ,0) , new Coord (3 ,0) , new Coord (4 ,0) , 												
    new Coord (5 ,0) , new Coord (2 ,7) , new Coord (3 ,7) , 												
    new Coord (4 ,7) , new Coord (5 ,7)}; 												
    for(int i =0; i<arrSides.length ; i++){ 												
        if (board.arrState[arrSides[i ].getX() ][arrSides[i ].getY ()] 												
        == board.intPlayer(playerColor )){ 												
            result = result + 5 ; 												
        }
    } 											
												
    return result ; 												
    }

private int boardValue2( Board board) {
        int result = 0 ;
        int[] score = board.score () ;

//Checking	if	the game	is	over
        if (board.gameOver == true){
// If	black	is	the	winner
            if(score [1] >score [0]){
// If	we are	black
                if(playerColor == false ){
                    return 100000 ;
                }else{
//We are	white
                    return -100000 ;
                }
            }
// If	white	is	the	winner
            if(score [1] >score [0]){
// If	we are	black
                if(playerColor == true){
                    return -100000 ;
                }else{
//We are	white
                    return 100000 ;
                }
            }
        }

//Score
        if(playerColor == false ){
            result = result + score[1];
        }
        else{
            result = result + score[0];
        }

        return result ;
    }


} 												
