package notebook;

import file.FileManager;
import notebook.menu.GUIMenubar;
import notebook.pane.GUIPaneGenerator;
import notebook.write.GUIWritingArea;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Notebook extends JFrame {
    private JTabbedPane tabs;

    private JPanel north, center;

    public Notebook(String title) {
        setTitle(title);
        setSize(Preferences.WIDTH, Preferences.HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void init() {
        tabs = new JTabbedPane();
        north = new JPanel();
        center = new JPanel();
    }

    public void display() {
        init();

        GUIMenubar menubar = createMenubar();
        north.add(menubar, BorderLayout.CENTER);

        center.add(tabs, BorderLayout.CENTER);

        this.add(north, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private GUIMenubar createMenubar() {
        GUIMenubar menubarCreator = new GUIMenubar();

        menubarCreator
                .pinSubmenus(Arrays.asList("Notes", "Tools"))
                .pinMenuItemTo("Notes", "Preferences")
                .pinMenuItemTo("Notes", "Close")
                .pinMenuItemTo("Tools", "Open")
                .pinMenuItemTo("Tools", "Create")
                .pinMenuItemTo("Tools", "Edit")
                .pinMenuItemTo("Tools", "Save")
                .pinMenuItemTo("Tools", "Rename")
                .pinMenuItemTo("Tools", "Delete")
                .generate();


        menubarCreator.getMenuItem("Preferences")
                .addActionListener(event -> {
                    JFrame innerJFrame = new JFrame("Preferences");
                    innerJFrame.setSize(Preferences.WIDTH / 4 * 3, Preferences.HEIGHT / 4 * 3);
                    JPanel innerJPanel = new JPanel();
                    GUIPaneGenerator paneGenerator = new GUIPaneGenerator();
                    paneGenerator
                            .addTab("Font Color", new JTextArea(15, 20))
                            .addTab("Font Size", new JTextArea(15, 20))
                            .addTab("BGR Color", new JTextArea(15, 20))
                            .generate();
                    innerJPanel.add(paneGenerator.getTabs(), BorderLayout.CENTER);
                    innerJFrame.add(innerJPanel, BorderLayout.CENTER);
                    innerJFrame.setVisible(true);
                });

        menubarCreator.
                getMenuItem("Close")
                .addActionListener(event -> System.exit(0));

        menubarCreator
                .getMenuItem("Open")
                .addActionListener(event -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
                            String line;
                            StringBuilder content = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                            GUIWritingArea area = new GUIWritingArea();
                            area.makeContent(content.toString());
                            area.setBackground(area.getBackground());
                            tabs.add("Note", area);
                            reader.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
        menubarCreator.getMenuItem("Create")
                .addActionListener(event -> {
                    GUIWritingArea writing = new GUIWritingArea();
                    tabs.add(writing);
                    tabs.setSelectedIndex(tabs.getTabCount() - 1);
                    String title = JOptionPane.showInputDialog("Define the title for the note");
                    tabs.setTitleAt(tabs.getSelectedIndex(), (title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase()));
                });
        menubarCreator.getMenuItem("Delete")
                .addActionListener(event -> {
                    for (File file : Objects.requireNonNull(Database.getExistingNotes())) {
                        String[] tokens = file.getName().split("[./]");
                        if ((tokens[0].substring(0, 1).toUpperCase() + tokens[0].substring(1).toLowerCase()).equals(tabs.getTitleAt(tabs.getSelectedIndex()))) {
                            file.delete();
                            tabs.remove(tabs.getComponentAt(tabs.getSelectedIndex()));
                            break;
                        }
                    }
                });
        menubarCreator.getMenuItem("Edit")
                .addActionListener(event -> {
                    ((JTextArea) tabs.getComponentAt(tabs.getSelectedIndex())).setEditable(true);
                });
        menubarCreator.getMenuItem("Rename")
                .addActionListener(event -> {
                    String title = JOptionPane.showInputDialog("Define the title for the note");
                    Objects.requireNonNull(Database.getExistingNotes())
                            .forEach(file -> {
                                String[] tokens = file.getName().split("[./]");
                                if ((tokens[0].substring(0, 1).toUpperCase() + tokens[0].substring(1).toLowerCase()).equals(tabs.getTitleAt(tabs.getSelectedIndex()))) {
                                    file = new File(Database.getPATH() + "/" + title);
                                    try {
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(Database.getPATH() + "/" + title.toLowerCase()));
                                        writer.write(((GUIWritingArea) tabs.getComponentAt(tabs.getSelectedIndex())).getText());
                                        writer.close();
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                    tabs.setTitleAt(tabs.getSelectedIndex(), (title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase()));
                                }
                            });
                });
        menubarCreator.getMenuItem("Save")
                .addActionListener(event -> {
                    JFileChooser chooser = new JFileChooser();
                    int option = chooser.showSaveDialog(this);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = chooser.getSelectedFile();
                        FileManager.createFile(file.getAbsolutePath());
                        FileManager.write(file.getAbsolutePath(), (tabs.getComponentAt(tabs.getSelectedIndex()).toString()));
                    }

                });
        return menubarCreator;
    }
}
