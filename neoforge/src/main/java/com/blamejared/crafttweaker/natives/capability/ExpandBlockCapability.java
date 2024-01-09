package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/BlockCapability")
@NativeTypeRegistration(value = BlockCapability.class, zenCodeName = "crafttweaker.neoforge.api.capability.BlockCapability")
public class ExpandBlockCapability {
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static <T, U> T getCapability(BlockCapability internal, Class<T> tClass, Class<U> uClass, Level level, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, U context) {
        
        return GenericUtil.uncheck(internal.getCapability(level, pos, state, blockEntity, context));
    }
    
}
