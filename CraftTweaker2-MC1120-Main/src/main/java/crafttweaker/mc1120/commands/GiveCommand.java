package crafttweaker.mc1120.commands;

import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.data.StringIDataParser;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class GiveCommand extends CraftTweakerCommand {
    
    GiveCommand() {
        super("give");
    }
    
    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct give", "/ct give", true), getNormalMessage(" \u00A73Gives an item using the BH"));
    }
    
    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        //Get the whole string, with whitespaces
        final String s = String.join(" ", args);
        
        //Quick check, doesn't cover the Tag part though.
        if(!s.matches("<\\w+:\\w+(:\\d+)?>(.withTag\\(\\{.*}\\))?")) {
            sender.sendMessage(getNormalMessage("Invalid string " + s));
            return;
        }
        
        if(!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(getNormalMessage("Only players can execute this command!"));
            return;
        }
        
        //Get item
        final String[] exParts = s.split(">", 2)[0].substring(1).split(":");
        final String call = exParts[0] + ":" + exParts[1];
        final int meta = exParts.length > 2 ? Integer.parseInt(exParts[2]) : 0;
        IItemStack item = BracketHandlerItem.getItem(call, meta);
        if(item == null) {
            sender.sendMessage(getNormalMessage("Could not find item: " + call));
            return;
        }
        
        //Should we apply a tag?
        if(s.contains("withTag")) {
            //Remove the withTag and the round brackets so that we get only the tag.
            final String expression = s.split("withTag\\(")[1];
            item = item.withTag(StringIDataParser.parse(expression.substring(0, expression.length() - 1)), false);
        }
        
        new MCPlayer((EntityPlayer) sender).give(item);
    }
}
