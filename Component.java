import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Component {
    static int UNIT = 40;
    static final boolean LOW = false, HIGH = true;
    Circuit circuit;
    List<Component> outputs = new ArrayList<>();
    Point p;
    boolean logic, active;

    Component(Circuit circuit, Point p) {
        circuit.getComponents().put(p, this);
        this.circuit = circuit;
        this.p = p;
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
        g.setPaint(logic == HIGH ? MainFrame.palette[6] : MainFrame.palette[5]);
        outputs.stream().filter(output -> output != null).forEach(output -> g.drawLine(
                UNIT*p.getX() + UNIT/2, UNIT*p.getY() + UNIT/2,
                UNIT*output.getPoint().getX() + UNIT/2,
                UNIT*output.getPoint().getY() + UNIT/2));
    }

    void connect(Component c) {
        outputs.add(c);
    }

    void disconnect() {
        circuit.getComponents().remove(p);
        circuit.getComponents().values().forEach(c -> c.outputs.remove(this));
    }

    void enable() {
        active = HIGH;
    }

    boolean isLogic() {
        return logic;
    }

    Point getPoint() {
        return p;
    }
}
