package notebook;

import notebook.menu.GUIMenubar;
import notebook.pane.GUIPaneGenerator;
import notebook.tools.GUIToolPanel;
import notebook.write.GUIWritingArea;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Notebook extends JFrame {

    private JTabbedPane tabs;

    private JPanel south, center;
    private ArrayList<GUIWritingArea> areas;
    private ArrayList<JScrollPane> panes;

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
        panes = new ArrayList<>();
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
                   // Some functions will be here...
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
                            FileReader reader = new FileReader(file.getPath());
                            GUIWritingArea area = new GUIWritingArea();
                            area.read(reader, file.getPath());

                            tabs.add(file.getName().split("[.]")[0], area);
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
                    panes.add(scrollPane);
                    tabs.add(scrollPane);

                    writing.requestFocusInWindow();
                    tabs.setSelectedIndex(tabs.getTabCount() - 1);
                    tabs.setTitleAt(
                            tabs.getSelectedIndex(),
                            JOptionPane.showInputDialog(
                                    null,
                                    "Define the title for the note"
                            )
                    );
                });
        builder.getMenuItem("Delete")
                .addActionListener(event -> {
                    if(tabs.getTabCount() == 0){
                        JOptionPane.showMessageDialog(
                                null,
                                "Create the note.\nNone of the notes exists that is why this operation cannot be used."
                        );
                    } else {
                        JFileChooser chooser = new JFileChooser();
                        int option = chooser.showOpenDialog(null);
                        if(option == JFileChooser.APPROVE_OPTION){
                            File file = chooser.getSelectedFile();
                            String filename = file.getName().split("[.]")[0];
                            file.delete();
                            for(int index = 0; index < tabs.getTabCount(); index++){
                                if(filename.equals(tabs.getTitleAt(index))){
                                    tabs.remove(index);
                                }
                            }
                        }
                    }
                });
        builder.getMenuItem("Edit")
                .addActionListener(event -> {
                     tabs
                             .getComponentAt(tabs.getSelectedIndex())
                             .setEnabled(true);
                });
        builder.getMenuItem("Rename")
                .addActionListener(event -> {
                    if(tabs.getTabCount() == 0){
                        JOptionPane.showMessageDialog(
                                null,
                                "Create the note!\nNone of the notes exists that is why this operation cannot be used."
                        );
                    } else {
                        String title = JOptionPane.showInputDialog(
                                null,
                                "Define the title for current note"
                        );
                        tabs.setTitleAt(
                                tabs.getSelectedIndex(),
                                (title)
                        );
                    }
                });
        builder.getMenuItem("Save")
                .addActionListener(event -> {
                    if(tabs.getTabCount() == 0){
                        JOptionPane.showMessageDialog(
                                null,
                                "Create the note!\nNone of the notes exists that is why this operation cannot be used."
                        );
                    } else {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setDialogTitle("Save dialog");
                        chooser
                                .setSelectedFile(
                                        new File(tabs.getTitleAt(tabs.getSelectedIndex()) + ".doc")
                                );
                        int option = chooser.showSaveDialog(null);
                        if (option == JFileChooser.APPROVE_OPTION) {
                            File file = chooser.getSelectedFile();
                            BufferedOutputStream out = null;
                            try {
                                out = new BufferedOutputStream(new FileOutputStream(file));
                            } catch (FileNotFoundException exception) {
                                exception.printStackTrace();
                            }
                            StyledEditorKit kit = (StyledEditorKit)
                                    areas.get(tabs.getSelectedIndex())
                                            .getEditorKit();
                            StyledDocument document = (StyledDocument) areas.get(tabs.getSelectedIndex()).getDocument();
                            try {
                                assert out != null;
                                kit.write(out, document, 0, document.getLength());
                            } catch (IOException | BadLocationException exception) {
                                exception.printStackTrace();
                            }
                            tabs
                                    .getComponentAt(tabs.getSelectedIndex())
                                    .setEnabled(false);
                        }
                    }
                });
        return builder;
    }
}