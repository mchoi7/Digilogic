public class Point {
    private final int x, y;
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
    public boolean equals (final Object o) {
        return o instanceof Point && ((Point) o).x == x && ((Point) o).y == y;
    }

    public int hashCode() {
        return (x << 16) + y;
    }
}
