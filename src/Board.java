import java.util.ArrayList;

public class Board extends BasicBoard {

	public ArrayList<Coord> validMoves(boolean color){

		ArrayList<Coord> arrValidCoords = new ArrayList<Coord>();

		//Running through the array, to find valid coords.
		for (int y = 0; y<arrState.length;y++){
			for (int x = 0; x<arrState[y].length;x++){

				Coord testCoord = new Coord(x,y);
				
				//Testing if it's a valid move
				Boolean valid = isValidMove(testCoord, color);
				if (valid == true){
					//If it's a valid coord, we are adding it
					arrValidCoords.add(testCoord);
				}
			}
		}
		//We are done, let's return our result.
		return arrValidCoords;
	} //Method
}
