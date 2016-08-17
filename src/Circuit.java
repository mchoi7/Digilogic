package src;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import src.Components.Pin;
import src.Components.Wire;

public class Circuit {
    private Map<Pin, Wire> wires = new HashMap<>();

    void update() {
        wires.values().forEach(Wire::update);
    }

    void render(Graphics2D g) {
        wires.values().forEach(wire -> wire.renderIn(g));
        wires.values().forEach(wire -> wire.renderOut(g));
        wires.values().forEach(wire -> wire.renderWires(g));
    }
    public void remove(Wire c) {
        wires.values().forEach(wire -> wire.disconnect(c));
        wires.remove(c.getPoint());
    }

    public Wire get(Pin p) {
        return wires.get(p);
    }

    public Map<Pin, Wire> getWires() {
        return wires;
    }
}
