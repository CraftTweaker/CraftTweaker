package crafttweaker.mc1120.server;

import crafttweaker.api.chat.IChatMessage;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.attribute.IEntityAttributeInstance;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.util.IPosition3f;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IDimension;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.util.Position3f;
import crafttweaker.mc1120.world.MCBlockPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stan
 */
public class ServerPlayer implements IPlayer {

    public static final ServerPlayer INSTANCE = new ServerPlayer();

    private ServerPlayer() {

    }

    @Override
    public String getId() {
        return "server";
    }

    @Override
    public String getName() {
        return "Server";
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
        FMLServerHandler.instance().getServer().sendMessage((ITextComponent) message.getInternal());
    }

    @Override
    public void sendChat(String message) {
        FMLServerHandler.instance().getServer().sendMessage(new TextComponentString(message));
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
        return true;
    }

    @Override
    public boolean isAdventure() {
        return false;
    }

    @Override
    public void openBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(URI.create(url));
            } catch (IOException ex) {
                Logger.getLogger(ServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void copyToClipboard(String value) {
        if (Desktop.isDesktopSupported()) {
            StringSelection stringSelection = new StringSelection(value);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
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
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public Position3f getPosition() {
        return new Position3f((float) getX(), (float) getY(), (float) getZ());
    }

    @Override
    public void setPosition(IPosition3f position) {

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
    public int getAir() {
        return 0;
    }

    @Override
    public void setAir(int seconds) {

    }

    @Override
    public Object getInternal() {
        return null;
    }

    @Override
    public void teleport(IPosition3f pos) {
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void setScore(int amount) {

    }

    @Override
    public void addScore(int amount) {

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

    @Override
    public IEntityAttributeInstance getAttribute(String name) {
        return null;
    }

    @Override
    public IWorld getWorld() {
        return null;
    }

    @Override
    public IBlockPos getBlockPos() {
        return new MCBlockPos(new BlockPos(0, 0, 0));
    }
}
