package notebook.tools;

import notebook.Preferences;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class GUIToolPanel extends JPanel {

    private JButton
            subscript, bold, italic, underline, font, strikeThrough, indent, left, center, right, list, сolorWheel, print;

    public GUIToolPanel() {
        super();
        this.setBackground(new Color(246, 255, 255));
    }

    public GUIToolPanel generate(){
        return this;
    }

    public GUIToolPanel createToolButtons(){
        this
                .bindSubscriptTool()
                .bindBoldTool()
                .bindItalicTool()
                .bindUnderlineTool()
                .bindFontTool()
                .bindStrikeThroughTool()
                .bindIndentTool()
                .bindLeftAlignmentTool()
                .bindCenterAlignmentTool()
                .bindRightAlignmentTool()
                .bindListTool()
                .bindColorWheelTool()
                .bindPrintTool()
        ;
        return this;
    }

    public GUIToolPanel activateToolButtons(){
        subscript.addActionListener(new SubscriptAction());
        bold.addActionListener(new BoldAction());
        italic.addActionListener(new ItalicAction());
        underline.addActionListener(new UnderlineAction());
        font.addActionListener(new FontAndSizeAction());
        strikeThrough.addActionListener(new StrikeThroughAction());
        indent.addActionListener(new IndentAction());

        center.addActionListener(new CenterAlignmentAction());

        сolorWheel.addActionListener(new ForegroundAction());
        return this;
    }

    private GUIToolPanel bindSubscriptTool(){
        // Tool bar items
        subscript = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.SUBSCRIPT_TOOL_PATH
                )
        );
        subscript.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(subscript);
        subscript.addActionListener(event -> Preferences.ACTION_TYPE = 0);
        return this;
    }

    private GUIToolPanel bindBoldTool(){
        bold = new JButton(
                new ImageIcon(GUIToolPanelOptions.BOLD_TOOL_PATH));
        bold.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(bold);
        bold.addActionListener(event -> Preferences.ACTION_TYPE = 1);
        return this;
    }

    private GUIToolPanel bindItalicTool(){
        italic = new JButton(
                new ImageIcon(GUIToolPanelOptions.ITALIC_TOOL_PATH));
        italic.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(italic);
        italic.addActionListener(event -> Preferences.ACTION_TYPE = 2);
        return this;
    }

    private GUIToolPanel bindUnderlineTool(){
        underline = new JButton(
                new ImageIcon(GUIToolPanelOptions.UNDERLINE_TOOL_PATH));
        underline.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(underline);
        underline.addActionListener(event -> Preferences.ACTION_TYPE = 3);
        return this;
    }

    private GUIToolPanel bindFontTool(){
        font = new JButton(
                new ImageIcon(GUIToolPanelOptions.FONT_TOOL_PATH));
        font.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(font);
        font.addActionListener(event -> Preferences.ACTION_TYPE = 4);
        return this;
    }

    private GUIToolPanel bindStrikeThroughTool(){
        strikeThrough = new JButton(
                new ImageIcon(GUIToolPanelOptions.STRIKE_THROUGH_TOOL_PATH));
        strikeThrough.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(strikeThrough);
        strikeThrough.addActionListener(event -> Preferences.ACTION_TYPE = 5);
        return this;
    }

    private GUIToolPanel bindIndentTool(){
        indent = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.INDENT_TOOL_PATH
                )
        );
        indent.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(indent);
        indent.addActionListener(event -> Preferences.ACTION_TYPE = 6);
        return this;
    }

    private GUIToolPanel bindLeftAlignmentTool(){
        left = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.LEFT_ALIGNMENT_PATH
                )
        );
        left.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(left);
        left.addActionListener(event -> Preferences.ACTION_TYPE = 6);
        return this;
    }

    private GUIToolPanel bindCenterAlignmentTool(){
        center = new JButton(
                new ImageIcon(GUIToolPanelOptions.CENTER_ALIGNMENT_PATH));
        center.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(center);
        center.addActionListener(event -> Preferences.ACTION_TYPE = 7);
        return this;
    }

    private GUIToolPanel bindRightAlignmentTool(){
        right = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.RIGHT_ALIGNMENT_PATH
                )
        );
        right.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(right);
        right.addActionListener(event -> Preferences.ACTION_TYPE = 8);
        return this;
    }

    private GUIToolPanel bindListTool(){
        list = new JButton(
                new ImageIcon(GUIToolPanelOptions.LIST_TOOL_PATH));
        list.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(list);
        list.addActionListener(event -> Preferences.ACTION_TYPE = 9);
        return this;
    }

    private GUIToolPanel bindColorWheelTool(){
        сolorWheel = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.COLOR_WHEEL_TOOL_PATH
                )
        );
        сolorWheel.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(сolorWheel);
        сolorWheel.addActionListener(event -> Preferences.ACTION_TYPE = 10);
        return this;
    }

    private GUIToolPanel bindPrintTool(){
        print = new JButton(
                new ImageIcon(GUIToolPanelOptions.PRINT_TOOL_PATH));
        print.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(print);
        print.addActionListener(event -> Preferences.ACTION_TYPE = 11);
        return this;
    }

    class FontAndSizeAction extends StyledEditorKit.StyledTextAction {

        private static final long serialVersionUID = 584531387732416339L;

        private String family;

        private float fontSize;

        JDialog formatText;

        private boolean accept = false;

        JComboBox fontFamilyChooser;

        JComboBox fontSizeChooser;

        public FontAndSizeAction() {
            super("Font and Size");
        }

        public String toString() {
            return "Font and Size";
        }

        public void actionPerformed(ActionEvent event) {
            JTextPane editor = (JTextPane) getEditor(event);
            int p0 = editor.getSelectionStart();
            StyledDocument doc = getStyledDocument(editor);
            Element paragraph = doc.getCharacterElement(p0);
            AttributeSet as = paragraph.getAttributes();

            family = StyleConstants.getFontFamily(as);
            fontSize = StyleConstants.getFontSize(as);

            formatText = new JDialog(new JFrame(), "Font and Size", true);
            formatText.getContentPane().setLayout(new BorderLayout());

            JPanel choosers = new JPanel();
            choosers.setLayout(new GridLayout(2, 1));

            JPanel fontFamilyPanel = new JPanel();
            fontFamilyPanel.add(new JLabel("Font"));

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = ge.getAvailableFontFamilyNames();

            fontFamilyChooser = new JComboBox();
            for (int i = 0; i < fontNames.length; i++) {
                fontFamilyChooser.addItem(fontNames[i]);
            }
            fontFamilyChooser.setSelectedItem(family);
            fontFamilyPanel.add(fontFamilyChooser);
            choosers.add(fontFamilyPanel);

            JPanel fontSizePanel = new JPanel();
            fontSizePanel.add(new JLabel("Size"));
            fontSizeChooser = new JComboBox();
            fontSizeChooser.setEditable(true);
            fontSizeChooser.addItem(new Float(4));
            fontSizeChooser.addItem(new Float(8));
            fontSizeChooser.addItem(new Float(12));
            fontSizeChooser.addItem(new Float(16));
            fontSizeChooser.addItem(new Float(20));
            fontSizeChooser.addItem(new Float(24));
            fontSizeChooser.setSelectedItem(fontSize);
            fontSizePanel.add(fontSizeChooser);
            choosers.add(fontSizePanel);

            JButton ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    accept = true;
                    formatText.dispose();
                    family = (String) fontFamilyChooser.getSelectedItem();
                    fontSize = Float.parseFloat(Objects.requireNonNull(fontSizeChooser.getSelectedItem()).toString());
                }
            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    formatText.dispose();
                }
            });

            JPanel buttons = new JPanel();
            buttons.add(ok);
            buttons.add(cancel);
            formatText.getContentPane().add(choosers, BorderLayout.CENTER);
            formatText.getContentPane().add(buttons, BorderLayout.SOUTH);
            formatText.pack();
            formatText.setVisible(true);

            MutableAttributeSet attr = null;
            if (accept) {
                attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, family);
                StyleConstants.setFontSize(attr, (int) fontSize);
                setCharacterAttributes(editor, attr, false);
            }

        }
    }

    class ForegroundAction extends StyledEditorKit.StyledTextAction {

        private static final long serialVersionUID = 6384632651737400352L;

        private JColorChooser chooser;
        private JDialog dialog;
        private Color foreground;

        boolean noChange = false;
        boolean cancelled = false;

        public ForegroundAction() {
            super("foreground");
            init();
        }

        private void init(){
            chooser = new JColorChooser();
            dialog  = new JDialog();
        }

        public void actionPerformed(ActionEvent event) {
            JTextPane editor = (JTextPane) getEditor(event);

            if (editor == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "You need to select the editor pane before you can change the color.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int p0 = editor.getSelectionStart();
            StyledDocument document = getStyledDocument(editor);
            Element paragraph = document.getCharacterElement(p0);
            AttributeSet as = paragraph.getAttributes();
            foreground = StyleConstants.getForeground(as);

            if (foreground == null) {
                foreground = Color.BLACK;
            }
            chooser.setColor(foreground);

            JButton accept = new JButton("OK");
            accept.addActionListener(action -> {
                foreground = chooser.getColor();
                dialog.dispose();
            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(action -> {
                cancelled = true;
                dialog.dispose();
            });

            JButton none = new JButton("None");
            none.addActionListener(action -> {
                noChange = true;
                dialog.dispose();
            });

            JPanel buttons = new JPanel();
            buttons.add(accept);
            buttons.add(none);
            buttons.add(cancel);

            dialog.getContentPane().setLayout(new BorderLayout());
            dialog.getContentPane().add(chooser, BorderLayout.CENTER);
            dialog.getContentPane().add(buttons, BorderLayout.SOUTH);
            dialog.setModal(true);
            dialog.pack();
            dialog.setVisible(true);

            if (!cancelled) {
                MutableAttributeSet attr = null;
                if (editor != null) {
                    if (foreground != null && !noChange) {
                        attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, foreground);
                        setCharacterAttributes(editor, attr, false);
                    }
                }
            }
            noChange = false;
            cancelled = false;
        }
    }

    class SubscriptAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public SubscriptAction() {
            super("subscript");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attributes = kit.getInputAttributes();
                boolean subscript = !StyleConstants.isSubscript(attributes);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setSubscript(sas, subscript);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public String toString() {
            return "Subscript";
        }
    }

    class BoldAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = 9174670038684056758L;

        public BoldAction() {
            super("font-bold");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attr = kit.getInputAttributes();
                boolean bold = !StyleConstants.isBold(attr);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setBold(sas, bold);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public String toString() {
            return "Bold";
        }
    }

    class ItalicAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public ItalicAction() {
            super("font-italic");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attr = kit.getInputAttributes();
                boolean italic = !StyleConstants.isItalic(attr);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setItalic(sas, italic);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public String toString() {
            return "Italic";
        }
    }

    class UnderlineAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public UnderlineAction() {
            super("underline");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attributes = kit.getInputAttributes();
                boolean underline = !StyleConstants.isUnderline(attributes);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setUnderline(sas, underline);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public String toString() {
            return "Underline";
        }
    }

    class StrikeThroughAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public StrikeThroughAction() {
            super("strike-through");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attributes = kit.getInputAttributes();
                boolean underline = !StyleConstants.isStrikeThrough(attributes);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setStrikeThrough(sas, underline);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public String toString() {
            return "Strike-through";
        }
    }

    class IndentAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;
        private int indent = 5;

        public IndentAction() {
            super("indent");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attributes = kit.getInputAttributes();
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setFirstLineIndent(sas, indent);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public String toString() {
            return "Indent";
        }
    }

    class CenterAlignmentAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;
        private int centrify = 5;

        public CenterAlignmentAction() {
            super("indent");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledEditorKit kit = getStyledEditorKit(editor);
                MutableAttributeSet attributes = kit.getInputAttributes();
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setAlignment(sas, centrify);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public String toString() {
            return "Indent";
        }
    }
}