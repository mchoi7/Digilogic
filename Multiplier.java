import java.awt.*;

public class Multiplier extends Component {
    Multiplier(Circuit circuit, Point p) {
        super(circuit, p);
    }

    void enable() {
        active = !active;
    }

    void renderOut(Graphics2D g) {
        super.renderOut(g);
        int x = UNIT*p.getX(), y = UNIT*p.getY(), x2 = x + UNIT, y2 = y + UNIT;
        g.drawLine(x, y, x2, y2);
        g.drawLine(x, y2, x2, y);
    }
}
