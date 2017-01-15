package src.parts;

import src.DrawAssistant;

import java.awt.*;

public class Source extends Component {
    private boolean powered;

    public Source(Pin p) {
        super(p);
        getThroughputs().add(this);
    }

    @Override
    protected boolean logicCondition() {
        return powered;
    }

    @Override
    public void interact() {
        powered = !powered;
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawSource(getPin(), g);
    }
}