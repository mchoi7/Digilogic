package src.parts;

import src.Constants;
import src.DrawAssistant;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Transistor extends Component {
    private List<Component> reverses = new ArrayList<>();

    public Transistor(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        boolean condition = false;
        for (Component input : getInputs())
            if (input.isLogic())
                condition = true;
        return condition;
    }

    @Override
    protected List<Component> resolveNext() {
        return isLogic() ? getThroughputs() : reverses;
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawTransistor(getPin(), g);
    }

    @Override
    public void renderConnections(Graphics2D g) {
        super.renderConnections(g);

        for (Component reverse : reverses) {
            g.setPaint(Constants.palette.get(getNext().contains(reverse) ? "brightRed" : "shadeRed"));
            DrawAssistant.drawLine(getPin(), reverse.getPin(), g);
        }
    }

    @Override
    public void join(Component c) {
        if (getThroughputs().size() > reverses.size())
            reverses.add(c);
        else
            super.join(c);
    }

    @Override
    public void release(Component c) {
        super.release(c);
        reverses.remove(c);
    }
}