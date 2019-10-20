package minetweaker.api.entity;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.value.*;

import java.util.*;


/**
 * @author Stan Hebben
 */
@ZenClass("minetweaker.entity.IEntityDefinition")
public interface IEntityDefinition {
    
    @ZenGetter("id")
    String getId();
    
    @ZenGetter("name")
    String getName();
    
    @ZenMethod
    void addDrop(IItemStack stack, @Optional int min, @Optional int max);
    
    @ZenMethod
    void addPlayerOnlyDrop(IItemStack stack, @Optional int min, @Optional int max);
    
    @ZenMethod
    void removeDrop(IItemStack stack);
    
    Map<IItemStack, IntRange> getDropsToAdd();
    
    Map<IItemStack, IntRange> getDropsToAddPlayerOnly();
    
    List<IItemStack> getDropsToRemove();
    
}
