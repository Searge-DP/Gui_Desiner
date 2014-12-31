package me.modmuss50.guiDesigner.saving;

import cpw.mods.fml.relauncher.FMLInjectionData;
import me.modmuss50.guiDesigner.GuiDesigner;

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
        file = new File(folder, name  + ".gui");
    }

    public GuiDesigner load(){
        if(file.getName().equals("New gui")){
            return null;
        }
        if(!folder.exists()){
            return null;
        }
        if(!file.exists()){
            return null;
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
