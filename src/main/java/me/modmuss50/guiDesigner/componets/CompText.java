package me.modmuss50.guiDesigner.componets;

public class CompText extends Component {
    String text;

    public CompText(int x, int y, String name, String text, int height) {
        super(x, y, name, height, text.length() * 5);
        this.text = text;
    }

    public CompText() {
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
