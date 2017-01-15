package src.parts.gates;

import src.DrawAssistant;
import src.parts.Pin;

import java.awt.*;

public class Not extends src.parts.Component {

    public Not(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        boolean condition = true;
        for (src.parts.Component input : getInputs())
            if (input.isLogic())
                condition = false;
        return condition;
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawNot(getPin(), g);
    }
}