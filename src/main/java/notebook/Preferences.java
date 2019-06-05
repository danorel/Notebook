package notebook;

import java.awt.*;

public class Preferences {
    /*
        Notebook basic preferences:
        - WIDTH
        - HEIGHT
     */
    public static int WIDTH  = 640;
    public static int HEIGHT = 480;
    /*
        Writing area preferences
     */
    public final static int AREA_WIDTH = WIDTH - 100;
    public final static int AREA_HEIGHT = HEIGHT - 170;
    public final static Color BACKGROUND_COLOR = new Color( 178,192, 190);
    public final static Color FONT_COLOR = new Color(255-178, 255-192, 255-190);
    public final static Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    /*
        Button, which located on the ToolBar panel preferences
     */
    public static final Dimension BUTTON_SIZE
            = new Dimension(40, 40);
    public static final Color BUTTON_COLOR
            = new Color(175, 224, 240);
    public static int ACTION_TYPE = 0;
    /*
        Fonts preferences
     */
    public static int FONT_SIZE = 12;
    public static Font BOLD =
            new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE);
    public static Font ITALIC =
            new Font(Font.MONOSPACED, Font.ITALIC, FONT_SIZE);
}
