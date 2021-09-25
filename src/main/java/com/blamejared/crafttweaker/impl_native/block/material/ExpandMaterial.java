package com.blamejared.crafttweaker.impl_native.block.material;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.Util;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Locale;

@ZenRegister
@Document("vanilla/api/block/material/MCMaterial")
@NativeTypeRegistration(value = Material.class, zenCodeName = "crafttweaker.api.block.material.MCMaterial")
public class ExpandMaterial {
    
    public static final BiMap<String, Material> hardcodedMaterials = Util.make(HashBiMap.create(), map -> {
        map.put("AIR", Material.AIR);
        map.put("STRUCTURE_VOID", Material.STRUCTURE_VOID);
        map.put("PORTAL", Material.PORTAL);
        map.put("CARPET", Material.CARPET);
        map.put("PLANTS", Material.PLANTS);
        map.put("OCEAN_PLANT", Material.OCEAN_PLANT);
        map.put("TALL_PLANTS", Material.TALL_PLANTS);
        map.put("NETHER_PLANTS", Material.NETHER_PLANTS);
        map.put("SEA_GRASS", Material.SEA_GRASS);
        map.put("WATER", Material.WATER);
        map.put("BUBBLE_COLUMN", Material.BUBBLE_COLUMN);
        map.put("LAVA", Material.LAVA);
        map.put("SNOW", Material.SNOW);
        map.put("FIRE", Material.FIRE);
        map.put("MISCELLANEOUS", Material.MISCELLANEOUS);
        map.put("WEB", Material.WEB);
        map.put("REDSTONE_LIGHT", Material.REDSTONE_LIGHT);
        map.put("CLAY", Material.CLAY);
        map.put("EARTH", Material.EARTH);
        map.put("ORGANIC", Material.ORGANIC);
        map.put("PACKED_ICE", Material.PACKED_ICE);
        map.put("SAND", Material.SAND);
        map.put("SPONGE", Material.SPONGE);
        map.put("SHULKER", Material.SHULKER);
        map.put("WOOD", Material.WOOD);
        map.put("NETHER_WOOD", Material.NETHER_WOOD);
        map.put("BAMBOO_SAPLING", Material.BAMBOO_SAPLING);
        map.put("BAMBOO", Material.BAMBOO);
        map.put("WOOL", Material.WOOL);
        map.put("TNT", Material.TNT);
        map.put("LEAVES", Material.LEAVES);
        map.put("GLASS", Material.GLASS);
        map.put("ICE", Material.ICE);
        map.put("CACTUS", Material.CACTUS);
        map.put("ROCK", Material.ROCK);
        map.put("IRON", Material.IRON);
        map.put("SNOW_BLOCK", Material.SNOW_BLOCK);
        map.put("ANVIL", Material.ANVIL);
        map.put("BARRIER", Material.BARRIER);
        map.put("PISTON", Material.PISTON);
        map.put("CORAL", Material.CORAL);
        map.put("GOURD", Material.GOURD);
        map.put("DRAGON_EGG", Material.DRAGON_EGG);
        map.put("CAKE", Material.CAKE);
    });
    
    /**
     * Indicate if the material is opaque
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("opaque")
    public static boolean isOpaque(Material internal) {
        
        return internal.isOpaque();
    }
    
    /**
     * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("color")
    public static MaterialColor getColor(Material internal) {
        
        return internal.getColor();
    }
    
    
    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("solid")
    public static boolean isSolid(Material internal) {
        
        return internal.isSolid();
    }
    
    /**
     * Returns if the block can burn or not.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("flammable")
    public static boolean isFlammable(Material internal) {
        
        return internal.isFlammable();
    }
    
    /**
     * Returns if blocks of these materials are liquids.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("liquid")
    public static boolean isLiquid(Material internal) {
        
        return internal.isLiquid();
    }
    
    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("replaceable")
    public static boolean isReplaceable(Material internal) {
        
        return internal.isReplaceable();
    }
    
    /**
     * Returns if this material is considered solid or not
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("blocksMovement")
    public static boolean blocksMovement(Material internal) {
        
        return internal.blocksMovement();
    }
    
    /**
     * Gets this Material's {@link PushReaction}.
     *
     * @return The {@link PushReaction} of this Material.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("pushReaction")
    public static PushReaction getPushReaction(Material internal) {
        
        return internal.getPushReaction();
    }
    
    /**
     * Gets the bracket syntax for this Material
     *
     * @return The {@code <blockmaterial>} Bracket Syntax for this material
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Material internal) {
        
        final BiMap<Material, String> inverse = hardcodedMaterials.inverse();
        final String name = inverse.getOrDefault(internal, "UNKNOWN");
        return "<blockmaterial:" + name.toLowerCase(Locale.ROOT) + ">";
        
    }
    
    public static Material tryGet(String tokens) {
    
        return hardcodedMaterials.get(tokens.toUpperCase());
    }
    
}
