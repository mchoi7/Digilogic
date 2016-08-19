package src.Components;

import src.Circuit;
import src.Constants;

import java.awt.*;

public class Transistor extends Wire {
    private Wire base, collector;

    public Transistor(Circuit circuit, Pin p) {
        super(circuit, p);
        base = this;
        collector = this;
    }

    public void update() {
        if(logic == HIGH) {
            if(base.isLogic() == HIGH)
                emitters.forEach(Wire::enable);
            else if(!collector.equals(this))
                collector.enable();
        }
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    public void connect(Wire c) {
        if(!emitters.isEmpty() && collector.equals(this))
            collector = c;
        else super.connect(c);
    }

    public void pair(Wire c) {
        base = c;
    }

    public void disconnect(Wire c) {
        super.disconnect(c);
        if(c.equals(base))
            base = this;
        if(c.equals(collector))
            collector = this;
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
        lineTo(g, collector);
    }
}
