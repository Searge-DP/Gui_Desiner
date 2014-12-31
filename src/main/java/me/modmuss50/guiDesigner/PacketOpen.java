package me.modmuss50.guiDesigner;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import me.modmuss50.guiDesigner.saving.Loader;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.nio.charset.Charset;

public class PacketOpen implements IMessage {

    String name;

    public PacketOpen() {
        this.name = "NULL";
    }

    public PacketOpen(String name) {
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            name = packetBuffer.readStringFromBuffer(32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            packetBuffer.writeStringToBuffer(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<PacketOpen, IMessage> {

        @Override
        public IMessage onMessage(PacketOpen message, MessageContext ctx) {
            Loader loader = new Loader(message.name);
            GuiDesigner guiDesigner = loader.load();
            if(guiDesigner == null){
                guiDesigner = new GuiDesigner();
                guiDesigner.newName = message.name;
            }
            FMLClientHandler.instance().showGuiScreen(guiDesigner);
            return null;
        }
    }

}
