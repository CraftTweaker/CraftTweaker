package crafttweaker.mc1120.player;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.chat.IChatMessage;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.util.IPosition3f;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.entity.MCEntityLivingBase;
import crafttweaker.mc1120.network.MessageCopyClipboard;
import crafttweaker.mc1120.network.MessageOpenBrowser;
import crafttweaker.mc1120.util.Position3f;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Stan
 */
public class MCPlayer extends MCEntityLivingBase implements IPlayer {
    private final EntityPlayer player;

    public MCPlayer(EntityPlayer player) {
        super(player);
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
        if (!(internal instanceof ITextComponent)) {
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
        if (player instanceof EntityPlayerMP) {
            CraftTweaker.NETWORK.sendTo(new MessageOpenBrowser(url), (EntityPlayerMP) player);
        }
    }

    @Override
    public void copyToClipboard(String value) {
        if (player instanceof EntityPlayerMP) {
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
        ItemStack itemstack = CraftTweakerMC.getItemStack(stack).copy();
        boolean flag = player.inventory.addItemStackToInventory(itemstack);

        if (flag) {
            player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.inventoryContainer.detectAndSendChanges();
        }

        if (flag && itemstack.isEmpty()) {
            itemstack.setCount(1);
            EntityItem entityitem1 = player.dropItem(itemstack, false);

            if (entityitem1 != null) {
                entityitem1.makeFakeItem();
            }
        } else {
            EntityItem entityitem = player.dropItem(itemstack, false);

            if (entityitem != null) {
                entityitem.setNoPickupDelay();
                entityitem.setOwner(player.getName());
            }
        }
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

    @Override
    public Position3f getPosition() {
        return new Position3f((float) getX(), (float) getY(), (float) getZ());
    }

    @Override
    public void teleport(IPosition3f pos) {
        player.setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public int getScore() {
        return player.getScore();
    }

    @Override
    public void addScore(int amount) {
        player.addScore(amount);
    }

    @Override
    public void setScore(int amount) {
        player.setScore(amount);
    }
}
