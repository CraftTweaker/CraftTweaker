package crafttweaker.mc1120.player;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.chat.IChatMessage;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;

/**
 * @author Stan
 */
public class MCPlayer implements IPlayer {

    private final EntityPlayer player;

    public MCPlayer(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getInternal() {
        return player;
    }

    @Override
    public String getId() {
        return player.getUniqueID().toString();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public IData getData() {
        return NBTConverter.from(player.getEntityData(), true);
    }

    @Override
    public int getXP() {
        return player.experienceLevel;
    }

    @Override
    public void setXP(int xp) {
        player.addExperienceLevel(-player.experienceLevel);
        player.addExperienceLevel(xp);
    }

    @Override
    public void removeXP(int xp) {
        player.addExperience(-xp);
    }

    @Override
    public void update(IData data) {
        NBTConverter.updateMap(player.getEntityData(), data);
    }

    @Override
    public void sendChat(IChatMessage message) {
        Object internal = message.getInternal();
        if(!(internal instanceof ITextComponent)) {
            CraftTweakerAPI.logError("not a valid chat message");
            return;
        }
        player.sendMessage((ITextComponent) internal);
    }

    @Override
    public void sendChat(String message) {
        player.sendMessage(new TextComponentString(message));
    }

    @Override
    public int getHotbarSize() {
        return 9;
    }

    @Override
    public IItemStack getHotbarStack(int i) {
        return i < 0 || i >= 9 ? null : CraftTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
    }

    @Override
    public int getInventorySize() {
        return player.inventory.getSizeInventory();
    }

    @Override
    public IItemStack getInventoryStack(int i) {
        return CraftTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
    }

    @Override
    public IItemStack getCurrentItem() {
        return CraftTweakerMC.getIItemStack(player.inventory.getCurrentItem());
    }

    @Override
    public boolean isCreative() {
        return player.capabilities.isCreativeMode;
    }

    @Override
    public boolean isAdventure() {
        return !player.capabilities.allowEdit;
    }

    @Override
    public void openBrowser(String url) {
        if(player instanceof EntityPlayerMP) {
            CraftTweaker.NETWORK.sendTo(new MessageOpenBrowser(url), (EntityPlayerMP) player);
        }
    }

    @Override
    public void copyToClipboard(String value) {
        if(player instanceof EntityPlayerMP) {
            CraftTweaker.NETWORK.sendTo(new MessageCopyClipboard(value), (EntityPlayerMP) player);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other.getClass() == this.getClass() && ((MCPlayer) other).player == player;

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.player != null ? this.player.hashCode() : 0);
        return hash;
    }

    @Override
    public void give(IItemStack stack) {
        player.inventory.addItemStackToInventory(CraftTweakerMC.getItemStack(stack).copy());
    }
    
    @Override
    public double getX() {
        return player.posX;
    }
    
    @Override
    public double getY() {
        return player.posY;
    }
    
    @Override
    public double getZ() {
        return player.posZ;
    }
}
