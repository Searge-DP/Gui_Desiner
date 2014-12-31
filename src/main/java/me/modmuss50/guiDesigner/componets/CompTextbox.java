package me.modmuss50.guiDesigner.componets;

/**
 * Created by Mark on 31/12/2014.
 */
public class CompTextbox extends Componet {
    String text;

    public CompTextbox(int x, int y, String name, String text, int height, int lenght) {
        super(x, y, name, height, lenght);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}