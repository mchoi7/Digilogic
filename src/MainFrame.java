package src;

import src.Components.*;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;

public class MainFrame extends JFrame {

    /*----------------*/
    /*=====Fields=====*/

    private final static long FPS = 60, MSPF = 1000/FPS; /* MSPF -> Milli-Seconds Per Frame */
    private Circuit circuit = new Circuit();
    private Input input;
    private boolean running;
    private int xCamera, yCamera;

    /*----------------*/
    /*==Constructor===*/

    private MainFrame() {
        setTitle("Digilogic");
        setSize(800, 600);
        setLocationRelativeTo(null); /* Center the window */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }

    /*----------------*/
    /*===Functions====*/

    private void init() {
        createBufferStrategy(3); /* Triple check that rendering is loadable */
        input = new Input(this);
        addMouseListener(input);
        addMouseMotionListener(input);
        addKeyListener(input);
        circuit.loadCircuit();
        running = true;
        new Thread(() -> loop(this::update)).start(); /* Thread's run() is loop(update()) */
        new Thread(() -> loop(this::render)).start(); /* Thread's run() is loop(render()) */
    }

    private void loop(Runnable runnable) {
        long startTime;
        while(running) {
            startTime = System.currentTimeMillis();
            try {
            runnable.run();
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
            try {
                long sleepTime = MSPF - System.currentTimeMillis() + startTime;
                if(sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        circuit.update();
        input.update();
    }

    private void render() {
        Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();
        g.setPaint(Constants.palette[1]);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(xCamera, yCamera);
        g.setStroke(Constants.stroke);
        g.setPaint(Constants.palette[0]);
        for(int j = 0; j < 50; j++)
            for(int i = 0; i < 50; i++)
                g.drawOval(i*Wire.UNIT + Wire.UNIT/4, j* Wire.UNIT + Wire.UNIT/4, Wire.UNIT/2, Wire.UNIT/2);
        circuit.render(g);
        g.dispose();
        getBufferStrategy().show();
    }

    /*----------------*/
    /*===Accessors====*/

    Circuit getCircuit() {
        return circuit;
    }
    int getxCamera() {
        return xCamera;
    }
    int getyCamera() {
        return yCamera;
    }

    /*----------------*/
    /*====Mutators====*/

    void setxCamera(int xCamera) {
        this.xCamera = xCamera;
    }
    void setyCamera(int yCamera) {
        this.yCamera = yCamera;
    }

    /*----------------*/
    /*======Main======*/

    public static void main(String[] args) {
        new MainFrame();
    }
}
