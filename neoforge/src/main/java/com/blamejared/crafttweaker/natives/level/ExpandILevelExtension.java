package com.blamejared.crafttweaker.natives.level;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/world/ILevelExtension")
@NativeTypeRegistration(value = ILevelExtension.class, zenCodeName = "crafttweaker.neoforge.api.world.ILevelExtension")
public class ExpandILevelExtension {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T, C> T getCapability(ILevelExtension internal, Class<T> tClass, Class<C> cClass, BlockCapability<T, C> cap, BlockPos pos, @ZenCodeType.Nullable C context) {
        
        return internal.getCapability(cap, pos, context);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T, C> T getCapability(ILevelExtension internal, Class<T> tClass, Class<C> cClass, BlockCapability<T, C> cap, BlockPos pos, @ZenCodeType.Nullable BlockState state, @ZenCodeType.Nullable BlockEntity blockEntity, @ZenCodeType.Nullable C context) {
        
        return internal.getCapability(cap, pos, state, blockEntity, context);
    }
    
}
