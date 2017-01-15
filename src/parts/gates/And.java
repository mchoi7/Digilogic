package src.parts.gates;

import src.DrawAssistant;
import src.parts.*;
import java.awt.*;

public class And extends src.parts.Component {

    public And(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        boolean condition = true;
        for (src.parts.Component input : getInputs())
            if (!input.isLogic())
                condition = false;
        return condition;
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawAnd(getPin(), g);
    }
}
