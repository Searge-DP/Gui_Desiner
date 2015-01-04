package me.modmuss50.guiDesigner.componets;

public class CompButton extends Component {
    String text;

    public CompButton(int x, int y, String name, String text, int height) {
        super(x, y, name, height, text.length() * 6);
        this.text = text;
    }

    public CompButton() {
        super(0, 0, "", 0, 0);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getSaveLine() {
        return x + ":" + y + ":" + name + ":" + text + ":" + height + ":" + width;
    }

    @Override
    public void loadFromString(String string) {
        String[] parts = string.split(":");
        setX(Integer.parseInt(parts[0]));
        setY(Integer.parseInt(parts[1]));
        setName(parts[2]);
        setText(parts[3]);
        setHeight(Integer.parseInt(parts[4]));
        setWidth(Integer.parseInt(parts[5]));
    }
}
