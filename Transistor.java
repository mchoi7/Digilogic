import java.awt.*;

class Transistor extends Component {
    private Component trigger, redirect;

    Transistor(Circuit circuit, Point p) {
        super(circuit, p);
        trigger = this;
        redirect = this;
    }

    void update() {
        if(logic == HIGH) {
            if(trigger.isLogic() == HIGH)
                outputs.forEach(Component::enable);
            else if(!redirect.equals(this))
                redirect.enable();
        }
        logic = LOW;
        if(active == HIGH) {
            logic = HIGH;
            active = LOW;
        }
    }

    void connect(Component c) {
        if(trigger.equals(this))
            trigger = c;
        else {
            if(!outputs.isEmpty() && redirect.equals(this))
                redirect = c;
            else
                super.connect(c);
        }
    }

    void disconnect(Component c) {
        super.disconnect(c);
        outputs.remove(trigger);
        outputs.remove(redirect);
        if(c.equals(trigger))
            trigger = this;
        if(c.equals(redirect))
            redirect = this;
    }

    void renderIn(Graphics2D g) {
        super.renderIn(g);
    }
    void renderOut(Graphics2D g) {
        super.renderOut(g);
        g.setPaint(trigger.isLogic() == HIGH ? MainFrame.palette[8] : MainFrame.palette[7]);
        g.drawRect(UNIT*p.getX() + UNIT/4, UNIT*p.getY() + UNIT/4, UNIT/2, UNIT/2);
    }
    void renderWires(Graphics2D g) {
        g.setPaint(trigger.isLogic() == HIGH ? MainFrame.palette[8] : MainFrame.palette[7]);
        outputs.forEach(output -> lineTo(g, output));
        lineTo(g, trigger);
        g.setPaint(trigger.isLogic() == HIGH ? MainFrame.palette[7] : MainFrame.palette[8]);
        lineTo(g, redirect);
    }
}
