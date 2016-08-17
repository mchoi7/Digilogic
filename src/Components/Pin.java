package src.Components;

public class Pin {
    private final int x, y;
    public Pin(int x, int y) {
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
        return o instanceof Pin && ((Pin) o).x == x && ((Pin) o).y == y;
    }

    public int hashCode() {
        return (x << 16) + y;
    }
}
