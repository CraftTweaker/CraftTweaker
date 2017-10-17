package com.blamejared.ctgui.commands;

import com.blamejared.ctgui.api.GuiRegistry;
import com.blamejared.ctgui.api.events.CTGUIEvent;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Jared.
 */
public class CommandCTGUI extends CommandBase {
    
    @Override
    public String getName() {
        return "CTGUI";
    }
    
    @Override
    public List<String> getAliases() {
        return Arrays.asList(getName().toLowerCase());
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        return "CTGUI <id>";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length != 1) {
            return super.getTabCompletions(server, sender, args, targetPos);
        }
        return getListOfStringsMatchingLastWord(args, new ArrayList<>(GuiRegistry.getGuiMap().keySet()));
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0 || GuiRegistry.getID(args[0]) == Integer.MIN_VALUE) {
            sender.sendMessage(new TextComponentString("Invalid ID!"));
            return;
        }
        
        if(sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            MinecraftForge.EVENT_BUS.post(new CTGUIEvent(player, player.world, args[0]));
        }
    }
}
