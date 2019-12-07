package minetweaker.api.player;

import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.player.IPlayer")
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

    // not an exposed method. risks abuse
    void openBrowser(String url);

    // not an exposed method, so far. would it be useful?
    void copyToClipboard(String value);
}
