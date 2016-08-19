package src.Components;

import java.io.Serializable;

public class Pin implements Serializable{
    private final int x, y;
    public Pin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public boolean equals (final Object o) {
        return o instanceof Pin && ((Pin) o).x == x && ((Pin) o).y == y;
    }

    public int hashCode() {
        return (x << 16) + y;
    }
}
