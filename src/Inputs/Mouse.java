package src.Inputs;

import src.Components.*;
import src.MainFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {

    private MainFrame mainFrame;
    private Pin pressed;

    public Mouse(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
        pressed = new Pin((e.getX() - mainFrame.getxCamera()) / Wire.UNIT, (e.getY() - mainFrame.getyCamera()) / Wire.UNIT);
        Wire wire = mainFrame.getCircuit().get(pressed);
        if(SwingUtilities.isRightMouseButton(e)) { /* if right-click, try to remove wire */
            if (wire != null)
                mainFrame.getCircuit().remove(wire);
        } else if(wire == null) {
            if(e.isShiftDown()) {
                new Transistor(mainFrame.getCircuit(), pressed);
            } else if(e.isControlDown()) {
                new Source(mainFrame.getCircuit(), pressed);
            } else if(e.isAltDown()) {
                new Ground(mainFrame.getCircuit(), pressed);
            } else {
                new Wire(mainFrame.getCircuit(), pressed);
            }
        }
        super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Pin released = new Pin((e.getX() - mainFrame.getxCamera()) / Wire.UNIT, (e.getY() - mainFrame.getyCamera()) / Wire.UNIT);
        Wire wire = mainFrame.getCircuit().get(released);
        if(SwingUtilities.isRightMouseButton(e)) {
            if(wire != null)
                mainFrame.getCircuit().remove(wire);
        } else {
            if(wire == null) {
                if(e.isShiftDown()) {
                    new Transistor(mainFrame.getCircuit(), released);
                } else if(e.isControlDown()) {
                    new Source(mainFrame.getCircuit(), released);
                } else if(e.isAltDown()) {
                    new Ground(mainFrame.getCircuit(), released);
                } else {
                    new Wire(mainFrame.getCircuit(), released);
                }
            }

            wire = mainFrame.getCircuit().get(released);
            if(!mainFrame.getCircuit().get(pressed).equals(wire)) {
                if(e.isAltDown())
                    wire.pair(mainFrame.getCircuit().get(pressed));
                else
                    mainFrame.getCircuit().get(pressed).connect(wire);
            } else
                wire.enable();

        }
        super.mouseReleased(e);
    }
}
