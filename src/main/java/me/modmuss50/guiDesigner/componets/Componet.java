package me.modmuss50.guiDesigner.componets;

import java.io.Serializable;

/**
 * Created by Mark on 30/12/2014.
 */
public class Componet implements Serializable {

    int x;

    int y;

    int height;
    int width;

    String name;

    public Componet(int x, int y, String name, int height, int width) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.height = height;
        this.width = width;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
