public class IllegalMove extends Exception {
    public Coord i; 
    public IllegalMove (Coord i) {
	this.i = i;
    }
}
