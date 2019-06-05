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
    /*
        Button, which located on the ToolBar panel preferences
     */
    public static final Dimension BUTTON_SIZE
            = new Dimension(40, 40);
}
