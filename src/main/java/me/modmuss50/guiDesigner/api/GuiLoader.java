package me.modmuss50.guiDesigner.api;

import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * You can use this file in your mod to load the guis. You can rename it, hack it.
 *
 *
 * This file is messy! I know it needs work but i am short of free time to work on it
 *
 * This file is massive because it needs to be standalone and not need the main mod or mmc
 */
public class GuiLoader extends GuiScreen {

	private static final ResourceLocation baseTexture = new ResourceLocation("guidesigner:textures/gui/base.png");

	/**
	 * The X size of the inventory window in pixels.
	 */
	public int xSize = 176;
	/**
	 * The Y size of the inventory window in pixels.
	 */
	public int ySize = 166;
	public ArrayList<Component> components = new ArrayList<Component>();

	public ArrayList<CustButton> buttons  = new ArrayList<CustButton>();


	public GuiLoader(String name) {
		Importer importer = new Importer(this, name);
		importer.load();
	}

	@Override
	public void initGui() {
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		buttonList.clear();
		for(CustButton button : buttons){
			buttonList.add(button.getButton());
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		RenderManager.instance.renderEngine.bindTexture(baseTexture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		for (Component component : components) {
			if (component instanceof CompText) {
				CompText textLabel = (CompText) component;
				this.fontRendererObj.drawString(textLabel.getText(), k + textLabel.getX(), l + textLabel.getY(), Color.gray.getRGB());
			}
			if (component instanceof CompImage) {
				CompImage image = (CompImage) component;
				RenderManager.instance.renderEngine.bindTexture(new ResourceLocation(image.getImage()));
				this.drawTexturedModalRect(k + image.getX(), l + image.getY(), 0, 0, image.getWidth(), image.getHeight());
			}
		}
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();


	}

	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		for(CustButton custButton : buttons){
			if(custButton.getButton() == button){
				buttonPressed(custButton.component.getName());
			}
		}
	}

	//Use this to read button presses on the gui
	public void buttonPressed(String name){

	}

	@Override
	public void mouseClicked(int x, int y, int par3) {
		super.mouseClicked(x, y, par3);
	}


	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
	}

	public void onGuiClosed() {
		super.onGuiClosed();
	}


	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

}


class Importer {

	GuiLoader screen;
	File file;
	File folder;

	public Importer(GuiLoader screen, String name) {
		this.screen = screen;
		File mcDir = (File) FMLInjectionData.data()[6];
		folder = new File(mcDir, "guis");
		file = new File(folder, name + ".gui");
	}

	public void load() {
		if (file.getName().equals("New gui")) {
			return;
		}
		if (!folder.exists()) {
			return;
		}
		if (!file.exists()) {
			return;
		}

		int k = (screen.width - screen.xSize) / 2;
		int l = (screen.height - screen.ySize) / 2;

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] strings = line.split(":");
				if (strings[0].length() != 0) {
					String newLine = line.replace(strings[0] + ":", "");
					Component component = CompRegistry.getComponetFromClassName(strings[0], newLine);
					component.loadFromString(newLine);
					if(component instanceof CompButton){
						screen.buttons.add(new CustButton(new GuiButton(screen.buttons.size(), component.getX(),component.getY(), component.getWidth(), component.getHeight(), ((CompButton) component).getText()), screen.buttons.size(), component));
					}
					screen.components.add(component);
				}
			}
			bufferedReader.close();
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class CompRegistry {

	public static Component getComponetFromClassName(String name, String line) {
		try {
			Component component = (Component) Class.forName(name).newInstance();
			component.loadFromString(line);
			return component;

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (name.equals("me.modmuss50.guiDesigner.componets.CompButton")) {
			return new CompButton(line);
		} else if (name.equals("me.modmuss50.guiDesigner.componets.CompImage")) {
			return new CompImage(line);
		} else if (name.equals("me.modmuss50.guiDesigner.componets.CompTextbox")) {
			return new CompTextbox(line);
		} else if (name.equals("me.modmuss50.guiDesigner.componets.CompText")) {
			return new CompText(line);
		}
		return null;
	}
}

class Component implements Serializable {

	int x;

	int y;

	int height;
	int width;

	String name;

	public Component(int x, int y, String name, int height, int width) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.height = height;
		this.width = width;
	}

	public Component(String line) {
		this.loadFromString(line);
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

	public String getSaveLine() {
		return "";
	}

	public void loadFromString(String string) {
	}
}

class CompButton extends Component {
	String text;

	public CompButton(int x, int y, String name, String text, int height) {
		super(x, y, name, height, text.length() * 6);
		this.text = text;
	}

	public CompButton(String line) {
		super(line);
		this.loadFromString(line);
	}

	public CompButton() {
		super(0, 0, "", 0, 0);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getSaveLine() {
		return x + ":" + y + ":" + name + ":" + text + ":" + height + ":" + width;
	}

	@Override
	public void loadFromString(String string) {
		String[] parts = string.split(":");
		setX(Integer.parseInt(parts[0]));
		setY(Integer.parseInt(parts[1]));
		setName(parts[2]);
		setText(parts[3]);
		setHeight(Integer.parseInt(parts[4]));
		setWidth(Integer.parseInt(parts[5]));
	}
}

class CompImage extends Component {
	String image;

	public CompImage(int x, int y, String name, String image, int height, int length) {
		super(x, y, name, height, length);
		this.image = image;
	}

	public CompImage(String line) {
		super(line);
		this.loadFromString(line);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String getSaveLine() {
		return x + "&" + y + "&" + name + "&" + image + "&" + height + "&" + width;
	}

	@Override
	public void loadFromString(String string) {
		System.out.println(string);
		String[] parts = string.split("&");
		setX(Integer.parseInt(parts[0]));
		setY(Integer.parseInt(parts[1]));
		setName(parts[2]);
		setImage(parts[3]);
		setHeight(Integer.parseInt(parts[4]));
		setWidth(Integer.parseInt(parts[5]));
	}
}

class CompText extends Component {
	String text;

	public CompText(int x, int y, String name, String text, int height) {
		super(x, y, name, height, text.length() * 5);
		this.text = text;
	}

	public CompText(String line) {
		super(line);
		this.loadFromString(line);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getSaveLine() {
		return x + ":" + y + ":" + name + ":" + text + ":" + height + ":" + width;
	}

	@Override
	public void loadFromString(String string) {
		String[] parts = string.split(":");
		setX(Integer.parseInt(parts[0]));
		setY(Integer.parseInt(parts[1]));
		setName(parts[2]);
		setText(parts[3]);
		setHeight(Integer.parseInt(parts[4]));
		setWidth(Integer.parseInt(parts[5]));
	}

}

class CompTextbox extends Component {
	String text;

	public CompTextbox(int x, int y, String name, String text, int height, int lenght) {
		super(x, y, name, height, lenght);
		this.text = text;
	}

	public CompTextbox(String line) {
		super(line);
		this.loadFromString(line);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getSaveLine() {
		return x + ":" + y + ":" + name + ":" + text + ":" + height + ":" + width;
	}

	@Override
	public void loadFromString(String string) {
		String[] parts = string.split(":");
		setX(Integer.parseInt(parts[0]));
		setY(Integer.parseInt(parts[1]));
		setName(parts[2]);
		setText(parts[3]);
		setHeight(Integer.parseInt(parts[4]));
		setWidth(Integer.parseInt(parts[5]));
	}
}

class CustButton{

	GuiButton button;
	int id;
	Component component;

	public CustButton(GuiButton button, int id, Component component) {
		this.button = button;
		this.id = id;
		this.component = component;
	}

	public GuiButton getButton() {
		return button;
	}

	public void setButton(GuiButton button) {
		this.button = button;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
}