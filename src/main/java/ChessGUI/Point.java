package ChessGUI;

public class Point {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point))
            return false;

        Point p = (Point)obj;

        return p == this || (p.x == this.x && p.y == this.y);
    }
}
