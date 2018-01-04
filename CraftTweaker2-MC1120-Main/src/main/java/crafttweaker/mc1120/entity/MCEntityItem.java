package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class MCEntityItem extends MCEntity implements IEntityItem {
    
    private final EntityItem item;
    
    public MCEntityItem(EntityItem entityItem) {
        super(entityItem);
        item = entityItem;
    }
    
    @Override
    public IItemStack getItem() {
        ItemStack ret = item.getItem();
        return (item == null || item.getItem().isEmpty()) ? null : new MCItemStack(ret);
    }
}
