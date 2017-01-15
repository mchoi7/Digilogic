package src.parts;

import src.DrawAssistant;

import java.awt.*;

public class Ground extends Component {
    public Ground(Pin p) {
        super(p);
    }

    @Override
    public void renderIn(Graphics2D g) {
        DrawAssistant.drawGround(getPin(), g);
    }
}
