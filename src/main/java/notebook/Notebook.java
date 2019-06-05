package notebook;

import file.FileManager;
import notebook.menu.GUIMenubar;
import notebook.pane.GUIPaneGenerator;
import notebook.tools.GUIToolPanel;
import notebook.write.GUIWritingArea;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Notebook extends JFrame {

    private JTabbedPane tabs;

    private JPanel south, center;
    private ArrayList<GUIWritingArea> areas;
    private ArrayList<JScrollPane> scrollPanes;

    public Notebook(String title) {
        setTitle(title);
        setSize(
                new Dimension(
                        Preferences.WIDTH,
                        Preferences.HEIGHT
                )
        );
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void init() {
        tabs        = new JTabbedPane();
        south       = new JPanel();
        center      = new JPanel();
        areas       = new ArrayList<>();
        scrollPanes = new ArrayList<>();
    }

    private void initListeners(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                switch (Preferences.ACTION_TYPE){
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:

                        break;
                }
            }
        });
    }

    public void display() {
        init();

        south.add(
                new GUIToolPanel()
                    .createToolButtons()
                    .activateToolButtons()
                    .generate(),
                BorderLayout.CENTER);

        center.add(tabs, BorderLayout.CENTER);

        this.add(south, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.setJMenuBar(
                this.buildMenubar()
        );
        this.setVisible(true);
    }

    private GUIMenubar buildMenubar() {
        GUIMenubar builder = new GUIMenubar();

        builder
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


        builder.getMenuItem("Preferences")
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

        builder.
                getMenuItem("Close")
                .addActionListener(event -> System.exit(0));

        builder
                .getMenuItem("Open")
                .addActionListener(event -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
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
        builder.getMenuItem("Create")
                .addActionListener(event -> {
                    GUIWritingArea writing = new GUIWritingArea();

                    JScrollPane scrollPane = new JScrollPane(writing);
                    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

                    areas.add(writing);
                    scrollPanes.add(scrollPane);
                    tabs.add(scrollPane);

                    writing.requestFocusInWindow();
                    tabs.setSelectedIndex(tabs.getTabCount() - 1);
                    String title = JOptionPane.showInputDialog(null, "Define the title for the note");
                    tabs.setTitleAt(tabs.getSelectedIndex(), (title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase()));
                });
        builder.getMenuItem("Delete")
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
        builder.getMenuItem("Edit")
                .addActionListener(event -> {
                    ( tabs.getComponentAt(tabs.getSelectedIndex())).setEnabled(true);
                });
        builder.getMenuItem("Rename")
                .addActionListener(event -> {
                    String title = JOptionPane.showInputDialog(null, "Define the title for the note");
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
        builder.getMenuItem("Save")
                .addActionListener(event -> {
                    JFileChooser chooser = new JFileChooser();
                    int option = chooser.showSaveDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = chooser.getSelectedFile();
                        FileManager.createFile(file.getAbsolutePath());
                        FileManager.write(file.getAbsolutePath(), (tabs.getComponentAt(tabs.getSelectedIndex()).toString()));
                    }

                });
        return builder;
    }
}
