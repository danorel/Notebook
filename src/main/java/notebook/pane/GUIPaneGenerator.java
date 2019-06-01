package notebook.pane;

import javax.swing.*;
import java.awt.*;

public class GUIPaneGenerator {
    private JTabbedPane tabs;

    public GUIPaneGenerator(){
        tabs = new JTabbedPane();
    }

    public void generate(){
        tabs.setSelectedIndex(tabs.getTabCount() - 1);
    }

    public GUIPaneGenerator addTab(String tabName, Component component){
        tabs.addTab(tabName, component);
        return this;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }
}
