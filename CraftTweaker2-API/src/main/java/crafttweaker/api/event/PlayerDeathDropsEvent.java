package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerDeathDropsEvent")
@ZenRegister
public interface PlayerDeathDropsEvent extends IPlayerEvent {
    
    @ZenGetter("items")
    List<IEntityItem> getItems();
    
    @ZenSetter("items")
    void setItems(List<IEntityItem> items);
    
    @ZenMethod
    void addItem(IItemStack item);
    
    @ZenMethod
    void addItem(IEntityItem entityItem);
    
    @ZenGetter("damageSource")
    IDamageSource getDamageSource();
}
