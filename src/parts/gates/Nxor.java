package src.parts.gates;

import src.DrawAssistant;
import src.parts.Pin;

import java.awt.*;

public class Nxor extends Xor {

    public Nxor(Pin p) {
        super(p);
    }

    @Override
    protected boolean logicCondition() {
        return !super.logicCondition();
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawXor(getPin(), g);
        DrawAssistant.drawNotSide(getPin(), g);
    }
}
