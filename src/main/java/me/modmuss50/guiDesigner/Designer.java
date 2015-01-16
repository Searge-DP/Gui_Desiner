package me.modmuss50.guiDesigner;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


@Mod(modid = "guidesigner", name = "guiDesigner", version = "@MODVERSION@")
public class Designer {

    public static SimpleNetworkWrapper network;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
        network.registerMessage(PacketOpen.Handler.class, PacketOpen.class, 0, Side.CLIENT);
    }


    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandGui());
        event.registerServerCommand(new CommandOpenTestGUi());
    }
}
