package src.Components;

import src.Circuit;
import src.Constants;
import src.MainFrame;

import java.awt.*;

public class Ground extends Wire {
    public Ground(Circuit circuit, Pin p) {
        super(circuit, p);
    }

    public void update() {
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    public void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(logic == HIGH ? Constants.palette[6] : Constants.palette[5]);
        g.drawLine(UNIT*p.getX() + UNIT/4, UNIT*p.getY() + UNIT/4, UNIT*p.getX() + 3*UNIT/4, UNIT*p.getY() + UNIT/4);
        g.drawLine(UNIT*p.getX() + UNIT/3, UNIT*p.getY() + 3*UNIT/4, UNIT*p.getX() + 2*UNIT/3, UNIT*p.getY() + 3*UNIT/4);
    }
    public void renderWires(Graphics2D g) {
        g.setPaint(Constants.palette[5]);
        outputs.forEach(output -> lineTo(g, output));
    }
}
