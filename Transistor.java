import java.awt.*;

class Transistor extends Component {
    private Component trigger;

    Transistor(Circuit circuit, Point p) {
        super(circuit, p);
        trigger = new Component(circuit, new Point(p.getX(), p.getY() - 1));
    }

    void update() {
        if(logic == HIGH && trigger.isLogic() == HIGH)
                outputs.forEach(Component::enable);
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    void disconnect() {
        super.disconnect();
        trigger.disconnect();
    }

    void renderIn(Graphics2D g) {
        super.renderIn(g);
        g.setPaint(trigger.isLogic() == HIGH ? MainFrame.palette[2] : MainFrame.palette[3]);
        g.fillRect(UNIT * p.getX() + UNIT/4, UNIT * p.getY() + UNIT/4, UNIT/2, UNIT/2);
    }

    void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(MainFrame.palette[4]);
        g.drawRect(UNIT * p.getX() + UNIT/4, UNIT * p.getY() + UNIT/4, UNIT/2, UNIT/2);
        g.drawLine(UNIT*p.getX() + UNIT/2, UNIT*p.getY() + UNIT/2,
                UNIT*trigger.getPoint().getX() + UNIT/2, UNIT*trigger.getPoint().getY() + UNIT/2);
    }
}
