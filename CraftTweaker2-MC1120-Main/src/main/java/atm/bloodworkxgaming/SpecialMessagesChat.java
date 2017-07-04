package atm.bloodworkxgaming;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;


/**
 * Created by jonas on 04.07.2017.
 */
public class SpecialMessagesChat {

    public static final ITextComponent EMPTY_TEXTMESSAGE = new TextComponentString("");

    public static ITextComponent getClickableCommandText(String message, String command, boolean runDirectly){

        Style style = new Style();
        ClickEvent click = new ClickEvent(runDirectly ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND, command);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to execute [§6" + command + "§r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getClickableBrowserLinkText(String message, String url){

        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
        style.setClickEvent(click);
        style.setColor(TextFormatting.AQUA);
        style.setUnderlined(true);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to Open [§6" + url + "§r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getFileOpenText(String message, String filepath){

        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_FILE, filepath);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to open [§6" + filepath + "§r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getNormalMessage(String message){
        return new TextComponentString(message);
    }

    public static ITextComponent getLinkToCraftTweakerLog(String message, ICommandSender sender){
        if (sender.getEntityWorld().isRemote){
            return getNormalMessage(message + "\nSee §acrafttweaker.log §rin your minecraft dir");
        }else {
            return getFileOpenText(message + "\nSee §acrafttweaker.log §r[§6Click here to open§r]", CTChatCommand.CRAFTTWEAKER_LOG_PATH);
        }
    }
}
