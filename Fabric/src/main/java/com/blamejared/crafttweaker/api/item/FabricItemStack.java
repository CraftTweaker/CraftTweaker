package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.FabricItemStack")
@Document("fabric/api/item/FabricItemStack")
public interface FabricItemStack extends IItemStack {
    
    @Override
    default int getBurnTime() {
        
        return FuelRegistry.INSTANCE.get(getInternal().getItem());
    }
    
}
