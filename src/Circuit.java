package src;

import java.awt.*;
import java.io.*;
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
        wires.values().forEach(wire -> wire.renderConnections(g));
    }
    void remove(Wire c) {
        wires.values().forEach(wire -> wire.disconnect(c));
        wires.remove(c.getPoint());
    }

    Wire get(Pin p) {
        return wires.get(p);
    }

    void saveCircuit() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data.properties");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(wires);
            out.close();
            fileOut.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    void loadCircuit() {
        try {
            FileInputStream fileIn = new FileInputStream("data.properties");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            wires = (HashMap<Pin, Wire>) in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<Pin, Wire> getWires() {
        return wires;
    }
}
