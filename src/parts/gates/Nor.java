package src.parts.gates;

import src.DrawAssistant;
import src.parts.Pin;

import java.awt.*;

public class Nor extends Or {

    public Nor(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        return !super.logicCondition();
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawOr(getPin(), g);
        DrawAssistant.drawNotSide(getPin(), g);
    }
}
