import java.awt.*;

public class Ground extends Component {
    Ground(Circuit circuit, Point p) {
        super(circuit, p);
    }

    void update() {
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(logic == HIGH ? MainFrame.palette[6] : MainFrame.palette[5]);
        g.drawLine(UNIT*p.getX() + UNIT/4, UNIT*p.getY() + UNIT/4, UNIT*p.getX() + 3*UNIT/4, UNIT*p.getY() + UNIT/4);
        g.drawLine(UNIT*p.getX() + UNIT/3, UNIT*p.getY() + 3*UNIT/4, UNIT*p.getX() + 2*UNIT/3, UNIT*p.getY() + 3*UNIT/4);
    }

    void renderWires(Graphics2D g) {
        g.setPaint(MainFrame.palette[5]);
        outputs.forEach(output -> lineTo(g, output));
    }
}
