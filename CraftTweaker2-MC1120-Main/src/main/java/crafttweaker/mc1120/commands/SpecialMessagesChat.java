package crafttweaker.mc1120.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import static crafttweaker.mc1120.commands.ClipboardHelper.copyCommandBase;


/**
 * @author BloodWorkXGaming
 */
public class SpecialMessagesChat {

    public static final ITextComponent EMPTY_TEXTMESSAGE = new TextComponentString("");

    public static ITextComponent getClickableCommandText(String message, String command, boolean runDirectly) {

        Style style = new Style();
        ClickEvent click = new ClickEvent(runDirectly ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND, command);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to execute [\u00A76" + command + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getClickableBrowserLinkText(String message, String url) {

        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
        style.setClickEvent(click);
        style.setColor(TextFormatting.AQUA);
        style.setUnderlined(true);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to Open [\u00A76" + url + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getFileOpenText(String message, String filepath) {

        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_FILE, filepath);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to open [\u00A76" + filepath + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getNormalMessage(String message) {
        return new TextComponentString(message);
    }

    public static ITextComponent getLinkToCraftTweakerLog(String message, ICommandSender sender) {
        if (sender.getEntityWorld().isRemote) {
            return getNormalMessage(message + "\nSee \u00A7acrafttweaker.log \u00A7rin your minecraft dir");
        } else {
            return getFileOpenText(message + "\nSee \u00A7acrafttweaker.log \u00A7r[\u00A76Click here to open\u00A7r]", CTChatCommand.CRAFTTWEAKER_LOG_PATH);
        }
    }

    public static ITextComponent getCopyMessage(String message, String copyMessage) {
        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, copyCommandBase + copyMessage);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy [\u00A76" + copyMessage + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }
}
