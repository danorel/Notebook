package notebook.write;

import org.jsoup.nodes.Document;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class GUIWritingArea extends JTextPane {
    /*
        Content of the file. It consists of:
            - title
            - page content
     */
    private StringBuilder html;
    private StringBuilder body;
    /*
            Content of the file is saved in the object of Document class
    */
    private StyledDocument document;
    /*
        The attribute set of the writing area
     */
    private SimpleAttributeSet attributes;

    public GUIWritingArea(){
        html = new StringBuilder(
                "<html>"+
                    "<head>"+
                        "<title>"+
                        "</title>"+
                    "</head>"+
                    "<body>"+
                        "<h3 id=\"title\">"+
                        "</h3>"+
                        "<p id=\"text\">"+
                        "</p>"+
                    "</body>"+
                "</html>"
        );
        document = this.getStyledDocument();
        attributes = new SimpleAttributeSet();
    }

    private void connectAttributes(){
        StyleConstants.setItalic(attributes, true);
        StyleConstants.setForeground(attributes, Color.red);
        StyleConstants.setBackground(attributes, Color.blue);
    }

    public GUIWritingArea makeTitle(String title){
        try {
            document
                    .insertString(
                            document.getLength(), title, attributes
                    );
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public GUIWritingArea makeContent(String content){
        try {
            document
                    .insertString(
                            document.getLength(), content, attributes
                    );
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public String getWholeFileContent(){
        StringBuilder content = new StringBuilder();
        try {
            content.append(document.getText(0, document.getLength()));
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public String toString() {
        return getWholeFileContent();
    }
}