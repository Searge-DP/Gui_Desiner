package me.modmuss50.guiDesigner.componets;

public class CompRegistry {

    public static Component getComponetFromClassName(String name, String line) {
        try {
           Component component = (Component) Class.forName(name).newInstance();
            component.loadFromString(line);
            return component;

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (name.equals(CompButton.class.getCanonicalName())) {
            return new CompButton(line);
        } else if (name.equals(CompImage.class.getCanonicalName())) {
            return new CompImage(line);
        } else if (name.equals(CompTextbox.class.getCanonicalName())) {
            return new CompTextbox(line);
        } else if (name.equals(CompText.class.getCanonicalName())) {
            return new CompText(line);
        }
        return null;
    }


}
