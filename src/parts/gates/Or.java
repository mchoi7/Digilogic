package src.parts.gates;

import src.DrawAssistant;
import src.parts.*;

import java.awt.*;
import java.awt.Component;

public class Or extends src.parts.Component {

    public Or(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        boolean condition = false;
        for (src.parts.Component input : getInputs())
            if (input.isLogic())
                condition = true;
        return condition;
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawOr(getPin(), g);
    }
}
