package Utils;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { return x; }

    public int getY() { return y; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }
    
    public String toString() {
        String s = x + " " + y;
        return s;
    }
    @Override
    public boolean equals(Object coord)
    {
        Coordinate coords;
        coords = (Coordinate) coord;
        return this.x == coords.x && this.y==coords.y;
    }
}
