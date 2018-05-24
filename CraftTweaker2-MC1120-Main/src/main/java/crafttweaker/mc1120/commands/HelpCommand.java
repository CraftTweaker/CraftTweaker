package crafttweaker.mc1120.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.*;

import java.util.*;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class HelpCommand extends CraftTweakerCommand{
    private final List<SizedList<CraftTweakerCommand>> pages = new ArrayList<>();
    public static final int lineLimit = 19;
    public static final int windowWidth = 64;
    
    public HelpCommand() {
        super("help");
    }
    
    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        int page = 0;
        
        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                sender.sendMessage(SpecialMessagesChat.getNormalMessage("Page number provided was invalid!"));
            }
        }
        
        sendUsage(sender, page);
    }
    
    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct help", "/ct help", true), getNormalMessage(" \u00A73Prints out the this help page"));
    }
    
    public void sendUsage(ICommandSender sender, int pageNum) {
        calculatePages();
        SizedList<CraftTweakerCommand> page = pages.get(pageNum);
        sender.sendMessage(SpecialMessagesChat.getNormalMessage("Tex: " + page.getTextSize()));
        sender.sendMessage(SpecialMessagesChat.getNormalMessage( TextFormatting.DARK_BLUE + "-----------------------------"));
        
        // fill with empty lines
        for(int i = 0, textSize = lineLimit - page.getTextSize(); i < textSize; i++) {
            sender.sendMessage(SpecialMessagesChat.EMPTY_TEXTMESSAGE);
        }
        
        // print commands
        for(CraftTweakerCommand craftTweakerCommand : page.getList()) {
            for(ITextComponent s : craftTweakerCommand.getDescription()) {
                sender.sendMessage(s);
            }
            
            sender.sendMessage(SpecialMessagesChat.EMPTY_TEXTMESSAGE);
        }
        
        // back and forth command
        ITextComponent back = pageNum == 0
                ? SpecialMessagesChat.getClickableCommandText(TextFormatting.YELLOW +"##", "/ct help " + pageNum, true)
                : SpecialMessagesChat.getClickableCommandText(TextFormatting.GREEN +"<<", "/ct help " + (pageNum - 1), true);
        
        ITextComponent forth = pageNum == (pages.size() - 1)
                ? SpecialMessagesChat.getClickableCommandText(TextFormatting.YELLOW +"##", "/ct help " + pageNum, true)
                : SpecialMessagesChat.getClickableCommandText(TextFormatting.GREEN +">>", "/ct help " + (pageNum + 1), true);
        
        back.appendSibling(SpecialMessagesChat.getClickableCommandText(
                TextFormatting.DARK_BLUE + " ----  "
                        + TextFormatting.GOLD + (pageNum + 1) + "/" + pages.size()
                        + TextFormatting.DARK_BLUE + "  ----- ",
                "/ct help " + pageNum, true));
        
        back.appendSibling(forth);
        
        sender.sendMessage(back);
    }
    
    private void calculatePages() {
        pages.clear();
        SizedList<CraftTweakerCommand> currentList = new SizedList<>();
        pages.add(currentList);
        
        int currentCount = 1;
        for(CraftTweakerCommand entry : CTChatCommand.getCraftTweakerCommands().values()) {
    
            int currentEntry = 1;
            for(ITextComponent iTextComponent : entry.getDescription()) {
                int width = (iTextComponent.getUnformattedText().length() / windowWidth) + 1;
                currentEntry += width;
                System.out.println( "["+ pages.size() + "]" + iTextComponent.getUnformattedText() + " : " + width);
            }
            
            currentCount += currentEntry;
            
            // creates a new list when the old one is beyond the limit
            if (currentCount >= lineLimit) {
                currentList.setTextSize(currentCount - currentEntry);
                
                currentList = new SizedList<>();
                pages.add(currentList);
                currentCount = 1 + currentEntry;
            }
            
            currentList.add(entry);
        }
        
        currentList.setTextSize(currentCount);
    }
    
    
    private static class SizedList<T> {
        private int textSize =  0;
        private List<T> list = new ArrayList<>();
    
        public int getTextSize() {
            return textSize;
        }
    
        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }
    
        public List<T> getList() {
            return list;
        }
    
        public boolean add(T t) {
            return list.add(t);
        }
    }
}
