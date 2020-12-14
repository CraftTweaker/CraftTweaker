package com.blamejared.crafttweaker.impl_native.event.entity;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeExpansion(EntityEvent.class)
public class ExpandEntityEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entity")
    public static Entity getEntity(EntityEvent internal) {
        return internal.getEntity();
    }
}
