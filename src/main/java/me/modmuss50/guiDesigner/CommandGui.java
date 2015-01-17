package me.modmuss50.guiDesigner;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandGui implements ICommand {
    @Override
    public String getName() {
        return "gui";
    }


    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "Gui";
    }


    @Override
    public boolean canCommandSenderUse(ICommandSender sender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public List getAliases() {
        ArrayList<String> list = new ArrayList<String>();
        return list;
    }

    @Override
    public void execute(ICommandSender sender, String[] strings) {
        String name;
        if (strings.length == 0) {
            name = "New gui";
        } else {
            name = strings[0];
        }
        Designer.network.sendToAll(new PacketOpen(name, sender.getName()));
        sender.addChatMessage(new ChatComponentText("Opening the gui Designer!"));
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
