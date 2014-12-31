package me.modmuss50.guiDesigner;

import me.modmuss50.guiDesigner.componets.CompButton;
import me.modmuss50.guiDesigner.componets.Componet;
import me.modmuss50.guiDesigner.componets.TextLabel;
import me.modmuss50.guiDesigner.componets.mc.GuiTextField;
import me.modmuss50.guiDesigner.saving.Saver;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import java.io.Serializable;

import java.awt.*;
import java.util.ArrayList;

public class GuiDesigner extends GuiScreen implements Serializable {

    private static final ResourceLocation baseTexture = new ResourceLocation("guidesigner:textures/gui/base.png");
    private static final ResourceLocation baseTextureSmall = new ResourceLocation("guidesigner:textures/gui/baseSmall.png");
    private static final ResourceLocation hotBar = new ResourceLocation("guidesigner:textures/gui/hotbar.png");

    /**
     * The X size of the inventory window in pixels.
     */
    public int xSize = 176;
    /**
     * The Y size of the inventory window in pixels.
     */
    public int ySize = 166;
    public int selectedComponet = -1;
    ArrayList<Componet> componets = new ArrayList<Componet>();
    Componet movingComponet = null;

    GuiTextField nameFeild;

    GuiTextField textLabelBox;
    GuiTextField buttonLableBox;

    GuiTextField guiTitleBox;
    String newName;

    int scrollerPos = 0;

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

    @Override
    public void initGui() {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        buttonList.clear();
        buttonList.add(new GuiButton(0, -ySize / 2 + 150, l + ySize - 24, 20, 20, "-"));
        buttonList.add(new GuiButton(1, (this.width - ySize / 2) - 278, this.height - 22, 20, 20, "ABC"));
        buttonList.add(new GuiButton(2, (this.width - ySize / 2) - 278 + 22, this.height - 22, 20, 20, "B"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        RenderManager.instance.renderEngine.bindTexture(baseTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(-ySize / 2, l, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(this.width - ySize / 2 - 50, l, 0, 0, this.xSize, this.ySize);
        {//Hot bar
            RenderManager.instance.renderEngine.bindTexture(hotBar);
            this.drawTexturedModalRect((this.width - ySize / 2) - 280, this.height - 25, 0, 0, 256, 256);
            this.drawTexturedModalRect((this.width - ySize / 2) - 280,  -150, 0, 0, 256, 256);
        }

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
                if (textLabelBox == null) {
                    textLabelBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }

                if (textLabelBox.getText().equals("ERROR")) {
                    textLabelBox.setText(textLabel.getText());
                }
                textLabelBox.drawTextBox();
                textLabelBox.setTextColor(Color.white.getRGB());
            } else {
                textLabelBox = null;
            }
            if (componet instanceof CompButton) {
                CompButton compButton = (CompButton) componet;
                this.fontRendererObj.drawString("Text: ", this.width - ySize / 2 + 10 - 50, l + 35, Color.white.getRGB());
                if (buttonLableBox == null) {
                    buttonLableBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }

                if (buttonLableBox.getText().equals("ERROR")) {
                    buttonLableBox.setText(compButton.getText());
                }
                buttonLableBox.drawTextBox();
                buttonLableBox.setTextColor(Color.white.getRGB());
            } else {
                buttonLableBox = null;
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
            if (componet instanceof CompButton) {
                CompButton button = (CompButton) componet;
                GuiButton button1 = new GuiButton(99, k + button.getX(), l + button.getY(), button.getWidth(), button.getHeight(), button.getText());
                button1.drawButton(this.mc, k + button.getX(), l + button.getY());
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
        mouse();
        if(guiTitleBox == null){
            guiTitleBox = new GuiTextField(this.fontRendererObj,(this.width - ySize / 2) - 240,  2, 100 , 10);
            if(newName.length() == 0){
                guiTitleBox.setText("New Gui");
            } else {
                guiTitleBox.setText(newName);
            }

        }
        guiTitleBox.drawTextBox();
        drawString(this.fontRendererObj ,"Name:",(this.width - ySize / 2) - 275,  2, Color.white.getRGB());
        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button.id == 0 && !componets.isEmpty() && selectedComponet < componets.size() && selectedComponet != -1) {
            componets.remove(selectedComponet);
            selectedComponet = -1;
        }
        if (button.id == 1) {
            componets.add(new TextLabel(10, 10, "Label " + Integer.toString(componets.size() + 1), "Text goes here", 10));
        }
        if (button.id == 2) {
            componets.add(new CompButton(10, 10, "Button " + Integer.toString(componets.size() + 1), "Text goes here", 20));
        }
    }

    public boolean hasScrollBar() {
        return componets.size() > 9;
    }

    @Override
    public void mouseClicked(int x, int y, int par3) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        if (x > -ySize / 2 + 90 && x < -ySize / 2 + 90 + 70) {
            if (y > l + 25 && y < l + 135) {
                int num = -(l + 25 - y) / 10;
                setSelectedComponet(num);
            }
        }
        if (nameFeild != null) {
            nameFeild.mouseClicked(x, y, par3);
        }
        if (textLabelBox != null) {
            textLabelBox.mouseClicked(x, y, par3);
        }
        if (buttonLableBox != null) {
            buttonLableBox.mouseClicked(x, y, par3);
        }
        if(guiTitleBox != null){
            guiTitleBox.mouseClicked(x, y, par3);
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
                if (textLabelBox == null) {
                    textLabelBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }
                textLabelBox.setText("ERROR");
            }
            if (componet instanceof CompButton) {
                if (buttonLableBox == null) {
                    buttonLableBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }
                buttonLableBox.setText("ERROR");
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

        if (hasScrollBar()) {
            //TODO make this scroll :)
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if(guiTitleBox != null && guiTitleBox.textboxKeyTyped(par1, par2)){

        }
        if (nameFeild != null && nameFeild.textboxKeyTyped(par1, par2)) {
            if (nameFeild.getText().length() != 0) {
                componets.get(selectedComponet).setName(nameFeild.getText());
            }
        } else if (textLabelBox != null && textLabelBox.textboxKeyTyped(par1, par2)) {
            if (textLabelBox.getText().length() != 0) {
                Componet componet = componets.get(selectedComponet);
                if (componet instanceof TextLabel) {
                    ((TextLabel) componet).setText(textLabelBox.getText());
                    componet.setWidth(textLabelBox.getText().length() * 5);
                }
            }
        } else if (buttonLableBox != null && buttonLableBox.textboxKeyTyped(par1, par2)) {
            if (buttonLableBox.getText().length() != 0) {
                Componet componet = componets.get(selectedComponet);
                if (componet instanceof CompButton) {
                    ((CompButton) componet).setText(buttonLableBox.getText());
                    componet.setWidth(buttonLableBox.getText().length() * 6);
                }
            }
        } else {
            super.keyTyped(par1, par2);
        }
    }

    public void onGuiClosed() {
        selectedComponet = -1;
        nameFeild = null;
        textLabelBox = null;
        buttonLableBox = null;
        Saver saver = new Saver(this, guiTitleBox.getText());
        saver.save();
        super.onGuiClosed();
    }


}
