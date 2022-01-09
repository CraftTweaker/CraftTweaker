package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.ForgeHooks;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.ForgeItemStack")
@Document("forge/api/item/ForgeItemStack")
public interface ForgeItemStack extends IItemStack {
    
    @Override
    default int getBurnTime() {
        
        return ForgeHooks.getBurnTime(getInternal(), null);
    }
    
}
