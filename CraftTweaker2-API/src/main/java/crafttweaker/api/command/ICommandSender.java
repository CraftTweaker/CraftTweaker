package crafttweaker.api.command;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.server.IServer;
import crafttweaker.api.text.ITextComponent;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.command.ICommandSender")
@ZenRegister
public interface ICommandSender {
    
    @ZenGetter("displayName")
    String getDisplayName();
    
    @ZenGetter("position")
    IBlockPos getPosition();
    
    @ZenGetter("world")
    IWorld getWorld();
    
    @ZenGetter("server")
    IServer getServer();
    
    @ZenMethod
    void sendMessage(String text);
    
    Object getInternal();
}
