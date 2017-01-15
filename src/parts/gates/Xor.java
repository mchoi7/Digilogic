package src.parts.gates;

import src.DrawAssistant;
import src.parts.*;

import java.awt.*;
import java.awt.Component;

public class Xor extends src.parts.Component {

    public Xor(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        boolean condition = false;
        for (src.parts.Component input : getInputs())
            if (!input.isLogic())
                condition = !condition;
        return condition;
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawXor(getPin(), g);
    }
}
