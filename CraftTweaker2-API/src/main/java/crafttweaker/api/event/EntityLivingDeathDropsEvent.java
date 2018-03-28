package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenRegister
@ZenClass("crafttweaker.event.EntityLivingDeathDropsEvent")
public interface EntityLivingDeathDropsEvent extends ILivingEvent, IEventCancelable {
    
    @ZenGetter("damageSource")
    IDamageSource getDamageSource();
    
    @ZenMethod
    void addItem(IItemStack item);
    
    @ZenMethod
    void addItem(IEntityItem entityItem);
    
    @ZenGetter("lootingLevel")
    int getLootingLevel();
    
    @ZenGetter("isRecentlyHit")
    boolean getIsRecentlyHit();
    
    @ZenGetter("drops")
    List<IEntityItem> getDrops();
    
    @ZenSetter("drops")
    void setDrops(List<IEntityItem> drops);
    
}
