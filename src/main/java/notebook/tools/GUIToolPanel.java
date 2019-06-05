package notebook.tools;

import notebook.Preferences;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class GUIToolPanel extends JPanel {

    private JButton
            subscript, bold, italic, underline, font, strikeThrough, indent, left, center, right, list, colorWheel, picture, print;

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
                .bindPictureTool()
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
        left.addActionListener(new LeftAlignmentAction());
        center.addActionListener(new CenterAlignmentAction());
        right.addActionListener(new RightAlignmentAction());
        list.addActionListener(ListAction);
        colorWheel.addActionListener(new ForegroundAction());
        picture.addActionListener(new ImageInsertionAction());
        print.addActionListener(new PrintAction());
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
        return this;
    }

    private GUIToolPanel bindBoldTool(){
        bold = new JButton(
                new ImageIcon(GUIToolPanelOptions.BOLD_TOOL_PATH));
        bold.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(bold);
        return this;
    }

    private GUIToolPanel bindItalicTool(){
        italic = new JButton(
                new ImageIcon(GUIToolPanelOptions.ITALIC_TOOL_PATH));
        italic.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(italic);
        return this;
    }

    private GUIToolPanel bindUnderlineTool(){
        underline = new JButton(
                new ImageIcon(GUIToolPanelOptions.UNDERLINE_TOOL_PATH));
        underline.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(underline);
        return this;
    }

    private GUIToolPanel bindFontTool(){
        font = new JButton(
                new ImageIcon(GUIToolPanelOptions.FONT_TOOL_PATH));
        font.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(font);
        return this;
    }

    private GUIToolPanel bindStrikeThroughTool(){
        strikeThrough = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.STRIKE_THROUGH_TOOL_PATH
                )
        );
        strikeThrough.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(strikeThrough);
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
        return this;
    }

    private GUIToolPanel bindCenterAlignmentTool(){
        center = new JButton(
                new ImageIcon(GUIToolPanelOptions.CENTER_ALIGNMENT_PATH));
        center.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(center);
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
        return this;
    }

    private GUIToolPanel bindListTool(){
        list = new JButton(
                new ImageIcon(GUIToolPanelOptions.LIST_TOOL_PATH));
        list.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(list);
        return this;
    }

    private GUIToolPanel bindColorWheelTool(){
        colorWheel = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.COLOR_WHEEL_TOOL_PATH
                )
        );
        colorWheel.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(colorWheel);
        return this;
    }

    private GUIToolPanel bindPictureTool(){
        picture = new JButton(
                new ImageIcon(
                        GUIToolPanelOptions.PICTURE_TOOL_PATH
                )
        );
        picture.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(picture);
        return this;
    }

    private GUIToolPanel bindPrintTool(){
        print = new JButton(
                new ImageIcon(GUIToolPanelOptions.PRINT_TOOL_PATH));
        print.setPreferredSize(Preferences.BUTTON_SIZE);
        this.add(print);
        return this;
    }

    class FontAndSizeAction extends StyledEditorKit.StyledTextAction {

        private static final long serialVersionUID = 584531387732416339L;

        private String font_family;
        private float font_size;

        private JDialog textFormatter;

        private boolean accept = false;

        private JComboBox fontFamilyChooser;
        private JComboBox fontSizeChooser;

        public FontAndSizeAction() {
            super("font-size");
        }

        public void actionPerformed(ActionEvent event) {
            JTextPane editor = (JTextPane) getEditor(event);
            int par = editor.getSelectionStart();
            StyledDocument doc = getStyledDocument(editor);
            Element paragraph = doc.getCharacterElement(par);
            AttributeSet attributes = paragraph.getAttributes();

            font_family = StyleConstants.getFontFamily(attributes);
            font_size = StyleConstants.getFontSize(attributes);

            textFormatter = new JDialog(
                    new JFrame(), "Font and Size",
                    true
            );
            textFormatter
                    .getContentPane()
                    .setLayout(
                            new BorderLayout()
                    );

            JPanel choosers = new JPanel();
            choosers.setLayout(
                    new GridLayout(2, 1)
            );

            JPanel fontsPanel = new JPanel();
            fontsPanel
                    .add(
                            new JLabel("Font")
                    );

            GraphicsEnvironment environment
                    = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fonts
                    = environment.getAvailableFontFamilyNames();

            fontFamilyChooser = new JComboBox();
            for (int i = 0; i < fonts.length; i++) {
                fontFamilyChooser.addItem(fonts[i]);
            }
            fontFamilyChooser.setSelectedItem(font_family);
            fontsPanel.add(fontFamilyChooser);
            choosers.add(fontsPanel);

            JPanel fontSizePanel = new JPanel();
            fontSizePanel.add(new JLabel("Size"));
            fontSizeChooser = new JComboBox();
            fontSizeChooser.setEditable(true);
            fontSizeChooser.addItem(4f);
            fontSizeChooser.addItem(8f);
            fontSizeChooser.addItem(12f);
            fontSizeChooser.addItem(16f);
            fontSizeChooser.addItem(20f);
            fontSizeChooser.addItem(24f);
            fontSizeChooser.setSelectedItem(font_size);
            fontSizePanel.add(fontSizeChooser);
            choosers.add(fontSizePanel);

            JButton ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    accept = true;
                    textFormatter.dispose();
                    font_family = (String) fontFamilyChooser.getSelectedItem();
                    font_size = Float.parseFloat(Objects.requireNonNull(fontSizeChooser.getSelectedItem()).toString());
                }
            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    textFormatter.dispose();
                }
            });

            JPanel buttons = new JPanel();
            buttons.add(ok);
            buttons.add(cancel);
            textFormatter.getContentPane().add(choosers, BorderLayout.CENTER);
            textFormatter.getContentPane().add(buttons, BorderLayout.SOUTH);
            textFormatter.pack();
            textFormatter.setVisible(true);

            MutableAttributeSet attr = null;
            if (accept) {
                attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, font_family);
                StyleConstants.setFontSize(attr, (int) font_size);
                setCharacterAttributes(editor, attr, false);
            }
        }

        public String toString() {
            return "font-size";
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
                boolean strikeThrough = !StyleConstants.isStrikeThrough(attributes);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setStrikeThrough(sas, strikeThrough);
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
                StyleContext context = new StyleContext();
                StyledDocument document = (StyledDocument) editor.getDocument();
                Style style = context.getStyle("default");
                StyleConstants.setLeftIndent(style, 12);
                try {
                    document.insertString(document.getLength(), "        ", style);
                } catch (BadLocationException exception) {
                    exception.printStackTrace();
                }
            }
        }

        public String toString() {
            return "Indent";
        }
    }

    class LeftAlignmentAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public LeftAlignmentAction() {
            super("left-alignment");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledDocument document = (StyledDocument) editor.getDocument();
                SimpleAttributeSet left = new SimpleAttributeSet();
                StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
                document.setParagraphAttributes(0, document.getLength(), left, false);
            }
        }

        public String toString() {
            return "Left-alignment";
        }
    }

    class CenterAlignmentAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public CenterAlignmentAction() {
            super("center-alignment");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledDocument document = (StyledDocument) editor.getDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                document.setParagraphAttributes(0, document.getLength(), center, false);
            }
        }

        public String toString() {
            return "Center-alignment";
        }
    }

    class RightAlignmentAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public RightAlignmentAction() {
            super("right-alignment");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                StyledDocument document = (StyledDocument) editor.getDocument();
                SimpleAttributeSet right = new SimpleAttributeSet();
                StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
                document.setParagraphAttributes(0, document.getLength(), right, false);
            }
        }

        public String toString() {
            return "Right-alignment";
        }
    }

    private HTMLEditorKit.InsertHTMLTextAction ListAction = new HTMLEditorKit.InsertHTMLTextAction("Bullet", "<li> </li>", HTML.Tag.BODY, HTML.Tag.UL);

    class ImageInsertionAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public ImageInsertionAction() {
            super("image-insertion");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showOpenDialog(null);
                StyledDocument doc = (StyledDocument) editor.getDocument();
                Style style = doc.addStyle(
                        "image",
                        null
                );
                StyleConstants.setIcon(
                        style,
                        new ImageIcon(
                                chooser
                                        .getSelectedFile()
                                        .getAbsolutePath()
                        )
                );
                try {
                    doc.insertString(doc.getLength() + 1, "\n", style);
                } catch (BadLocationException exception) {
                    exception.printStackTrace();
                }
            }
        }

        public String toString() {
            return "Image-insertion";
        }
    }

    class PrintAction extends StyledEditorKit.StyledTextAction {
        private static final long serialVersionUID = -1428340091100055456L;

        public PrintAction() {
            super("print");
        }

        public void actionPerformed(ActionEvent event) {
            JEditorPane editor = getEditor(event);
            if (editor != null) {
                try {
                    editor.setContentType("text/html");
                    boolean done = editor.print();
                    if (done) {
                        JOptionPane.showMessageDialog(null, "Printing is done");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error while printing");
                    }
                } catch (Exception pex) {
                    JOptionPane.showMessageDialog(null, "Error while printing");
                    pex.printStackTrace();
                }
            }
        }

        public String toString() {
            return "Print";
        }
    }
}