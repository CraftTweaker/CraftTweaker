package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.item.EntityItem;

public class MCEntityItem extends MCEntity implements IEntityItem {
    
    private final EntityItem item;
    
    public MCEntityItem(EntityItem entityItem) {
        super(entityItem);
        item = entityItem;
    }
    
    @Override
    public IItemStack getItem() {
        return CraftTweakerMC.getIItemStack(item.getItem());
    }
}
