package src;

import src.parts.*;
import src.parts.Component;
import src.parts.gates.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import static java.lang.Math.*;

public class MainFrame extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    /*----------------*/
    /*=====Fields=====*/

    private final static long FPS = 60, MSPF = 1000 / FPS; /* MSPF -> Milli-Seconds Per Frame */
    private Circuit circuit = new Circuit();
    private Component select;
    private boolean running, lock = false;
    private Point camera, mouse;
    private Pin mousePin = new Pin(0, 0);
    private double scroll = (int) (200 / Constants.UNIT), power = 1.2, scale = Math.pow(power, scroll);
    private int key;

    /*----------------*/
    /*==Constructor===*/

    private MainFrame() {
        setTitle("Digilogic");
        setSize(1500, 1250);
        setLocationRelativeTo(null); /* Center the window */
        setUndecorated(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        createBufferStrategy(3); /* Triple check that rendering is loadable */
        running = true;
        camera = new Point(-getWidth() / 4, -getHeight() / 4);
        mouse = new Point(getWidth() / 2, getHeight() / 2);
        new Thread(this::loop).start();
    }

    /*----------------*/
    /*===Functions====*/

    private void loop() {
        long startTime;
        while (running) {
            startTime = System.currentTimeMillis();
            update();
            render();
            try {
                Thread.sleep(max(0, MSPF - System.currentTimeMillis() + startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        circuit.update();
    }

    private void render() {
        Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
        double sx = (1 - scale) * getWidth() / 2 - scale * camera.getX(), sy = (1 - scale) * getHeight() / 2 - scale * camera.getY();
        g.translate(sx, sy);
        g.scale(scale, scale);
        g.setStroke(Constants.basic);
        g.setColor(Color.lightGray);
        DrawAssistant.drawGrid(g);
        circuit.render(g);
        g.setPaint(Constants.palette.get("shadeRed"));
        DrawAssistant.drawOval(mousePin, .75, g);
        g.dispose();
        getBufferStrategy().show();
    }

    private Component build(Pin p) {
        Component c;
        switch (key) {
            case '1':
                c = new Source(p);
                break;
            case '2':
                c = new Transistor(p);
                break;
            case '3':
                c = new Ground(p);
                break;
            case '4':
                c = new And(p);
                break;
            case '5':
                c = new Or(p);
                break;
            case '6':
                c = new Xor(p);
                break;
            case '7':
                c = new Not(p);
                break;
            case '8':
                c = new Nand(p);
                break;
            case '9':
                c = new Nor(p);
                break;
            case '0':
                c = new Nxor(p);
                break;
            default:
                c = new Wire(p);
                break;
        }
        circuit.add(c);
        return c;
    }

    private void mouseSet(MouseEvent e) {
        mouse = e.getPoint();
        double rx = camera.getX() + (getWidth() / 2 + (e.getX() - getWidth() / 2) / scale) - Constants.UNIT / 2,
                ry = camera.getY() + (getHeight() / 2 + (e.getY() - getHeight() / 2) / scale) - Constants.UNIT / 2;
        mousePin = new Pin((int) round(rx / Constants.UNIT), (int) round(ry / Constants.UNIT));

        double offset = 100;
        double dx = mouse.getX() - getWidth() / 2, dy = mouse.getY() - getHeight() / 2;
        if ((abs(dx) > getWidth() / 2 - offset || abs(dy) > getHeight() / 2 - offset)) {
            if (lock) {
                try {
                    Robot robot = new Robot();
                    robot.mouseMove((int) (getX() + max(min(mouse.getX(), getWidth() - offset / 2), offset / 2)),
                            (int) (getY() + max(min(mouse.getY(), getHeight() - offset / 2), offset / 2)));
                } catch (AWTException err) {
                    err.printStackTrace();
                }
            }
            camera.translate((int) (64 * dx / getWidth() / (1 + scale)), (int) (64 * dy / getHeight() / (1 + scale)));
        }
    }

    /*----------------*/
    /*=Implementions==*/

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == '\u001B')
            System.exit(0);
        if((e.getExtendedKeyCode() >= '0' && e.getExtendedKeyCode() <= '9') || e.getExtendedKeyCode() == ' ')
            key = e.getExtendedKeyCode();

        if (e.isControlDown()) {
            switch (e.getExtendedKeyCode()) {
                case 'S':
                    try {
                        FileOutputStream fileOut = new FileOutputStream("src/resources/circuit.data");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(circuit);
                        out.close();
                        fileOut.close();
                        System.out.println("Circuit File Saved");
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                    break;
                case 'O':
                    try {
                        FileInputStream fileIn = new FileInputStream("src/resources/circuit.data");
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        circuit = (Circuit) in.readObject();
                        in.close();
                        fileIn.close();
                        System.out.println("Circuit File Opened");
                    } catch (IOException i) {
                        i.printStackTrace();
                        return;
                    } catch (ClassNotFoundException c) {
                        System.out.println("Circuit File Not Found");
                        c.printStackTrace();
                        return;
                    }
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        mouseSet(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            circuit.remove(circuit.get(mousePin));
        } else {
            select = circuit.get(mousePin) == null ? build(mousePin) : circuit.get(mousePin);
        }
    }

    public void mouseReleased(MouseEvent e) {
        mouseSet(e);
        Component finish = circuit.get(mousePin);
        if (SwingUtilities.isRightMouseButton(e)) {
            circuit.remove(circuit.get(mousePin));
        } else {
            if (circuit.get(mousePin) == null)
                finish = build(mousePin);
            if (select != null) {
                if (!finish.getPin().equals(select.getPin())) {
                    if (e.isShiftDown())
                        select.connect(finish);
                    else
                        select.join(finish);
                } else
                    circuit.interact(select);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        mouseSet(e);
    }

    public void mouseExited(MouseEvent e) {
        mouseSet(e);
    }

    public void mouseDragged(MouseEvent e) {
        mouseSet(e);
        if (SwingUtilities.isRightMouseButton(e)) {
            circuit.remove(circuit.get(mousePin));
        }
    }

    public void mouseMoved(MouseEvent e) {
        mouseSet(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll -= e.getPreciseWheelRotation();
        scale = Math.pow(power, scroll);
    }

    /*----------------*/
    /*======Main======*/

    public static void main(String[] args) {
        new MainFrame();
    }
}