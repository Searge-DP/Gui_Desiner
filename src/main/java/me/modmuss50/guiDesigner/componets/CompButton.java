package me.modmuss50.guiDesigner.componets;

/**
 * Created by Mark on 31/12/2014.
 */
public class CompButton extends Componet {
    String text;

    public CompButton(int x, int y, String name, String text, int height) {
        super(x, y, name, height, text.length() * 6);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
