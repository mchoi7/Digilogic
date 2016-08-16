public class Point {
    private int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    public boolean equals (final Object o) {
        return o instanceof Point && ((Point) o).x == x && ((Point) o).y == y;
    }

    public int hashCode() {
        return (x << 16) + y;
    }
}
