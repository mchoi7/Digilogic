package src.Components;

import src.Circuit;
import src.Constants;
import src.MainFrame;

import java.awt.*;

public class Source extends Wire {

    public Source(Circuit circuit, Pin p) {
        super(circuit, p);
    }

    public void update() {
        if(logic == HIGH)
            outputs.forEach(Wire::enable);
        logic = active;
    }

    public void enable() {
        active = !active;
    }

    public void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(logic == HIGH ? Constants.palette[6] : Constants.palette[5]);
        g.drawLine(UNIT*p.getX() + UNIT/2, UNIT*p.getY() + UNIT/2, UNIT*p.getX() + UNIT/2, UNIT*p.getY() + UNIT/8);
        g.drawOval(UNIT*p.getX() + UNIT/4, UNIT*p.getY() + UNIT/4, UNIT/2, UNIT/2);
    }
    public void renderWires(Graphics2D g) {
        super.renderWires(g);
    }
}
