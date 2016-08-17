import java.awt.*;

public class Source extends Component {

    Source(Circuit circuit, Point p) {
        super(circuit, p);
    }

    void update() {
        if(logic == HIGH)
            outputs.forEach(Component::enable);
        logic = active;
    }

    void enable() {
        active = !active;
    }

    void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(logic == HIGH ? MainFrame.palette[6] : MainFrame.palette[5]);
        g.drawOval(UNIT*p.getX() + UNIT/4, UNIT*p.getY() + UNIT/4, UNIT/2, UNIT/2);
    }
    void renderWires(Graphics2D g) {
        super.renderWires(g);
    }

}
