package minetweaker.mc1102.player;

import minetweaker.MineTweakerAPI;
import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Jared
 */
public class CommandBlockPlayer implements IPlayer{
    private final ICommandSender sender;

    public CommandBlockPlayer(ICommandSender sender){
        this.sender = sender;
    }

    public ICommandSender getInternal(){
        return sender;
    }

    @Override
    public String getId(){
        return null; // TODO: we should be having this for MC 1.7.10, right?
    }

    @Override
    public String getName(){
        return sender.getName();
    }

    @Override
    public IData getData(){
        return null;
    }

    @Override
    public void update(IData data){

    }

    @Override
    public void sendChat(IChatMessage message){
        Object internal = message.getInternal();
        if(!(internal instanceof ITextComponent)){
            MineTweakerAPI.logError("not a valid chat message");
            return;
        }
        sender.addChatMessage((ITextComponent) internal);
    }

    @Override
    public void sendChat(String message){
        sender.addChatMessage(new TextComponentString(message));
    }

    @Override
    public int getHotbarSize(){
        return 0;
    }

    @Override
    public IItemStack getHotbarStack(int i){
        return null;
    }

    @Override
    public int getInventorySize(){
        return 0;
    }

    @Override
    public IItemStack getInventoryStack(int i){
        return null;
    }

    @Override
    public IItemStack getCurrentItem(){
        return null;
    }

    @Override
    public boolean isCreative(){
        return false;
    }

    @Override
    public boolean isAdventure(){
        return false;
    }

    @Override
    public void openBrowser(String url){
    }

    @Override
    public void copyToClipboard(String value){
    }

    @Override
    public boolean equals(Object other){
        if(other.getClass() != this.getClass())
            return false;

        return ((CommandBlockPlayer) other).sender.equals(sender);
    }

    @Override
    public int hashCode(){
        int hash = 5;
        hash = 23 * hash + (this.sender != null ? this.sender.hashCode() : 0);
        return hash;
    }

    @Override
    public void give(IItemStack stack){
    }
}
