package src.parts.gates;

import src.DrawAssistant;
import src.parts.Pin;

import java.awt.*;

public class Nand extends And {

    public Nand(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        return !super.logicCondition();
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawAnd(getPin(), g);
        DrawAssistant.drawNotSide(getPin(), g);
    }
}
