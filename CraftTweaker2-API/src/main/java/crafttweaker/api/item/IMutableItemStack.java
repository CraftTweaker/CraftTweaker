package crafttweaker.api.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * A mutable ItemStack. <code>withTag</code>, <code>withAmount</code>, <code>damageItem</code> etc.
 * will modify and return the ItemStack itself.
 * Use it with caution.
 *
 * @author youyihj
 */
@ZenClass("crafttweaker.item.IMutableItemStack")
@ZenRegister
public interface IMutableItemStack extends IItemStack {

    @ZenMethod
    void shrink(int quality);

    @ZenMethod
    void grow(int quality);

    // TODO: Allow passing in Random?
    @ZenMethod
    boolean attemptDamageItem(int amount, @Optional IPlayer player);
    /**
     * Returns a new immutable stack with the same properties.
     */
    @ZenMethod
    IItemStack copy();
}
