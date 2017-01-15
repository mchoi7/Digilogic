package src;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int UNIT = 40;
    public final static Stroke stroke = new BasicStroke(UNIT / 20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4,
            new float[]{UNIT / 10, UNIT / 10}, 0), basic = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public final static Map<String, Paint> palette = new HashMap<>();

    static {
        palette.put("lightYellow",new Color(0xEEFFB4));
        palette.put("lightGreen",new Color(0xA7C981));
        palette.put("mellowBlue", new Color(0x3E6198));
        palette.put("darkBlue", new Color(0x2C2072));
        palette.put("darkPurple", new Color(0x1E0722));
        palette.put("shadeRed", new Color(0x982646));
        palette.put("brightRed", new Color(0xFF4A4A));
        palette.put("darkCyan", new Color(0x0F557C));
        palette.put("brightCyan", new Color(0x139CE9));
        palette.put("yellow", new Color(0xD3BB47));
        palette.put("darkYellow", new Color(0x8F790D));
        palette.put("white", new Color(0xFFFFFF));
    }
}
