package com.blamejared.crafttweaker.natives.block.material;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.Util;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Locale;
import java.util.Optional;

@ZenRegister
@Document("vanilla/api/block/material/Material")
@NativeTypeRegistration(value = Material.class, zenCodeName = "crafttweaker.api.block.material.Material")
public class ExpandMaterial {
    
    public static final BiMap<String, Material> VANILLA_MATERIALS = Util.make(HashBiMap.create(), map -> {
        map.put("AIR", Material.AIR);
        map.put("STRUCTURAL_AIR", Material.STRUCTURAL_AIR);
        map.put("PORTAL", Material.PORTAL);
        map.put("CLOTH_DECORATION", Material.CLOTH_DECORATION);
        map.put("PLANT", Material.PLANT);
        map.put("WATER_PLANT", Material.WATER_PLANT);
        map.put("REPLACEABLE_PLANT", Material.REPLACEABLE_PLANT);
        map.put("REPLACEABLE_FIREPROOF_PLANT", Material.REPLACEABLE_FIREPROOF_PLANT);
        map.put("REPLACEABLE_WATER_PLANT", Material.REPLACEABLE_WATER_PLANT);
        map.put("WATER", Material.WATER);
        map.put("BUBBLE_COLUMN", Material.BUBBLE_COLUMN);
        map.put("LAVA", Material.LAVA);
        map.put("TOP_SNOW", Material.TOP_SNOW);
        map.put("FIRE", Material.FIRE);
        map.put("DECORATION", Material.DECORATION);
        map.put("WEB", Material.WEB);
        map.put("SCULK", Material.SCULK);
        map.put("BUILDABLE_GLASS", Material.BUILDABLE_GLASS);
        map.put("CLAY", Material.CLAY);
        map.put("DIRT", Material.DIRT);
        map.put("GRASS", Material.GRASS);
        map.put("ICE_SOLID", Material.ICE_SOLID);
        map.put("SAND", Material.SAND);
        map.put("SPONGE", Material.SPONGE);
        map.put("SHULKER_SHELL", Material.SHULKER_SHELL);
        map.put("WOOD", Material.WOOD);
        map.put("NETHER_WOOD", Material.NETHER_WOOD);
        map.put("BAMBOO_SAPLING", Material.BAMBOO_SAPLING);
        map.put("BAMBOO", Material.BAMBOO);
        map.put("WOOL", Material.WOOL);
        map.put("EXPLOSIVE", Material.EXPLOSIVE);
        map.put("LEAVES", Material.LEAVES);
        map.put("GLASS", Material.GLASS);
        map.put("ICE", Material.ICE);
        map.put("CACTUS", Material.CACTUS);
        map.put("STONE", Material.STONE);
        map.put("METAL", Material.METAL);
        map.put("SNOW", Material.SNOW);
        map.put("HEAVY_METAL", Material.HEAVY_METAL);
        map.put("BARRIER", Material.BARRIER);
        map.put("PISTON", Material.PISTON);
        map.put("MOSS", Material.MOSS);
        map.put("VEGETABLE", Material.VEGETABLE);
        map.put("EGG", Material.EGG);
        map.put("CAKE", Material.CAKE);
        map.put("AMETHYST", Material.AMETHYST);
        map.put("POWDER_SNOW", Material.POWDER_SNOW);
    });
    
    public static Optional<Material> getOptionalMaterial(String tokens) {
        
        return Optional.ofNullable(VANILLA_MATERIALS.get(tokens));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isLiquid")
    public static boolean isLiquid(Material internal) {
        
        return internal.isLiquid();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSolid")
    public static boolean isSolid(Material internal) {
        
        return internal.isSolid();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blocksMotion")
    public static boolean blocksMotion(Material internal) {
        
        return internal.blocksMotion();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFlammable")
    public static boolean isFlammable(Material internal) {
        
        return internal.isFlammable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isReplaceable")
    public static boolean isReplaceable(Material internal) {
        
        return internal.isReplaceable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSolidBlocking")
    public static boolean isSolidBlocking(Material internal) {
        
        return internal.isSolidBlocking();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pushReaction")
    public static PushReaction getPushReaction(Material internal) {
        
        return internal.getPushReaction();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("color")
    public static MaterialColor getColor(Material internal) {
        
        return internal.getColor();
    }
    
    /**
     * Gets the bracket syntax for this Material
     *
     * @return The {@code <material>} Bracket Syntax for this material
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Material internal) {
        
        final BiMap<Material, String> inverse = VANILLA_MATERIALS.inverse();
        final String name = inverse.getOrDefault(internal, "UNKNOWN");
        return "<material:" + name.toLowerCase(Locale.ROOT) + ">";
    }
    
}
