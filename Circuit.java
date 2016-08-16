import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

class Circuit {
    private Map<Point, Component> components = new HashMap<>();

    void update() {
        components.values().forEach(Component::update);
    }

    void render(Graphics2D g) {
        try {
            components.values().forEach(component -> component.renderIn(g));
            components.values().forEach(component -> component.renderOut(g));
        } catch(ConcurrentModificationException e) {}
    }

    void modify() {

    }

    void remove(Component c) {
        c.disconnect();
    }

    Component get(Point p) {
        return components.get(p);
    }


    Map<Point, Component> getComponents() {
        return components;
    }
}
