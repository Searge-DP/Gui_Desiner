package me.modmuss50.guiDesigner.saving;

import cpw.mods.fml.relauncher.FMLInjectionData;
import me.modmuss50.guiDesigner.GuiDesigner;
import me.modmuss50.guiDesigner.componets.CompRegistry;
import me.modmuss50.guiDesigner.componets.Component;

import java.io.*;

/**
 * Created by Mark on 31/12/2014.
 */
public class Loader {

    GuiDesigner guiDesigner;
    File file;
    File folder;

    public Loader(String name) {
        File mcDir = (File) FMLInjectionData.data()[6];
        folder = new File(mcDir, "guis");
        file = new File(folder, name + ".gui");
    }

    public GuiDesigner load() {
        if (file.getName().equals("New gui")) {
            return null;
        }
        if (!folder.exists()) {
            return null;
        }
        if (!file.exists()) {
            return null;
        }
        if (true == true) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                GuiDesigner gui = new GuiDesigner();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split(":");
                    if (strings[0].length() != 0) {
                        Component component = CompRegistry.getComponetFromClassName(strings[0]);
                        //if(component == null){
                        String newLine = line.replace(strings[0] + ":", "");
                        component.loadFromString(newLine);
                        gui.components.add(component);
                        //}
                    }
                }
                bufferedReader.close();
                return guiDesigner;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            guiDesigner = (GuiDesigner) objectInputStream.readObject();
            objectInputStream.close();
            System.out.println(file.getAbsolutePath());
            return guiDesigner;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
