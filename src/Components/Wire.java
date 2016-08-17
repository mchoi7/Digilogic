package src.Components;

import src.Circuit;
import src.Constants;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Wire {
    public static int UNIT = 40;
    static final boolean LOW = false, HIGH = true;
    List<Wire> outputs = new ArrayList<>();
    Pin p;
    boolean logic, active;

    public Wire(Circuit circuit, Pin p) {
        circuit.getWires().put(p, this);
        this.p = p;
    }

    public void connect(Wire c) {
        outputs.add(c);
    }

    public void pair(Wire c) {

    }

    public void disconnect(Wire c) {
        outputs.remove(c);
    }

    public void enable() {
        active = HIGH;
    }

    public void update() {
        if(logic == HIGH)
            outputs.forEach(Wire::enable);
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    public void renderIn(Graphics2D g) {
        g.setPaint(logic == HIGH ? Constants.palette[2] : Constants.palette[3]);
        g.fillRect(UNIT*p.getX(), UNIT*p.getY(), UNIT, UNIT);
    }
    public void renderOut(Graphics2D g) {
        g.setPaint(Constants.palette[4]);
        g.drawRect(UNIT*p.getX(), UNIT*p.getY(), UNIT, UNIT);
    }
    public void renderWires(Graphics2D g) {
        g.setPaint(logic == HIGH ? Constants.palette[6] : Constants.palette[5]);
        outputs.forEach(output -> lineTo(g, output));
    }
    void lineTo(Graphics2D g, Wire c) {
        g.drawLine(UNIT*p.getX() + UNIT/2, UNIT*p.getY() + UNIT/2, UNIT*c.getPoint().getX() + UNIT/2, UNIT*c.getPoint().getY() + UNIT/2);
    }

    boolean isLogic() {
        return logic;
    }

    public Pin getPoint() {
        return p;
    }
}
