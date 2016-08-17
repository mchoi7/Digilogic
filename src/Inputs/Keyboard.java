package src.Inputs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        System.out.println(e.getExtendedKeyCode());
    }
}
