package src.parts;

import src.Constants;
import src.DrawAssistant;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public abstract class Component implements Serializable {

    private static final List<Component> empty = new ArrayList<>();
    private List<Component> outputs = new ArrayList<>();
    private Pin pin;
    private List<Component> inputs = new ArrayList<>(), throughputs = new ArrayList<>(), next = empty;
    private boolean logic;

    public Component(Pin pin) {
        this.pin = pin;
    }

    public void update(int stage) {
        switch (stage) {
            case 0:
                logic = true;
                break;
            case 1:
                logic = logicCondition();
                next = resolveNext();
                break;
            case 2:
                logic = false;
                next = empty;
                break;
        }
    }

    protected boolean logicCondition() {
        return true;
    }

    public void interact() {
        System.out.println(this);
    }

    protected List<Component> resolveNext() {
        return logic ? throughputs : empty;
    }

    public void join(Component c) {
        throughputs.add(c);
    }

    public void connect(Component c) {
        c.inputs.add(this);
        outputs.add(c);
    }

    public void release(Component c) {
        throughputs.remove(c);
        next.remove(c);
        inputs.remove(c);
        outputs.remove(c);
    }

    public void render(Graphics2D g) {
        g.setPaint(Constants.palette.get(logic ? "brightRed" : "shadeRed"));
        DrawAssistant.drawBase(getPin(), g);
        renderIn(g);
    }

    public abstract void renderIn(Graphics2D g);

    public void renderConnections(Graphics2D g) {
        for (Component throughput : throughputs) {
            g.setPaint(Constants.palette.get(next.contains(throughput) ? "brightRed" : "shadeRed"));
            DrawAssistant.drawLine(getPin(), throughput.getPin(), g);
        }
        g.setPaint(Constants.palette.get(logic ? "yellow" : "darkYellow"));
        outputs.forEach(output -> DrawAssistant.drawLine(getPin(), output.getPin(), g));
    }

    public List<Component> getNext() {
        return next;
    }

    public Pin getPin() {
        return pin;
    }

    protected List<Component> getOutputs() {
        return outputs;
    }

    protected List<Component> getInputs() {
        return inputs;
    }

    protected List<Component> getThroughputs() {
        return throughputs;
    }

    public boolean isLogic() {
        return logic;
    }
}
