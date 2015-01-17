package me.modmuss50.guiDesigner;


import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            packetBuffer.writeString(name);
            packetBuffer.writeString(playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<PacketOpen, IMessage> {

        @Override
        public IMessage onMessage(PacketOpen message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
                if (Minecraft.getMinecraft().thePlayer.getName().equals(message.playerName)) {
                    FMLClientHandler.instance().showGuiScreen(new GuiDesigner(message.name));
                }
            }
            return null;
        }
    }

}
