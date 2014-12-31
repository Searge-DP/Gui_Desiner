package me.modmuss50.guiDesigner;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import me.modmuss50.guiDesigner.saving.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketOpen implements IMessage {

    String name;
    String playerName;

    public PacketOpen() {
        this.name = "NULL";
    }

    public PacketOpen(String name, String playerName) {
        this.name = name;
        this.playerName = playerName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            name = packetBuffer.readStringFromBuffer(999);
            playerName = packetBuffer.readStringFromBuffer(999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            packetBuffer.writeStringToBuffer(name);
            packetBuffer.writeStringToBuffer(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<PacketOpen, IMessage> {

        @Override
        public IMessage onMessage(PacketOpen message, MessageContext ctx) {
            if(ctx.side == Side.CLIENT){
               if(Minecraft.getMinecraft().thePlayer.getCommandSenderName().equals(message.playerName)){
                    Loader loader = new Loader(message.name);
                    GuiDesigner guiDesigner = loader.load();
                    if(guiDesigner == null){
                        guiDesigner = new GuiDesigner();
                    }
                    guiDesigner.newName = message.name;
                    FMLClientHandler.instance().showGuiScreen(guiDesigner);
                }

            }

            return null;
        }
    }

}
