package crafttweaker.mc1120.player;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.chat.IChatMessage;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IDimension;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.List;
import java.util.Objects;

/**
 * @author Jared
 */
public class CommandBlockPlayer implements IPlayer {

    private final ICommandSender sender;

    public CommandBlockPlayer(ICommandSender sender) {
        this.sender = sender;
    }

    public ICommandSender getInternal() {
        return sender;
    }

    @Override
    public String getId() {
        return sender.getCommandSenderEntity().getUniqueID().toString();
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    @Override
    public IData getData() {
        return null;
    }

    @Override
    public int getXP() {
        return 0;
    }

    @Override
    public void setXP(int xp) {

    }

    @Override
    public void removeXP(int xp) {

    }

    @Override
    public void update(IData data) {

    }

    @Override
    public void sendChat(IChatMessage message) {
        Object internal = message.getInternal();
        if (!(internal instanceof ITextComponent)) {
            CraftTweakerAPI.logError("not a valid chat message");
            return;
        }
        sender.sendMessage((ITextComponent) internal);
    }

    @Override
    public void sendChat(String message) {
        sender.sendMessage(new TextComponentString(message));
    }

    @Override
    public int getHotbarSize() {
        return 0;
    }

    @Override
    public IItemStack getHotbarStack(int i) {
        return null;
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    @Override
    public IItemStack getInventoryStack(int i) {
        return null;
    }

    @Override
    public IItemStack getCurrentItem() {
        return null;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public boolean isAdventure() {
        return false;
    }

    @Override
    public void openBrowser(String url) {
    }

    @Override
    public void copyToClipboard(String value) {
    }

    @Override
    public boolean equals(Object other) {
        return (other != null) && other.getClass() == this.getClass() && Objects.equals(((CommandBlockPlayer) other).sender, sender);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.sender != null ? this.sender.hashCode() : 0);
        return hash;
    }

    @Override
    public void give(IItemStack stack) {
    }

    @Override
    public IDimension getDimension() {
        return null;
    }

    @Override
    public double getX() {
        return sender.getPosition().getX();
    }

    @Override
    public double getY() {
        return sender.getPosition().getY();
    }

    @Override
    public double getZ() {
        return sender.getPosition().getZ();
    }

    @Override
    public Position3f getPosition() {
        return new Position3f((float) getX(), (float) getY(), (float) getZ());
    }

    @Override
    public void setPosition(Position3f position) {

    }

    @Override
    public void setDead() {

    }

    @Override
    public void setFire(int seconds) {

    }

    @Override
    public void extinguish() {

    }

    @Override
    public boolean isWet() {
        return false;
    }

    @Override
    public List<IEntity> getPassengers() {
        return null;
    }

    @Override
    public double getDistanceSqToEntity(IEntity entity) {
        return 0;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public IEntity getRidingEntity() {
        return null;
    }

    @Override
    public IItemStack getPickedResult() {
        return null;
    }

    @Override
    public String getCustomName() {
        return null;
    }

    @Override
    public void setCustomName(String name) {

    }

    @Override
    public boolean isImmuneToFire() {
        return false;
    }

    @Override
    public void setAir(int seconds) {

    }

    @Override
    public int getAir() {
        return 0;
    }

    @Override
    public void teleport(Position3f pos) {
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void addScore(int amount) {

    }

    @Override
    public void setScore(int amount) {

    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    public boolean isPotionActive(IPotion potion) {
        return false;
    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public void setHealth(float amount) {

    }

    @Override
    public boolean isChild() {
        return false;
    }

    @Override
    public void clearActivePotions() {

    }

    @Override
    public boolean isUndead() {
        return false;
    }

    @Override
    public void heal(int amount) {

    }

    @Override
    public boolean attackEntityFrom(IDamageSource source, float amount) {
        return false;
    }

    @Override
    public float getMaxHealth() {
        return 0;
    }

    @Override
    public IItemStack getHeldItemMainHand() {
        return null;
    }

    @Override
    public IItemStack getHeldItemOffHand() {
        return null;
    }
}
