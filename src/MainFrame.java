package src;

import src.Components.*;
import src.Inputs.Keyboard;
import src.Inputs.Mouse;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    /*----------------*/
    /*=====Fields=====*/

    private final static long FPS = 60, MSPF = 1000/FPS;

    private Circuit circuit = new Circuit();
    private boolean running;
    private int xCamera, yCamera;

    /*----------------*/
    /*==Constructor===*/

    private MainFrame() {
        setSize(1200, 1000);
        setLocationRelativeTo(null); /* Center the window */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        init();
    }

    /*----------------*/
    /*===Functions====*/

    private void init() {
        createBufferStrategy(3); /* Triple check that rendering is loadable */
        addMouseListener(new Mouse(this));
        addKeyListener(new Keyboard());
        running = true;
        new Thread(() -> loop(this::update)).start(); /* Thread's run() is loop(update()) */
        new Thread(() -> loop(this::render)).start(); /* Thread's run() is loop(render()) */
    }

    private void loop(Runnable runnable) {
        long startTime;
        while(running) {
            startTime = System.currentTimeMillis();
            runnable.run();
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
    }

    private void render() {
        Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();
        g.setPaint(Constants.palette[1]);
        g.translate(xCamera, yCamera);
        g.fillRect(0, 0, getWidth(), getHeight());
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

    public Circuit getCircuit() {
        return circuit;
    }
    public int getxCamera() {
        return xCamera;
    }
    public int getyCamera() {
        return yCamera;
    }

    /*----------------*/
    /*====Mutators====*/

    public void setxCamera(int xCamera) {
        this.xCamera = xCamera;
    }
    public void setyCamera(int yCamera) {
        this.yCamera = yCamera;
    }

    /*----------------*/
    /*======Main======*/

    public static void main(String[] args) {
        new MainFrame();
    }
}
