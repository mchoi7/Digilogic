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
            components.values().forEach(component -> component.renderWires(g));
        } catch(ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    void remove(Component c) {
        components.values().forEach(component -> component.disconnect(c));
        components.remove(c.getPoint());
    }

    Component get(Point p) {
        return components.get(p);
    }

    Map<Point, Component> getComponents() {
        return components;
    }
}
