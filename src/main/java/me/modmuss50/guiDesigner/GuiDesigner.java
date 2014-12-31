package me.modmuss50.guiDesigner;

import me.modmuss50.guiDesigner.componets.Componet;
import me.modmuss50.guiDesigner.componets.TextLabel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class GuiDesigner extends GuiScreen {

    private static final ResourceLocation baseTexture = new ResourceLocation("network:textures/gui/base.png");
    private static final ResourceLocation baseTextureSmall = new ResourceLocation("network:textures/gui/baseSmall.png");

    /**
     * The X size of the inventory window in pixels.
     */
    public int xSize = 176;
    /**
     * The Y size of the inventory window in pixels.
     */
    public int ySize = 166;

    ArrayList<Componet> componets = new ArrayList<Componet>();
    public int selectedComponet = -1;
    Componet movingComponet = null;

    GuiTextField nameFeild;

    GuiTextField textLableBox;

    boolean itemSelectionBox = false;


    int scrollerPos = 0;


    @Override
    public void initGui() {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        buttonList.clear();
        buttonList.add(new GuiButton(0, -ySize / 2 + 150, l + ySize - 24, 20, 20, "+"));
        buttonList.add(new GuiButton(1, -ySize / 2 + 128, l + ySize - 24, 20, 20, "-"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        RenderManager.instance.renderEngine.bindTexture(baseTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(-ySize / 2, l, 0, 0, this.xSize, this.ySize);

        this.drawTexturedModalRect(this.width - ySize / 2 - 50, l, 0, 0, this.xSize, this.ySize);


        this.fontRendererObj.drawString("Components", -ySize / 2 + 100, l + 5, Color.gray.getRGB());

        this.fontRendererObj.drawString("Settings", this.width - ySize / 2 + 10 - 15, l + 5, Color.gray.getRGB());


        this.drawColourOnScreen(0, 0, 0, 255, -ySize / 2 + 90, l + 25, 75, 110, 0);
        this.drawColourOnScreen(0, 0, 0, 255, this.width - ySize / 2 - 45, l + 15, 125, 145, 0);

        if (!componets.isEmpty() && selectedComponet < componets.size() && selectedComponet != -1) {
            this.drawColourOnScreen(50, 50, 50, 255, -ySize / 2 + 95, l + 29 + (selectedComponet * 10), 65, 10, 0);


            Componet componet = componets.get(selectedComponet);
            this.fontRendererObj.drawString("Name: ", this.width - ySize / 2 + 10 - 50, l + 20, Color.white.getRGB());
            if (nameFeild == null) {
                nameFeild = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 20, 70, 10);
            }

            if (nameFeild.getText().equals("ERROR")) {
                nameFeild.setText(componets.get(selectedComponet).getName());
            }
            nameFeild.drawTextBox();
            nameFeild.setTextColor(Color.white.getRGB());

            if (componet instanceof TextLabel) {
                TextLabel textLabel = (TextLabel) componet;
                this.fontRendererObj.drawString("Text: ", this.width - ySize / 2 + 10 - 50, l + 35, Color.white.getRGB());
                if (textLableBox == null) {
                    textLableBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }

                if (textLableBox.getText().equals("ERROR")) {
                    textLableBox.setText(textLabel.getText());
                }
                textLableBox.drawTextBox();
                textLableBox.setTextColor(Color.white.getRGB());
            } else {
                textLableBox = null;
            }
        } else {
            nameFeild = null;
        }


        int i = 0;
        for (Componet componet : componets) {
            this.fontRendererObj.drawString(componet.getName(), -ySize / 2 + 95, l + 30 + (i * 10), Color.white.getRGB());
            i++;
            if (componet instanceof TextLabel) {
                TextLabel textLabel = (TextLabel) componet;
                this.fontRendererObj.drawString(textLabel.getText(), k + textLabel.getX(), l + textLabel.getY(), Color.gray.getRGB());
            }


            if (movingComponet == componet) {
                this.drawColourOnScreen(50, 50, 50, 255, k + componet.getX(), l + componet.getY(), 1, componet.getHeight(), 0);
                this.drawColourOnScreen(50, 50, 50, 255, k + componet.getX(), l + componet.getY(), componet.getWidth(), 1, 0);
                this.drawColourOnScreen(50, 50, 50, 255, k + componet.getX() + componet.getWidth(), l + componet.getY(), 1, componet.getHeight(), 0);
                this.drawColourOnScreen(50, 50, 50, 255, k + componet.getX(), l + componet.getY() + componet.getHeight(), componet.getWidth() + 1, 1, 0);
            }
        }

        if (hasScrollBar()) {
            this.drawColourOnScreen(160, 160, 160, 255, k + -ySize / 2 + 35, l + 25, 5, 110, 0);
            this.drawColourOnScreen(90, 90, 90, 255, k + -ySize / 2 + 35, l + 25 + scrollerPos, 5, 10, 0);

        }

        if (itemSelectionBox) {
            int k2 = (this.width - 175) / 2;
            int l2 = (this.height - 67) / 2;
            RenderManager.instance.renderEngine.bindTexture(baseTextureSmall);
            this.drawTexturedModalRect(k, l, 0, 0, 175, 67);
        }

        mouse();


        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (!itemSelectionBox) {
            if (button.id == 0) {
                componets.add(new TextLabel(10, 10, "Label " + Integer.toString(componets.size() + 1), "Text goes here", 10));
                //itemSelectionBox = true;
            }
            if (button.id == 1 && !componets.isEmpty() && selectedComponet < componets.size() && selectedComponet != -1) {
                componets.remove(selectedComponet);
                selectedComponet = -1;
            }
        }

    }

    public boolean hasScrollBar() {
        return componets.size() > 9;
    }

    @Override
    public void mouseClicked(int x, int y, int par3) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        if (!itemSelectionBox) {
            if (x > -ySize / 2 + 90 && x < -ySize / 2 + 90 + 70) {
                if (y > l + 25 && y < l + 135) {
                    int num = -(l + 25 - y) / 10;
                    setSelectedComponet(num);
                }
            }
            if (nameFeild != null) {
                nameFeild.mouseClicked(x, y, par3);
            }
            if (textLableBox != null) {
                textLableBox.mouseClicked(x, y, par3);
            }
        }


        super.mouseClicked(x, y, par3);
    }

    public void setSelectedComponet(int num) {
        if (componets.isEmpty()) {
            return;
        }
        selectedComponet = num;
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        if (nameFeild == null) {
            nameFeild = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 20, 70, 10);
        }
        nameFeild.setText("ERROR");
        Componet componet;
        if (selectedComponet <= componets.size()) {
            if (componets.size() == 1) {
                componet = componets.get(0);
            } else {
                componet = componets.get(selectedComponet);
            }
            if (componet instanceof TextLabel) {
                if (textLableBox == null) {
                    textLableBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }
                textLableBox.setText("ERROR");
            }
        } else {
            selectedComponet = -1;
        }

    }

    public void mouse() {
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        if (itemSelectionBox) {
            return;
        }

        if (movingComponet == null) {
            for (Componet componet : componets) {
                boolean flag = x >= k + componet.getX() && x < k + componet.getX() + componet.getWidth() && y >= l + componet.getY() && y < l + componet.getY() + componet.getHeight();

                if (flag && Mouse.isButtonDown(0)) {
                    movingComponet = componet;
                }
            }
        }

        if (movingComponet != null) {
            setSelectedComponet(componets.indexOf(movingComponet));

            //if(x - k > 0 && x - k < xSize - movingComponet.getWidth()){
            movingComponet.setX(x - k - (movingComponet.getWidth() / 2));
            // }

            // if(y - l > 0 && y - l < ySize - movingComponet.getHeight()){
            movingComponet.setY(y - l - (movingComponet.getHeight() / 2));
            // }

        }
        if (!Mouse.isButtonDown(0) && movingComponet != null) {
            movingComponet = null;
        }

        if(hasScrollBar()){
            //TODO make this scroll :)
        }
    }


    @Override
    protected void keyTyped(char par1, int par2) {
        if (!itemSelectionBox) {
            if (nameFeild != null && nameFeild.textboxKeyTyped(par1, par2)) {
                if (nameFeild.getText().length() != 0) {
                    componets.get(selectedComponet).setName(nameFeild.getText());
                }
            } else if (textLableBox != null && textLableBox.textboxKeyTyped(par1, par2)) {
                if (textLableBox.getText().length() != 0) {
                    Componet componet = componets.get(selectedComponet);
                    if (componet instanceof TextLabel) {
                        ((TextLabel) componet).setText(textLableBox.getText());
                        componet.setWidth(textLableBox.getText().length() * 5);
                    }
                }
            } else {
                super.keyTyped(par1, par2);
            }
        } else {
            super.keyTyped(par1, par2);
        }
    }

    public static void drawColourOnScreen(int colour, int alpha, double posX, double posY, double width, double height, double zLevel) {
        int r = (colour >> 16 & 0xff);
        int g = (colour >> 8 & 0xff);
        int b = (colour & 0xff);
        drawColourOnScreen(r, g, b, alpha, posX, posY, width, height, zLevel);
    }

    public static void drawColourOnScreen(int r, int g, int b, int alpha, double posX, double posY, double width, double height, double zLevel) {
        if (width <= 0 || height <= 0) {
            return;
        }
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(r, g, b, alpha);
        tessellator.addVertex(posX, posY + height, zLevel);
        tessellator.addVertex(posX + width, posY + height, zLevel);
        tessellator.addVertex(posX + width, posY, zLevel);
        tessellator.addVertex(posX, posY, zLevel);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}