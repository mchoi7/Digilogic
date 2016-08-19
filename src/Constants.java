package src;

import java.awt.*;

public class Constants {

    final static Stroke stroke = new BasicStroke(4);
    public final static Paint[] palette = new Paint[11];

    static {
        palette[0] = new Color(0xEEFFB4);
        palette[1] = new Color(0xA7C981);
        palette[2] = new Color(0x3E6198);
        palette[3] = new Color(0x2C2072);
        palette[4] = new Color(0x1E0722);
        palette[5] = new Color(0xAA982646, true);
        palette[6] = new Color(0xAAFF4A4A, true);
        palette[7] = new Color(0xAA0F557C, true);
        palette[8] = new Color(0xAA139CE9, true);
        palette[9] = new Color(0xAAD3BB47, true);
        palette[10] = new Color(0xAA8F790D, true);
    }
}
