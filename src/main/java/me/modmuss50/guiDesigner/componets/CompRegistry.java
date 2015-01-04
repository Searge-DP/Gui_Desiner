package me.modmuss50.guiDesigner.componets;

public class CompRegistry {

    public static Component getComponetFromClassName(String string) {
        try {
            return (Component) Class.forName(string).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (string.equals(CompButton.class.getCanonicalName())) {
            return new CompButton(0, 0, "", "", 0);
        } else if (string.equals(CompImage.class.getCanonicalName())) {
            return new CompImage(0, 0, "", "", 0, 0);
        } else if (string.equals(CompTextbox.class.getCanonicalName())) {
            return new CompTextbox(0, 0, "", "", 0, 0);
        } else if (string.equals(CompText.class.getCanonicalName())) {
            return new CompText(0, 0, "", "", 0);
        }
        return null;
    }


}
