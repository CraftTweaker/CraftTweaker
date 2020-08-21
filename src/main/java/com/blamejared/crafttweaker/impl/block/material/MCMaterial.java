package com.blamejared.crafttweaker.impl.block.material;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.block.material.Material;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.material.MCMaterial")
@Document("vanilla/api/block/material/MCMaterial")
@ZenWrapper(wrappedClass = "net.minecraft.block.material.Material", displayStringFormat = "%s.toString()", creationMethodFormat = "new MCMaterial(%s, \"unknown\")")
public class MCMaterial implements CommandStringDisplayable {
    
    private static final Map<String, MCMaterial> hardcodedMaterials = new HashMap<>();
    
    static {
        hardcodedMaterials.put("AIR", new MCMaterial(Material.AIR, "AIR"));
        hardcodedMaterials.put("STRUCTURE_VOID", new MCMaterial(Material.STRUCTURE_VOID, "STRUCTURE_VOID"));
        hardcodedMaterials.put("PORTAL", new MCMaterial(Material.PORTAL, "PORTAL"));
        hardcodedMaterials.put("CARPET", new MCMaterial(Material.CARPET, "CARPET"));
        hardcodedMaterials.put("PLANTS", new MCMaterial(Material.PLANTS, "PLANTS"));
        hardcodedMaterials.put("OCEAN_PLANT", new MCMaterial(Material.OCEAN_PLANT, "OCEAN_PLANT"));
        hardcodedMaterials.put("TALL_PLANTS", new MCMaterial(Material.TALL_PLANTS, "TALL_PLANTS"));
        hardcodedMaterials.put("SEA_GRASS", new MCMaterial(Material.SEA_GRASS, "SEA_GRASS"));
        hardcodedMaterials.put("WATER", new MCMaterial(Material.WATER, "WATER"));
        hardcodedMaterials.put("BUBBLE_COLUMN", new MCMaterial(Material.BUBBLE_COLUMN, "BUBBLE_COLUMN"));
        hardcodedMaterials.put("LAVA", new MCMaterial(Material.LAVA, "LAVA"));
        hardcodedMaterials.put("SNOW", new MCMaterial(Material.SNOW, "SNOW"));
        hardcodedMaterials.put("FIRE", new MCMaterial(Material.FIRE, "FIRE"));
        hardcodedMaterials.put("MISCELLANEOUS", new MCMaterial(Material.MISCELLANEOUS, "MISCELLANEOUS"));
        hardcodedMaterials.put("WEB", new MCMaterial(Material.WEB, "WEB"));
        hardcodedMaterials.put("REDSTONE_LIGHT", new MCMaterial(Material.REDSTONE_LIGHT, "REDSTONE_LIGHT"));
        hardcodedMaterials.put("CLAY", new MCMaterial(Material.CLAY, "CLAY"));
        hardcodedMaterials.put("EARTH", new MCMaterial(Material.EARTH, "EARTH"));
        hardcodedMaterials.put("ORGANIC", new MCMaterial(Material.ORGANIC, "ORGANIC"));
        hardcodedMaterials.put("PACKED_ICE", new MCMaterial(Material.PACKED_ICE, "PACKED_ICE"));
        hardcodedMaterials.put("SAND", new MCMaterial(Material.SAND, "SAND"));
        hardcodedMaterials.put("SPONGE", new MCMaterial(Material.SPONGE, "SPONGE"));
        hardcodedMaterials.put("SHULKER", new MCMaterial(Material.SHULKER, "SHULKER"));
        hardcodedMaterials.put("WOOD", new MCMaterial(Material.WOOD, "WOOD"));
        hardcodedMaterials.put("BAMBOO_SAPLING", new MCMaterial(Material.BAMBOO_SAPLING, "BAMBOO_SAPLING"));
        hardcodedMaterials.put("BAMBOO", new MCMaterial(Material.BAMBOO, "BAMBOO"));
        hardcodedMaterials.put("WOOL", new MCMaterial(Material.WOOL, "WOOL"));
        hardcodedMaterials.put("TNT", new MCMaterial(Material.TNT, "TNT"));
        hardcodedMaterials.put("LEAVES", new MCMaterial(Material.LEAVES, "LEAVES"));
        hardcodedMaterials.put("GLASS", new MCMaterial(Material.GLASS, "GLASS"));
        hardcodedMaterials.put("ICE", new MCMaterial(Material.ICE, "ICE"));
        hardcodedMaterials.put("CACTUS", new MCMaterial(Material.CACTUS, "CACTUS"));
        hardcodedMaterials.put("ROCK", new MCMaterial(Material.ROCK, "ROCK"));
        hardcodedMaterials.put("IRON", new MCMaterial(Material.IRON, "IRON"));
        hardcodedMaterials.put("SNOW_BLOCK", new MCMaterial(Material.SNOW_BLOCK, "SNOW_BLOCK"));
        hardcodedMaterials.put("ANVIL", new MCMaterial(Material.ANVIL, "ANVIL"));
        hardcodedMaterials.put("BARRIER", new MCMaterial(Material.BARRIER, "BARRIER"));
        hardcodedMaterials.put("PISTON", new MCMaterial(Material.PISTON, "PISTON"));
        hardcodedMaterials.put("CORAL", new MCMaterial(Material.CORAL, "CORAL"));
        hardcodedMaterials.put("GOURD", new MCMaterial(Material.GOURD, "GOURD"));
        hardcodedMaterials.put("DRAGON_EGG", new MCMaterial(Material.DRAGON_EGG, "DRAGON_EGG"));
        hardcodedMaterials.put("CAKE", new MCMaterial(Material.CAKE, "CAKE"));
    }
    private final Material internal;
    private final String name;
    
    public MCMaterial(Material internal, String name) {
        this.internal = internal;
        this.name = name;
    }
    
    public static MCMaterial tryGet(String tokens) {
        return hardcodedMaterials.get(tokens.toUpperCase());
    }
    
    public Material getInternal() {
        return this.internal;
    }
    
    /**
     * Indicate if the material is opaque
     */
    @ZenCodeType.Method
    public boolean isOpaque() {
        return internal.isOpaque();
    }
    
    
    /**
     * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
     */
    @ZenCodeType.Method
    public MCMaterialColor getColor() {
        return new MCMaterialColor(internal.getColor());
    }
    
    
    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    @ZenCodeType.Method
    public boolean isSolid() {
        return internal.isSolid();
    }
    
    
    /**
     * Returns if the block can burn or not.
     */
    @ZenCodeType.Method
    public boolean isFlammable() {
        return internal.isFlammable();
    }
    
    
    /**
     * Returns if blocks of these materials are liquids.
     */
    @ZenCodeType.Method
    public boolean isLiquid() {
        return internal.isLiquid();
    }
    
    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    @ZenCodeType.Method
    public boolean isReplaceable() {
        return internal.isReplaceable();
    }
    
    
    /**
     * Returns if this material is considered solid or not
     */
    @ZenCodeType.Method
    public boolean blocksMovement() {
        return internal.blocksMovement();
    }
    
    
    @Override
    public String getCommandString() {
        return "<blockmaterial:" + getName() + ">";
    }
    
    public String getName() {
        return this.name;
    }
}
