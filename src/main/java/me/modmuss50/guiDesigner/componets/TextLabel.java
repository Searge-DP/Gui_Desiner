package me.modmuss50.guiDesigner.componets;

/**
 * Created by Mark on 30/12/2014.
 */
public class TextLabel extends Componet{
    String text;

    public TextLabel(int x, int y, String name, String text, int height) {
        super(x, y, name, height, text.length() * 5);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
