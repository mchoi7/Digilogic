package src.Components;

import src.Circuit;
import src.Constants;

import java.awt.*;

public class Transistor extends Wire {
    private Wire base, collector;

    public Transistor(Circuit circuit, Pin p) {
        super(circuit, p);
        base = this;
    }

    public void update() {
        if(logic == HIGH) {
            if(base.isLogic() == HIGH && !base.equals(this))
                emitters.forEach(Wire::enable);
            else if(collector != null) {
                collector.enable();
            }
        }
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    public void connect(Wire w) {
        if(emitters.isEmpty() || collector != null) {
            super.connect(w);
        } else
            collector = w;
    }
    public void disconnect(Wire w) {
        emitters.remove(w);
        if(w.equals(collector))
            collector = null;
        if(w.equals(base))
            base = this;
    }
    public void pair(Wire w) {
        base = w;
    }

    public void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(base.isLogic() == HIGH ? Constants.palette[8] : Constants.palette[7]);
        g.drawOval(UNIT*p.getX() + UNIT/4, UNIT*p.getY() + UNIT/4, UNIT/2, UNIT/2);
    }
    public void renderConnections(Graphics2D g) {
        g.setPaint(base.isLogic() == HIGH ? Constants.palette[8] : Constants.palette[7]);
        emitters.forEach(output -> lineTo(g, output));
        g.setPaint(base.isLogic() == HIGH ? Constants.palette[9] : Constants.palette[10]);
        lineTo(g, base);
        g.setPaint(base.isLogic() == HIGH ? Constants.palette[7] : Constants.palette[8]);
        if(collector != null)
            lineTo(g, collector);
    }
}
