package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/LootParamsBuilder")
@NativeTypeRegistration(value = LootParams.Builder.class, zenCodeName = "crafttweaker.api.loot.LootParamsBuilder")
public final class ExpandLootParamsBuilder {
    
    @ZenCodeType.StaticExpansionMethod
    public static LootParams.Builder create(ServerLevel level) {
        
        return new LootParams.Builder(level);
    }
    
    @ZenCodeType.Getter
    public static ServerLevel getLevel(LootParams.Builder internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Method
    public static <T> LootParams.Builder withParameter(LootParams.Builder internal, Class<T> tClass, LootContextParam<T> param, T value) {
        
        return internal.withParameter(param, value);
    }
    
    @ZenCodeType.Method
    public static <T> LootParams.Builder withOptionalParameter(LootParams.Builder internal, Class<T> tClass, LootContextParam<T> param, @ZenCodeType.Nullable T value) {
        
        return internal.withOptionalParameter(param, value);
    }
    
    @ZenCodeType.Method
    public static <T> T getParameter(LootParams.Builder internal, Class<T> tClass, LootContextParam<T> param) {
        
        return internal.getParameter(param);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T getOptionalParameter(LootParams.Builder internal, Class<T> tClass, LootContextParam<T> param) {
        
        return internal.getOptionalParameter(param);
    }
    
    @ZenCodeType.Method
    public static LootParams.Builder withDynamicDrop(LootParams.Builder internal, ResourceLocation key, LootParams.DynamicDrop drop) {
        
        return internal.withDynamicDrop(key, drop);
    }
    
    @ZenCodeType.Method
    public static LootParams.Builder withLuck(LootParams.Builder internal, float luck) {
        
        return internal.withLuck(luck);
    }
    
    @ZenCodeType.Method
    public static LootParams build(LootParams.Builder internal, LootContextParamSet params) {
        
        return internal.create(params);
    }
    
}
