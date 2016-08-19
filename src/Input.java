package src;

import src.Components.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Input extends MouseAdapter implements KeyListener {

    private MainFrame mainFrame;
    private Pin pressed;
    private char key;
    private int xMouse, yMouse;
    private boolean direction[] = new boolean[256];

    Input(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    void update() {
        if(direction['I'])
            mainFrame.setyCamera(mainFrame.getyCamera() + Wire.UNIT / 2);
        if(direction['J'])
            mainFrame.setxCamera(mainFrame.getxCamera() + Wire.UNIT / 2);
        if(direction['K'])
            mainFrame.setyCamera(mainFrame.getyCamera() - Wire.UNIT / 2);
        if(direction['L'])
            mainFrame.setxCamera(mainFrame.getxCamera() - Wire.UNIT / 2);
    }

    public void mousePressed(MouseEvent e) {
        pressed = new Pin((e.getX() - mainFrame.getxCamera()) / Wire.UNIT, (e.getY() - mainFrame.getyCamera()) / Wire.UNIT);
        Wire wire = mainFrame.getCircuit().get(pressed);
        if(SwingUtilities.isRightMouseButton(e)) { /* if right-click, try to remove wire */
            if (wire != null)
                mainFrame.getCircuit().remove(wire);
        } else
            buildWire(pressed);
        super.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        Pin released = new Pin((e.getX() - mainFrame.getxCamera()) / Wire.UNIT, (e.getY() - mainFrame.getyCamera()) / Wire.UNIT);
        if(!SwingUtilities.isRightMouseButton(e)) {
            buildWire(released);
            Wire wire = mainFrame.getCircuit().get(released);
            if(!mainFrame.getCircuit().get(pressed).equals(wire)) {
                if(e.isShiftDown())
                    wire.pair(mainFrame.getCircuit().get(pressed));
                else
                    mainFrame.getCircuit().get(pressed).connect(wire);
            } else
                wire.enable();
        }
        super.mouseReleased(e);
    }

    public void mouseDragged(MouseEvent e) {
        xMouse = e.getX();
        yMouse = e.getY();
        if(SwingUtilities.isRightMouseButton(e)) {
            Wire w = mainFrame.getCircuit().get(new Pin((xMouse - mainFrame.getxCamera()) / Wire.UNIT, (yMouse - mainFrame.getyCamera()) / Wire.UNIT));
            if(w != null)
                mainFrame.getCircuit().remove(w);
        }

    }

    public void mouseMoved(MouseEvent e) {
        xMouse = e.getX();
        yMouse = e.getY();
    }

    private void buildWire(Pin p) {
        if(mainFrame.getCircuit().get(p) == null) {
            switch(key) {
                case 'Z':
                    new Transistor(mainFrame.getCircuit(), p);
                    break;
                case 'S':
                    new Source(mainFrame.getCircuit(), p);
                    break;
                case 'X':
                    new Ground(mainFrame.getCircuit(), p);
                    break;
                default:
                    new Wire(mainFrame.getCircuit(), p);
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        key = e.getKeyChar();
        if(key >= 'a' && key <= 'z')
            key += 'A' - 'a';
        if(key == 'I' || key == 'J' || key == 'L' || key == 'K')
            direction[key] = false;
    }

    public void keyPressed(KeyEvent e) {
        key = e.getKeyChar();
        if(key >= 'a' && key <= 'z')
            key += 'A' - 'a';
        if(key == 'I' || key == 'J' || key == 'L' || key == 'K')
            direction[key] = true;
    }

    public void keyTyped(KeyEvent e) {
        key = e.getKeyChar();
        if(e.getKeyChar() == '\u001B') {
            mainFrame.getCircuit().saveCircuit();
            System.exit(1);
        } else if(key >= 'a' && key <= 'z')
            key += 'A' - 'a';

        if(!(key == 'I' || key == 'J' || key == 'L' || key == 'K'))
            switch(key) {
                case '+':
                case '=':
                    Wire.UNIT += 1;
                    break;
                case '-':
                case '_':
                    Wire.UNIT -= 1;
                    break;
                default:
                    if (!e.isAltDown())
                        buildWire(new Pin((xMouse - mainFrame.getxCamera()) / Wire.UNIT, (yMouse - mainFrame.getyCamera()) / Wire.UNIT));
                    break;
            }
    }
}
