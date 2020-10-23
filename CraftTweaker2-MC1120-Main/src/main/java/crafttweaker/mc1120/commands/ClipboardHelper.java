package crafttweaker.mc1120.commands;

import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.network.MessageCopyClipboard;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.TextComponentString;

/**
 * @author BloodWorkXGaming
 */
public class ClipboardHelper {
    
    static final String copyCommandBase = "/ct copy ";
    
    /**
     * Sends the Player a Message where he can click on and copy the it
     *
     * @param player       Player to send the message to
     * @param holeMessage  String that should be shown in chat
     * @param copyMessage  String that is being copied when the player clicks on it
     */
    public static void sendMessageWithCopy(EntityPlayer player, String holeMessage, String copyMessage) {
        player.sendMessage(SpecialMessagesChat.getCopyMessage(holeMessage, copyMessage));
    }
    
    /**
     * Called by the copy command
     * Copy command is needed to be able to copy something on clicking on a ChatMessage
     *
     * @param sender: sender that copies
     * @param args:   strings to copy
     */
    static void copyCommandRun(ICommandSender sender, String[] args) {
        
        StringBuilder message = new StringBuilder();
        
        for(int i = 0; i < args.length; i++) {
            message.append(args[i]);
            if(i != args.length - 1)
                message.append(" ");
        }
        
        if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
            copyStringPlayer((EntityPlayer) sender.getCommandSenderEntity(), message.toString());
            sender.sendMessage(new TextComponentString("Copied [\u00A76" + message.toString() + "\u00A7r] to the clipboard"));
        } else {
            sender.sendMessage(new TextComponentString("This command can only be executed as a Player (InGame)"));
        }
    }
    
    
    /**
     * Makes the player copy the sent String
     *
     * @param player: Player which should copy the string
     * @param s:      String to copy
     */
    static void copyStringPlayer(EntityPlayer player, String s) {
        if(player instanceof EntityPlayerMP) {
            CraftTweaker.NETWORK.sendTo(new MessageCopyClipboard(s), (EntityPlayerMP) player);
        }
    }
}