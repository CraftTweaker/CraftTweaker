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
    void addDrop(IItemStack stack, @Optional int min, @Optional int max);
    
    @ZenMethod
    void addPlayerOnlyDrop(IItemStack stack, @Optional int min, @Optional int max);
    
    @ZenMethod
    void removeDrop(IItemStack stack);
    
    Map<IItemStack, IntegerRange> getDropsToAdd();
    
    Map<IItemStack, IntegerRange> getDropsToAddPlayerOnly();
    
    List<IItemStack> getDropsToRemove();
    
}
