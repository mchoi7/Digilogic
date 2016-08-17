package src;

import java.awt.*;

public class Constants {

    final static Stroke stroke = new BasicStroke(5);
    public final static Paint[] palette = new Paint[11];

    static {
        palette[0] = new Color(0xEEFFB4);
        palette[1] = new Color(0xA7C981);
        palette[2] = new Color(0x3E6198);
        palette[3] = new Color(0x2C2072);
        palette[4] = new Color(0x1E0722);
        palette[5] = new Color(0x982646);
        palette[6] = new Color(0xFF4A4A);
        palette[7] = new Color(0x0F557C);
        palette[8] = new Color(0x139CE9);
        palette[9] = new Color(0xD3BB47);
        palette[10] = new Color(0x8F790D);
    }
}
