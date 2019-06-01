package notebook.menu;

import java.util.List;

public interface GUIMenubarManager {
    void generate();
    GUIMenubar createMenubar(String fieldName);
    GUIMenubar pinSubmenus(List<String> fieldNames);
    GUIMenubar pinMenuItemTo(String menuName, String fieldName);
}
