package src.parts;

import src.parts.Component;
import src.parts.Pin;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Circuit implements Serializable {
    private int clk = 6, cycle;
    private Map<Pin, Component> board = new HashMap<>();
    private List<Component> toRemove = new ArrayList<>(), toAdd = new ArrayList<>(), toInteract = new ArrayList<>();
    private List<Component> prevList = new ArrayList<>(), currentList = new ArrayList<>();

    public void update() {
        toAdd.forEach(added -> board.put(added.getPin(), added));
        toAdd.clear();

        for (Component component : board.values())
            toRemove.forEach(component::release);
        board.values().removeAll(toRemove);
        prevList.removeAll(toRemove);
        currentList.removeAll(toRemove);
        toRemove.clear();

        currentList.clear();
        currentList.addAll(toInteract);
        toInteract.forEach(Component::interact);
        toInteract.clear();

        prevList.forEach(prev -> currentList.addAll(prev.getNext()));
        prevList.forEach(component -> component.update(2));

        currentList.forEach(component -> component.update(0));
        currentList.forEach(component -> component.update(1));

        prevList.clear();
        prevList.addAll(currentList);
    }

    public void render(Graphics2D g) {
        board.values().forEach(c -> c.render(g));
        board.values().forEach(c -> c.renderConnections(g));
    }

    public void remove(Component c) {
        if (c != null)
            toRemove.add(c);
    }

    public void add(Component c) {
        if (c != null)
            toAdd.add(c);
    }

    public void interact(Component c) {
        if (c != null)
            toInteract.add(c);
    }

    public Component get(Pin p) {
        return board.get(p);
    }
}