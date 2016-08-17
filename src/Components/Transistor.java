package src.Components;

import src.Circuit;
import src.Constants;

import java.awt.*;

public class Transistor extends Wire {
    private Wire trigger, redirect;

    public Transistor(Circuit circuit, Pin p) {
        super(circuit, p);
        trigger = this;
        redirect = this;
    }

    public void update() {
        if(logic == HIGH) {
            if(trigger.isLogic() == HIGH)
                outputs.forEach(Wire::enable);
            else if(!redirect.equals(this))
                redirect.enable();
        }
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    public void connect(Wire c) {
        if(!outputs.isEmpty() && redirect.equals(this))
            redirect = c;
        else super.connect(c);
    }

    public void pair(Wire c) {
        trigger = c;
    }

    public void disconnect(Wire c) {
        super.disconnect(c);
        if(c.equals(trigger))
            trigger = this;
        if(c.equals(redirect))
            redirect = this;
    }

    public void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(trigger.isLogic() == HIGH ? Constants.palette[8] : Constants.palette[7]);
        g.drawRect(UNIT*p.getX() + UNIT/4, UNIT*p.getY() + UNIT/4, UNIT/2, UNIT/2);
    }
    public void renderWires(Graphics2D g) {
        g.setPaint(trigger.isLogic() == HIGH ? Constants.palette[8] : Constants.palette[7]);
        outputs.forEach(output -> lineTo(g, output));
        g.setPaint(trigger.isLogic() == HIGH ? Constants.palette[9] : Constants.palette[10]);
        lineTo(g, trigger);
        g.setPaint(trigger.isLogic() == HIGH ? Constants.palette[7] : Constants.palette[8]);
        lineTo(g, redirect);
    }
}
