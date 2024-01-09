package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@Document("vanilla/api/loot/LootParams")
@NativeTypeRegistration(value = LootParams.class, zenCodeName = "crafttweaker.api.loot.LootParams")
public final class ExpandLootParams {
    
    
    @ZenCodeType.Getter("level")
    public static ServerLevel getLevel(LootParams internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Method
    public static <T> boolean hasParam(LootParams internal, Class<T> tClass, LootContextParam<T> param) {
        
        return internal.hasParam(param);
    }
    
    @ZenCodeType.Method
    public static <T> T getParameter(LootParams internal, Class<T> tClass, LootContextParam<T> param) {
        
        return internal.getParameter(param);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getOptionalParameter(LootParams internal, Class<T> tClass, LootContextParam<T> param) {
        
        return internal.getOptionalParameter(param);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getParamOrNull( LootParams internal, Class<T> tClass, LootContextParam<T> param) {
        
        return internal.getParamOrNull(param);
    }
    
    @ZenCodeType.Method
    public static void addDynamicDrops(LootParams internal, ResourceLocation key, Consumer<ItemStack> drop) {
        
        internal.addDynamicDrops(key, drop);
    }
    
    @ZenCodeType.Getter("luck")
    public static float getLuck(LootParams internal) {
        
        return internal.getLuck();
    }
    
}
