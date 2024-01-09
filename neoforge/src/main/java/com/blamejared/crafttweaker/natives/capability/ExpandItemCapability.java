package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("neoforge/api/capability/ItemCapability")
@NativeTypeRegistration(value = ItemCapability.class, zenCodeName = "crafttweaker.neoforge.api.capability.ItemCapability")
public class ExpandItemCapability {
    
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static <T, U> T getCapability(ItemCapability internal, ItemStack entity, U context) {
        
        return GenericUtil.uncheck(internal.getCapability(entity, context));
    }
    
    
}
