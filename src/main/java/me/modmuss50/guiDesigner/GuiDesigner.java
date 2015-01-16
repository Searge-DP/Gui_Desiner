package me.modmuss50.guiDesigner;

import me.modmuss50.guiDesigner.componets.CompButton;
import me.modmuss50.guiDesigner.componets.CompImage;
import me.modmuss50.guiDesigner.componets.CompText;
import me.modmuss50.guiDesigner.componets.Component;
import me.modmuss50.guiDesigner.componets.mc.GuiTextField;
import me.modmuss50.guiDesigner.saving.Loader;
import me.modmuss50.guiDesigner.saving.Saver;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.Serializable;
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
    public ArrayList<Component> components = new ArrayList<Component>();
    Component movingComponent = null;

    GuiTextField nameFeild;
    GuiTextField xSizeImage;
    GuiTextField ySizeImage;

    GuiTextField textLabelBox;
    GuiTextField buttonLableBox;
    GuiTextField imageLocationBox;

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
        buttonList.add(new GuiButton(3, (this.width - ySize / 2) - 278 + 44, this.height - 22, 20, 20, "I"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        RenderManager.instance.renderEngine.bindTexture(baseTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(-ySize / 2, l, 0, 0, this.xSize, this.ySize);
        if (selectedComponet != -1)
            this.drawTexturedModalRect(this.width - ySize / 2 - 50, l, 0, 0, this.xSize, this.ySize);
        {//Hot bar
            RenderManager.instance.renderEngine.bindTexture(hotBar);
            this.drawTexturedModalRect((this.width - ySize / 2) - 280, this.height - 25, 0, 0, 256, 256);
            this.drawTexturedModalRect((this.width - ySize / 2) - 280, -150, 0, 0, 256, 256);
        }

        this.fontRendererObj.drawString("Components", -ySize / 2 + 100, l + 5, Color.gray.getRGB());
        if (selectedComponet != -1)
            this.fontRendererObj.drawString("Settings", this.width - ySize / 2 + 10 - 15, l + 5, Color.gray.getRGB());

        this.drawColourOnScreen(0, 0, 0, 255, -ySize / 2 + 90, l + 25, 75, 110, 0);
        if (selectedComponet != -1)
            this.drawColourOnScreen(0, 0, 0, 255, this.width - ySize / 2 - 45, l + 15, 125, 145, 0);

        if (!components.isEmpty() && selectedComponet < components.size() && selectedComponet != -1) {
            this.drawColourOnScreen(50, 50, 50, 255, -ySize / 2 + 95, l + 29 + ((selectedComponet - scrollerPos) * 10), 65, 10, 0);
            Component component = components.get(selectedComponet);
            this.fontRendererObj.drawString("Name: ", this.width - ySize / 2 + 10 - 50, l + 20, Color.white.getRGB());
            if (nameFeild == null) {
                nameFeild = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 20, 70, 10);
            }
            if (nameFeild.getText().equals("ERROR")) {
                nameFeild.setText(components.get(selectedComponet).getName());
            }
            nameFeild.drawTextBox();
            nameFeild.setTextColor(Color.white.getRGB());
            if (component instanceof CompText) {
                CompText textLabel = (CompText) component;
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
            if (component instanceof CompButton) {
                CompButton compButton = (CompButton) component;
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
            if (component instanceof CompImage) {
                CompImage compImage = (CompImage) component;
                this.fontRendererObj.drawString("Texture: ", this.width - ySize / 2 + 10 - 50, l + 35, Color.white.getRGB());
                if (imageLocationBox == null) {
                    imageLocationBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }

                if (imageLocationBox.getText().equals("ERROR")) {
                    imageLocationBox.setText(compImage.getImage());
                }
                imageLocationBox.drawTextBox();
                imageLocationBox.setTextColor(Color.white.getRGB());
            } else {
                imageLocationBox = null;
            }
        } else {
            nameFeild = null;
        }

        for (Component component : components) {
            if (component instanceof CompText) {
                CompText textLabel = (CompText) component;
                this.fontRendererObj.drawString(textLabel.getText(), k + textLabel.getX(), l + textLabel.getY(), Color.gray.getRGB());
            }
            if (component instanceof CompButton) {
                CompButton button = (CompButton) component;
                GuiButton button1 = new GuiButton(99, k + button.getX(), l + button.getY(), button.getWidth(), button.getHeight(), button.getText());
                button1.drawButton(this.mc, k + button.getX(), l + button.getY());
            }
            if (component instanceof CompImage) {
                CompImage image = (CompImage) component;
                RenderManager.instance.renderEngine.bindTexture(new ResourceLocation(image.getImage()));
                this.drawTexturedModalRect(k + image.getX(), l + image.getY(), 0, 0, image.getWidth(), image.getHeight());
            }
            //this draws the selection box when moving an item
            if (movingComponent == component) {
                this.drawColourOnScreen(50, 50, 50, 255, k + component.getX(), l + component.getY(), 1, component.getHeight(), 0);
                this.drawColourOnScreen(50, 50, 50, 255, k + component.getX(), l + component.getY(), component.getWidth(), 1, 0);
                this.drawColourOnScreen(50, 50, 50, 255, k + component.getX() + component.getWidth(), l + component.getY(), 1, component.getHeight(), 0);
                this.drawColourOnScreen(50, 50, 50, 255, k + component.getX(), l + component.getY() + component.getHeight(), component.getWidth() + 1, 1, 0);
            }
        }

        int renderLoc = 0;
        for (int j = 0; j < components.size(); j++) {
                Component component = components.get(j);
                if (hasScrollBar()) {
                    this.drawColourOnScreen(160, 160, 160, 255, k + -ySize / 2 + 35, l + 25, 5, 110, 0);
                    this.drawColourOnScreen(90, 90, 90, 255, k + -ySize / 2 + 35, l + 25 + scrollerPos, 5, 10, 0);
                    if (j >= scrollerPos && j <= scrollerPos + 9) {
                        this.fontRendererObj.drawString(component.getName(), -ySize / 2 + 95, l + 30 + (renderLoc * 10), Color.white.getRGB());
                        renderLoc++;
                    }
                } else {
                    this.fontRendererObj.drawString(component.getName(), -ySize / 2 + 95, l + 30 + (renderLoc * 10), Color.white.getRGB());
                    renderLoc++;
                }
        }

        mouse();
        if (guiTitleBox == null) {
            guiTitleBox = new GuiTextField(this.fontRendererObj, (this.width - ySize / 2) - 240, 2, 100, 10);
            if (newName.length() == 0) {
                guiTitleBox.setText("New Gui");
            } else {
                guiTitleBox.setText(newName);
            }

        }
        guiTitleBox.drawTextBox();
        drawString(this.fontRendererObj, "Name:", (this.width - ySize / 2) - 275, 2, Color.white.getRGB());
        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        if(hasScrollBar()){
            int scrolled = Mouse.getEventDWheel();
            if (scrolled != 0) {
                if (scrolled < 0) {
                    if(scrollerPos < components.size() && scrollerPos < components.size() - 10){
                        this.scrollerPos++;
                    }
                } else {
                    if(scrollerPos > 0){
                        this.scrollerPos--;
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button.id == 0 && !components.isEmpty() && selectedComponet < components.size() && selectedComponet != -1) {
            components.remove(selectedComponet);
            selectedComponet = -1;
        }
        if (button.id == 1) {
            components.add(new CompText(10, 10, "Label " + Integer.toString(components.size() + 1), "Text goes here", 10));
        }
        if (button.id == 2) {
            components.add(new CompButton(10, 10, "Button " + Integer.toString(components.size() + 1), "Text goes here", 20));
        }
        if (button.id == 3) {
            components.add(new CompImage(50, 50, "Image " + Integer.toString(components.size() + 1), "guidesigner:textures/gui/icon.png", 107, 113));
        }
    }

    public boolean hasScrollBar() {
        return components.size() > 9;
    }

    @Override
    public void mouseClicked(int x, int y, int par3) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        if (x > -ySize / 2 + 90 && x < -ySize / 2 + 90 + 70) {
            if (y > l + 25 && y < l + 135) {
                int num = -(l + 25 - y) / 10;
                setSelectedComponet(num + scrollerPos);
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
        if (guiTitleBox != null) {
            guiTitleBox.mouseClicked(x, y, par3);
        }
        if (imageLocationBox != null) {
            imageLocationBox.mouseClicked(x, y, par3);
        }
        super.mouseClicked(x, y, par3);
    }

    public void setSelectedComponet(int num) {
        if (components.isEmpty()) {
            return;
        }
        selectedComponet = num;
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        if (nameFeild == null) {
            nameFeild = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 20, 70, 10);
        }
        nameFeild.setText("ERROR");
        Component component;
        if (selectedComponet <= components.size()) {
            if (components.size() == 1) {
                component = components.get(0);
            } else {
                //I cant be arsed to fix this now :P
                try{
                    component = components.get(selectedComponet);
                } catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                    selectedComponet = -1;
                    return;
                }
            }
            if (component instanceof CompText) {
                if (textLabelBox == null) {
                    textLabelBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }
                textLabelBox.setText("ERROR");
            }
            if (component instanceof CompButton) {
                if (buttonLableBox == null) {
                    buttonLableBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }
                buttonLableBox.setText("ERROR");
            }
            if (component instanceof CompImage) {
                if (imageLocationBox == null) {
                    imageLocationBox = new GuiTextField(this.fontRendererObj, this.width - ySize / 2 + 40 - 50, l + 35, 70, 10);
                }
                imageLocationBox.setText("ERROR");
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
        if (movingComponent == null) {
            for (Component component : components) {
                boolean flag = x >= k + component.getX() && x < k + component.getX() + component.getWidth() && y >= l + component.getY() && y < l + component.getY() + component.getHeight();

                if (flag && Mouse.isButtonDown(0)) {
                    movingComponent = component;
                }
            }
        }
        if (movingComponent != null) {
            setSelectedComponet(components.indexOf(movingComponent));
            //if(x - k > 0 && x - k < xSize - movingComponet.getWidth()){
            movingComponent.setX(x - k - (movingComponent.getWidth() / 2));
            // }
            // if(y - l > 0 && y - l < ySize - movingComponet.getHeight()){
            movingComponent.setY(y - l - (movingComponent.getHeight() / 2));
            // }
        }
        if (!Mouse.isButtonDown(0) && movingComponent != null) {
            movingComponent = null;
        }

        if (hasScrollBar()) {
            //TODO make this scroll :)
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (guiTitleBox != null && guiTitleBox.textboxKeyTyped(par1, par2)) {

        }
        if (nameFeild != null && nameFeild.textboxKeyTyped(par1, par2)) {
            if (nameFeild.getText().length() != 0) {
                components.get(selectedComponet).setName(nameFeild.getText());
            }
        } else if (textLabelBox != null && textLabelBox.textboxKeyTyped(par1, par2)) {
            if (textLabelBox.getText().length() != 0) {
                Component component = components.get(selectedComponet);
                if (component instanceof CompText) {
                    ((CompText) component).setText(textLabelBox.getText());
                    component.setWidth(textLabelBox.getText().length() * 5);
                }
            }
        } else if (buttonLableBox != null && buttonLableBox.textboxKeyTyped(par1, par2)) {
            if (buttonLableBox.getText().length() != 0) {
                Component component = components.get(selectedComponet);
                if (component instanceof CompButton) {
                    ((CompButton) component).setText(buttonLableBox.getText());
                    component.setWidth(buttonLableBox.getText().length() * 6);
                }
            }
        } else if (imageLocationBox != null && imageLocationBox.textboxKeyTyped(par1, par2)) {
            if (imageLocationBox.getText().length() != 0) {
                Component component = components.get(selectedComponet);
                if (component instanceof CompImage) {
                    ((CompImage) component).setImage(buttonLableBox.getText());
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
        String name = guiTitleBox.getText();
        guiTitleBox = null;
        Saver saver = new Saver(this, name);
        saver.save();
        super.onGuiClosed();
    }


    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }


    public GuiDesigner(String name) {
        newName = name;
        Loader loader = new Loader(newName);
        loader.load(this);
    }
}
