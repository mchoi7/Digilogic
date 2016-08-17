import java.awt.*;
import java.util.List;
import java.util.ArrayList;

class Component {
    static int UNIT = 40;
    static final boolean LOW = false, HIGH = true;
    List<Component> outputs = new ArrayList<>();
    Point p;
    boolean logic, active;

    Component(Circuit circuit, Point p) {
        circuit.getComponents().put(p, this);
        this.p = p;
    }

    void connect(Component c) {
        outputs.add(c);
    }

    void disconnect(Component c) {
        outputs.remove(c);
    }

    void enable() {
        active = HIGH;
    }

    void update() {
        if(logic == HIGH)
            outputs.forEach(Component::enable);
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    void renderIn(Graphics2D g) {
        g.setPaint(logic == HIGH ? MainFrame.palette[2] : MainFrame.palette[3]);
        g.fillRect(UNIT*p.getX(), UNIT*p.getY(), UNIT, UNIT);
    }
    void renderOut(Graphics2D g) {
        g.setPaint(MainFrame.palette[4]);
        g.drawRect(UNIT*p.getX(), UNIT*p.getY(), UNIT, UNIT);
    }
    void renderWires(Graphics2D g) {
        g.setPaint(logic == HIGH ? MainFrame.palette[6] : MainFrame.palette[5]);
        outputs.forEach(output -> lineTo(g, output));
    }

    void lineTo(Graphics2D g, Component c) {
        g.drawLine(UNIT*p.getX() + UNIT/2, UNIT*p.getY() + UNIT/2, UNIT*c.getPoint().getX() + UNIT/2, UNIT*c.getPoint().getY() + UNIT/2);
    }

    boolean isLogic() {
        return logic;
    }

    Point getPoint() {
        return p;
    }
}
