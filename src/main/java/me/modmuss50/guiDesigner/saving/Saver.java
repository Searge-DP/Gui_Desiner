package me.modmuss50.guiDesigner.saving;

import me.modmuss50.guiDesigner.GuiDesigner;
import me.modmuss50.guiDesigner.componets.Component;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.*;

/**
 * Created by Mark on 31/12/2014.
 */
public class Saver {

    GuiDesigner guiDesigner;
    File file;
    File folder;

    public Saver(GuiDesigner guiDesigner, String name) {
        this.guiDesigner = guiDesigner;
        File mcDir = (File) FMLInjectionData.data()[6];
        folder = new File(mcDir, "guis");
        file = new File(folder, name + ".gui");
    }

    public void save() {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (true == true) {
            try {
                PrintWriter printWriter = new PrintWriter(file, "UTF-8");
                for (Component component : guiDesigner.components) {
                    printWriter.println(component.getClass().getCanonicalName() + ":" + component.getSaveLine());
                }
                printWriter.close();
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(guiDesigner);
            objectOutputStream.close();
            System.out.println(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
