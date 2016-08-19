package src;

import src.Components.*;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class Selection {
    Pin p, q;

    void start(Pin p) {
        this.p = p;
    }

    void stop(Pin q) {
        this.q = q;
    }

    Map<Pin, Wire> filter(Map<Pin, Wire> map) {
        int x = (p.getX() + q.getX())/2, y = (p.getY() + q.getY())/2;
        int w = abs(p.getX() - q.getX())/2, h = abs(p.getX() - q.getX())/2;
        Map<Pin, Wire> select = new HashMap<>();
        for(Pin wirePin : map.keySet())
            if(abs(wirePin.getX() - x) <= w && abs(wirePin.getY() - y) <= y)
                select.put(wirePin, map.get(wirePin));
        return select;
    }
}
