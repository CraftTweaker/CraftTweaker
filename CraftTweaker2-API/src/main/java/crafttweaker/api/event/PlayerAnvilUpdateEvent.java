package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.PlayerAnvilUpdateEvent")
@ZenRegister
public interface PlayerAnvilUpdateEvent extends IEventCancelable {
    
    @ZenGetter("leftItem")
    IItemStack getLeftItem();
    
    @ZenGetter("rightItem")
    IItemStack getRightItem();
    
    @ZenGetter("outputItem")
    IItemStack getOutputItem();
    
    @ZenSetter("outputItem")
    void setOutputItem(IItemStack output);
    
    @ZenGetter("itemName")
    String getItemName();
    
    @ZenGetter("xpCost")
    int getXPCost();
    
    @ZenSetter("xpCost")
    void setXPCost(int xp);
    
    @ZenGetter("materialCost")
    int getMaterialCost();
    
    @ZenSetter("materialCost")
    void setMaterialCost(int materialCost);
}
