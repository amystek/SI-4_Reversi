public class Coord {
    private int x, y;
    public Coord (int x, int y) {
	this.x = x;
	this.y = y;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX (int x) { this.x = x; }
    public void setY (int y) { this.y = y; }
    public boolean isEqualTo (Coord c) {
	return (c.getX() == this.x && c.getY() == this.y);
    }
}
