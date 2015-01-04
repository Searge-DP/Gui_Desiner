package me.modmuss50.guiDesigner.componets;

public class CompImage extends Component {
    String image;

    public CompImage(int x, int y, String name, String image, int height, int length) {
        super(x, y, name, height, length);
        this.image = image;
    }

    public CompImage() {
        super(0, 0, "", 0, 0);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getSaveLine() {
        return x + ":" + y + ":" + name + ":" + image + ":" + height + ":" + width;
    }

    @Override
    public void loadFromString(String string) {
        System.out.println(string);
        String[] parts = string.split(":");
        setX(Integer.parseInt(parts[0]));
        setY(Integer.parseInt(parts[1]));
        setName(parts[2]);
        setImage(parts[3]);
        setHeight(Integer.parseInt(parts[4]));
        setWidth(Integer.parseInt(parts[5]));
    }
}
