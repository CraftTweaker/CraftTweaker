package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A loot context param is provided to a {@link net.minecraft.world.level.storage.loot.LootContext} allowing it to supply information to functions.
 *
 * <p>See {@link net.minecraft.world.level.storage.loot.parameters.LootContextParams} for a list of built-in params.</p>
 *
 * @docParam this LootContextParams.thisEntity()
 */
@ZenRegister
@Document("vanilla/api/loot/param/LootContextParam")
@NativeTypeRegistration(value = LootContextParam.class, zenCodeName = "crafttweaker.api.loot.param.LootContextParam")
public final class ExpandLootContextParam {
    
    /**
     * Gets the name of this parameter.
     *
     * @return The name of this parameter
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static ResourceLocation getName(LootContextParam internal) {
        
        return internal.getName();
    }
    
}
