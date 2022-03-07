package com.blamejared.crafttweaker.impl_native.capability;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/capability/IFluidHandlerItem")
@NativeTypeRegistration(value = IFluidHandlerItem.class, zenCodeName = "crafttweaker.api.capability.IFluidHandlerItem")
public class ExpandIFluidHandlerItem {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("container")
    public static ItemStack getContainer(IFluidHandlerItem internal) {
        
        return internal.getContainer();
    }
    
}
