package notebook.menu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GUIMenubar extends JMenuBar implements GUIMenubarManager {

    // Menu bar items and notes.menuBar bar itself
    private ArrayList<JMenu> menus;
    private ArrayList<JMenuItem> menuItems;

    public GUIMenubar() {
        menus = new ArrayList<>();
        menuItems = new ArrayList<>();
    }

    @Override
    public void generate(){
        menus
                .forEach(this::add);
    }

    @Override
    public GUIMenubar createMenubar(String fieldName){
        menus.add(new JMenu(fieldName));
        return this;
    }

    @Override
    public GUIMenubar pinSubmenus(List<String> fieldNames){
        fieldNames
                .forEach(fieldName -> {
                    JMenu currentMenu = new JMenu(fieldName);
                    menus.add(currentMenu);
                });
        return this;
    }

    @Override
    public GUIMenubar pinMenuItemTo(String menuName, String fieldName) {
        menus
                .forEach(menuElement -> {
                    if(menuElement.getText().equals(menuName)){
                        JMenuItem field = new JMenuItem(fieldName);
                        menuElement.add(field);
                        menuItems.add(field);
                    }
                });
        return this;
    }

    public JMenuItem getMenuItem(String fieldName){
        JMenuItem []items = new JMenuItem[1];
        menuItems
                .forEach(field -> {
                    if(field.getText().equals(fieldName)){
                        items[0] = field;
                    }
                });
        return items[0];
    }
}
