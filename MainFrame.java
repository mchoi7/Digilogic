import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame implements Runnable, KeyListener, MouseListener {
    private static final int FPS = 60;
    private Circuit circuit = new Circuit();
    private Point pressed;
    private boolean running;
    private int componentType;
    private int tx, ty;
    final static Paint[] palette = new Paint[11];
    final static Stroke[] strokes = new Stroke[3];

    private MainFrame() {
        setSize(1200, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }

    private void init() {
        createBufferStrategy(3);
        addKeyListener(this);
        addMouseListener(this);
        palette[0] = new Color(0xEEFFB4);
        palette[1] = new Color(0xA7C981);
        palette[2] = new Color(0x3E6198);
        palette[3] = new Color(0x2C2072);
        palette[4] = new Color(0x1E0722);
        palette[5] = new Color(0x982646);
        palette[6] = new Color(0xFF4A4A);
        palette[7] = new Color(0x0F557C);
        palette[8] = new Color(0x139CE9);
        strokes[0] = new BasicStroke(2);
        strokes[1] = new BasicStroke(4);
        strokes[2] = new BasicStroke(8);
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        running = true;
    }

    public void run() {
        long startTime;
        while(running) {
            startTime = System.currentTimeMillis();
            update();
            render((Graphics2D) getBufferStrategy().getDrawGraphics());
            try {
                long sleepTime = 1000/FPS - (System.currentTimeMillis() - startTime);
                if(sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        circuit.update();
    }

    private void render(Graphics2D g) {
        g.translate(tx, ty);
        g.setPaint(palette[1]);
        g.fillRect(-getWidth(), -getHeight(), 3*getWidth(), 3*getHeight());
        g.setPaint(palette[0]);
        g.setStroke(strokes[2]);
        for(int j = 0; j < 50; j++)
            for(int i = 0; i < 50; i++)
                g.drawOval(i*Component.UNIT + Component.UNIT/4, j*Component.UNIT + Component.UNIT/4, Component.UNIT/2, Component.UNIT/2);
        g.setStroke(strokes[1]);
        circuit.render(g);
        g.dispose();
        getBufferStrategy().show();
    }

    private Component buildComponent(Point p) {
        switch(componentType) {
            case 'C':
            default:
                return new Component(circuit, p);
            case 'Z':
                return new Transistor(circuit, p);
            case 'S':
                return new Source(circuit, p);
            case 'V':
                return new Ground(circuit, p);
        }
    }


    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() >= 'A' && e.getKeyCode() <= 'Z')
            componentType = e.getKeyCode();

        switch(e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
            case KeyEvent.VK_UP:
                ty += Component.UNIT;
                break;
            case KeyEvent.VK_DOWN:
                ty -= Component.UNIT;
                break;
            case KeyEvent.VK_LEFT:
                tx += Component.UNIT;
                break;
            case KeyEvent.VK_RIGHT:
                tx -= Component.UNIT;
                break;
            case '=':
                Component.UNIT += 1;
                break;
            case '-':
                Component.UNIT -= 1;
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        pressed = new Point((e.getX() - tx) / Component.UNIT, (e.getY() - ty) / Component.UNIT);
        if(SwingUtilities.isRightMouseButton(e)) {
            if (circuit.get(pressed) != null)
                circuit.remove(circuit.get(pressed));
        } else if(e.isShiftDown()) {
            if(circuit.get(pressed) == null)
                new Transistor(circuit, pressed);
        } else {
            if(circuit.get(pressed) == null)
                buildComponent(pressed);
        }
    }

    public void mouseReleased(MouseEvent e) {
        Point released = new Point((e.getX() - tx) / Component.UNIT, (e.getY() - ty) / Component.UNIT);
        if(SwingUtilities.isRightMouseButton(e)) {
            if(circuit.get(released) != null)
                circuit.remove(circuit.get(released));
        } else if(e.isShiftDown()) {
            if(circuit.get(released) == null)
                new Component(circuit, released);
            circuit.get(pressed).connect(circuit.get(released));
        } else {
            if(circuit.get(released) == null)
                buildComponent(released);
            if(!pressed.equals(released))
                circuit.get(pressed).connect(circuit.get(released));
            else
                circuit.get(released).enable();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
