package com.blamejared.crafttweaker.impl_native.block;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@DocumentAsType
@NativeExpansion(Material.class)
public class ExpandMaterial {
    
    private static final BiMap<String, Material> hardcodedMaterials = HashBiMap.create();
    static {
        hardcodedMaterials.put("AIR", Material.AIR);
        hardcodedMaterials.put("STRUCTURE_VOID", Material.STRUCTURE_VOID);
        hardcodedMaterials.put("PORTAL", Material.PORTAL);
        hardcodedMaterials.put("CARPET", Material.CARPET);
        hardcodedMaterials.put("PLANTS", Material.PLANTS);
        hardcodedMaterials.put("OCEAN_PLANT", Material.OCEAN_PLANT);
        hardcodedMaterials.put("TALL_PLANTS", Material.TALL_PLANTS);
        hardcodedMaterials.put("SEA_GRASS", Material.SEA_GRASS);
        hardcodedMaterials.put("WATER", Material.WATER);
        hardcodedMaterials.put("BUBBLE_COLUMN", Material.BUBBLE_COLUMN);
        hardcodedMaterials.put("LAVA", Material.LAVA);
        hardcodedMaterials.put("SNOW", Material.SNOW);
        hardcodedMaterials.put("FIRE", Material.FIRE);
        hardcodedMaterials.put("MISCELLANEOUS", Material.MISCELLANEOUS);
        hardcodedMaterials.put("WEB", Material.WEB);
        hardcodedMaterials.put("REDSTONE_LIGHT", Material.REDSTONE_LIGHT);
        hardcodedMaterials.put("CLAY", Material.CLAY);
        hardcodedMaterials.put("EARTH", Material.EARTH);
        hardcodedMaterials.put("ORGANIC", Material.ORGANIC);
        hardcodedMaterials.put("PACKED_ICE", Material.PACKED_ICE);
        hardcodedMaterials.put("SAND", Material.SAND);
        hardcodedMaterials.put("SPONGE", Material.SPONGE);
        hardcodedMaterials.put("SHULKER", Material.SHULKER);
        hardcodedMaterials.put("WOOD", Material.WOOD);
        hardcodedMaterials.put("BAMBOO_SAPLING", Material.BAMBOO_SAPLING);
        hardcodedMaterials.put("BAMBOO", Material.BAMBOO);
        hardcodedMaterials.put("WOOL", Material.WOOL);
        hardcodedMaterials.put("TNT", Material.TNT);
        hardcodedMaterials.put("LEAVES", Material.LEAVES);
        hardcodedMaterials.put("GLASS", Material.GLASS);
        hardcodedMaterials.put("ICE", Material.ICE);
        hardcodedMaterials.put("CACTUS", Material.CACTUS);
        hardcodedMaterials.put("ROCK", Material.ROCK);
        hardcodedMaterials.put("IRON", Material.IRON);
        hardcodedMaterials.put("SNOW_BLOCK", Material.SNOW_BLOCK);
        hardcodedMaterials.put("ANVIL", Material.ANVIL);
        hardcodedMaterials.put("BARRIER", Material.BARRIER);
        hardcodedMaterials.put("PISTON", Material.PISTON);
        hardcodedMaterials.put("CORAL", Material.CORAL);
        hardcodedMaterials.put("GOURD", Material.GOURD);
        hardcodedMaterials.put("DRAGON_EGG", Material.DRAGON_EGG);
        hardcodedMaterials.put("CAKE", Material.CAKE);
    }
    
    /**
     * Indicate if the material is opaque
     */
    @ZenCodeType.Method
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
    public static boolean isSolid(Material internal) {
        return internal.isSolid();
    }
    
    /**
     * Returns if the block can burn or not.
     */
    @ZenCodeType.Method
    public static boolean isFlammable(Material internal) {
        return internal.isFlammable();
    }
    
    /**
     * Returns if blocks of these materials are liquids.
     */
    @ZenCodeType.Method
    public static boolean isLiquid(Material internal) {
        return internal.isLiquid();
    }
    
    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    @ZenCodeType.Method
    public static boolean isReplaceable(Material internal) {
        return internal.isReplaceable();
    }
    
    /**
     * Returns if this material is considered solid or not
     */
    @ZenCodeType.Method
    public static boolean blocksMovement(Material internal) {
        return internal.blocksMovement();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Material internal) {
        final BiMap<Material, String> inverse = hardcodedMaterials.inverse();
        final String name = inverse.getOrDefault(internal, "UNKNOWN");
        return "<blockmaterial:" + name + ">";
        
    }
    
    public static Material tryGet(String tokens) {
        return hardcodedMaterials.get(tokens);
    }
}
