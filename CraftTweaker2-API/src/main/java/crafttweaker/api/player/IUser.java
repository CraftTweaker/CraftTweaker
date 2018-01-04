package crafttweaker.api.player;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.command.ICommandSender;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.player.IUser")
@ZenRegister
public interface IUser extends ICommandSender{

}
