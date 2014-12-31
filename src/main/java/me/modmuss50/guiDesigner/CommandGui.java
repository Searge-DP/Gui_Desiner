package me.modmuss50.guiDesigner;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandGui implements ICommand {
    @Override
    public String getCommandName() {
        return "gui";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "Gui";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] strings) {
        Designer.network.sendToAll(new PacketOpen());
        sender.addChatMessage(new ChatComponentText("Opening the gui Designer!"));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
