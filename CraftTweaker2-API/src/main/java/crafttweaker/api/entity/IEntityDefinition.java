package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.util.IntegerRange;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.*;


/**
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.entity.IEntityDefinition")
@ZenRegister
public interface IEntityDefinition {
    
    @ZenGetter("id")
    String getId();
    
    @ZenGetter("name")
    String getName();
    
    @ZenMethod
    void addDrop(IItemStack stack, @Optional int min, @Optional int max, @Optional float chance);
    
    @ZenMethod
    void addPlayerOnlyDrop(IItemStack stack, @Optional int min, @Optional int max, @Optional float chance);
    
    @ZenMethod
    void removeDrop(IItemStack stack);

    @ZenMethod
    void clearDrops();

    List<IEntityDrop> getDrops();

    @Deprecated
    Map<IItemStack, IntegerRange> getDropsToAdd();

    @Deprecated
    Map<IItemStack, IntegerRange> getDropsToAddPlayerOnly();
    
    List<IItemStack> getDropsToRemove();

    boolean shouldClearDrops();
}
