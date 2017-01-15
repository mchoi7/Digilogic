package src;

import src.parts.Pin;

import java.awt.*;

import static src.Constants.UNIT;

public class DrawAssistant {
    public static void drawRect(Pin A, Pin B, Graphics2D g) {
        g.drawRect(Math.min(A.getX(), B.getX()) * UNIT, Math.min(A.getY(), B.getY()) * UNIT,
                Math.abs(A.getX() - B.getX()) * UNIT, Math.abs(A.getY() - B.getY()) * UNIT);
    }

    public static void drawRoundRect(Pin pin, double width, double radius, Graphics2D g) {
        g.drawRoundRect((int) (UNIT * ((1 - width) / 2 + pin.getX())), (int) (UNIT * ((1 - width) / 2 + pin.getY())),
                (int) (width * UNIT), (int) (width * UNIT), (int) (UNIT * radius), (int) (UNIT * radius));
    }

    public static void drawOval(Pin pin, double radius, Graphics2D g) {
        g.drawOval((int) (UNIT * ((1 - radius) / 2 + pin.getX())), (int) (UNIT * ((1 - radius) / 2 + pin.getY())),
                (int) (radius * UNIT), (int) (radius * UNIT));
    }

    public static void drawOval(Pin pin, double xOff, double yOff, double radius, Graphics2D g) {
        g.drawOval((int) (UNIT * (xOff + (1 - radius) / 2 + pin.getX())), (int) (UNIT * (yOff + (1 - radius) / 2 + pin.getY())),
                (int) (radius * UNIT), (int) (radius * UNIT));
    }

    public static void fillOval(Pin pin, Graphics2D g) {
        g.fillOval(UNIT * pin.getX(), UNIT * pin.getY(), UNIT, UNIT);
    }

    public static void drawLine(Pin A, Pin B, Graphics2D g) {
        int dx = A.getX() - B.getX(), dy = A.getY() - B.getY();
        double dr = 2.5 * Math.hypot(dx, dy);
        double xOff = dx / dr, yOff = dy / dr;
        g.drawLine((int) (UNIT * A.getX() + (-xOff + .5) * UNIT), (int) (UNIT * A.getY() + (-yOff + .5) * UNIT),
                (int) (UNIT * B.getX() + (xOff + .5) * UNIT), (int) (UNIT * B.getY() + (yOff + .5) * UNIT));
    }

    public static void drawLine(Pin A, double x, double y, double x2, double y2, Graphics2D g) {
        g.drawLine((int) (UNIT * A.getX() + (x + .5) * UNIT), (int) (UNIT * A.getY() + (y + .5) * UNIT),
                (int) (UNIT * A.getX() + (x2 + .5) * UNIT), (int) (UNIT * A.getY() + (y2 + .5) * UNIT));
    }

    public static void drawGrid(Graphics2D g) {
        Stroke s = g.getStroke();
        g.setStroke(Constants.stroke);
        for (int i = 0; i <= 15; i++)
            g.drawLine(UNIT * i, 0, UNIT * i, 15 * UNIT);
        for (int i = 0; i <= 15; i++)
            g.drawLine(0, UNIT * i, 15 * UNIT, UNIT * i);
        g.setStroke(s);
    }

    public static void drawBase(Pin pin, Graphics2D g) {
        drawRoundRect(pin, .8, .2, g);
    }

    public static void drawPowerless(Pin pin, Graphics2D g) {
        drawRoundRect(pin, .7, .2, g);
    }

    public static void drawGround(Pin pin, Graphics2D g) {
        drawLine(pin, -.3, .05, .3, .05, g);
        drawLine(pin, -.25, .2, .25, .2, g);
    }

    public static void drawSource(Pin pin, Graphics2D g) {
        drawLine(pin, 0, -.3, 0, -.15, g);
        drawOval(pin, .5, g);
    }

    public static void drawTransistor(Pin pin, Graphics2D g) {
        drawOval(pin, .5, g);
    }

    public static void drawAnd(Pin pin, Graphics2D g) {
        drawOval(pin, 0, -.15, .2, g);
        drawOval(pin, -.025, .1, .3, g);
        drawLine(pin, .1, .2, .175, .225, g);
    }

    public static void drawOr(Pin pin, Graphics2D g) {
        drawLine(pin, .1, .2, .1, -.2, g);
        drawLine(pin, -.1, .2, -.1, -.2, g);
    }

    public static void drawNot(Pin pin, Graphics2D g) {
        drawLine(pin, 0, -.2, 0, .1, g);
        drawLine(pin, 0, .2, 0, .2, g);
    }

    public static void drawXor(Pin pin, Graphics2D g) {
        drawOval(pin, .5, g);
        drawLine(pin, 0, -.2, 0, .2, g);
        drawLine(pin, .2, 0, -.2, 0, g);
    }

    public static void drawNotSide(Pin pin, Graphics2D g) {
        drawLine(pin, -.25, -.2, -.25, .1, g);
        drawLine(pin, -.25, .2, -.25, .2, g);
    }
}
