package crafttweaker.api.player;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.chat.IChatMessage;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.util.Position3f;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.player.IPlayer")
@ZenRegister
public interface IPlayer {
    
    @ZenGetter("id")
    String getId();
    
    @ZenGetter("name")
    String getName();
    
    @ZenGetter("data")
    IData getData();
    
    @ZenGetter("xp")
    int getXP();
    
    @ZenSetter("xp")
    void setXP(int xp);
    
    @ZenMethod
    void removeXP(int xp);
    
    @ZenMethod
    void update(IData data);
    
    @ZenMethod
    void sendChat(IChatMessage message);
    
    @ZenMethod
    void sendChat(String message);
    
    @ZenGetter("hotbarSize")
    int getHotbarSize();
    
    @ZenMethod
    IItemStack getHotbarStack(int i);
    
    @ZenGetter("inventorySize")
    int getInventorySize();
    
    @ZenMethod
    IItemStack getInventoryStack(int i);
    
    @ZenGetter("currentItem")
    IItemStack getCurrentItem();
    
    @ZenGetter("creative")
    boolean isCreative();
    
    @ZenGetter("adventure")
    boolean isAdventure();
    
    @ZenMethod
    void give(IItemStack stack);
    
    /**
     * Retrieves the x position of this entity.
     *
     * @return entity x position
     */
    @ZenGetter("x")
    double getX();
    
    /**
     * Retrieves the y position of this entity.
     *
     * @return entity y position
     */
    @ZenGetter("y")
    double getY();
    
    /**
     * Retrieves the z position of this entity.
     *
     * @return entity z position
     */
    @ZenGetter("z")
    double getZ();
    
    
    // not an exposed method. risks abuse
    void openBrowser(String url);
    
    // not an exposed method, so far. would it be useful?
    void copyToClipboard(String value);
    
    @ZenGetter("position")
    Position3f getPosition();
    
    @ZenSetter("position")
    @ZenMethod
    void teleport(Position3f pos);
}
