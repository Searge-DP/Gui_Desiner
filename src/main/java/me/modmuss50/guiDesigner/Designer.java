package me.modmuss50.guiDesigner;


import me.modmuss50.mods.core.client.BaseModGui;
import me.modmuss50.mods.core.mod.ModRegistry;
import me.modmuss50.mods.lib.mod.ISourceMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "guidesigner", name = "guiDesigner", version = "@MODVERSION@")
public class Designer implements ISourceMod {

    public static SimpleNetworkWrapper network;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.registerMod(this);
        network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
        network.registerMessage(PacketOpen.Handler.class, PacketOpen.class, 0, Side.CLIENT);
    }


    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandGui());
        event.registerServerCommand(new CommandOpenTestGUi());
    }

    @Override
    public BaseModGui settingsScreen() {
        return null;
    }

    @Override
    public String modId() {
        return "guidesigner";
    }

    @Override
    public String modName() {
        return "guiDesigner";
    }

    @Override
    public String modVersion() {
        return "@MODVERSION@";
    }

    @Override
    public String recomenedMinecraftVeriosion() {
        return "1.8";
    }
}
