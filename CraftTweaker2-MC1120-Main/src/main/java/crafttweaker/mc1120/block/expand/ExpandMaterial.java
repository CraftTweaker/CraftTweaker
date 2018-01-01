package crafttweaker.mc1120.block.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IMaterial;
import crafttweaker.mc1120.block.MCMaterial;
import net.minecraft.block.material.Material;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.block.IMaterial")
@ZenRegister
public class ExpandMaterial {
    
    public static final IMaterial AIR = new MCMaterial(Material.AIR);
    public static final IMaterial ANVIL = new MCMaterial(Material.ANVIL);
    public static final IMaterial BARRIER = new MCMaterial(Material.BARRIER);
    public static final IMaterial CACTUS = new MCMaterial(Material.CACTUS);
    public static final IMaterial CAKE = new MCMaterial(Material.CAKE);
    public static final IMaterial CARPET = new MCMaterial(Material.CARPET);
    public static final IMaterial CIRCUITS = new MCMaterial(Material.CIRCUITS);
    public static final IMaterial CLAY = new MCMaterial(Material.CLAY);
    public static final IMaterial CLOTH = new MCMaterial(Material.CLOTH);
    public static final IMaterial CORAL = new MCMaterial(Material.CORAL);
    public static final IMaterial CRAFTED_SNOW = new MCMaterial(Material.CRAFTED_SNOW);
    public static final IMaterial DRAGON_EGG = new MCMaterial(Material.DRAGON_EGG);
    public static final IMaterial FIRE = new MCMaterial(Material.FIRE);
    public static final IMaterial GLASS = new MCMaterial(Material.GLASS);
    public static final IMaterial GOURD = new MCMaterial(Material.GOURD);
    public static final IMaterial GRASS = new MCMaterial(Material.GRASS);
    public static final IMaterial GROUND = new MCMaterial(Material.GROUND);
    public static final IMaterial ICE = new MCMaterial(Material.ICE);
    public static final IMaterial IRON = new MCMaterial(Material.IRON);
    public static final IMaterial LAVA = new MCMaterial(Material.LAVA);
    public static final IMaterial LEAVES = new MCMaterial(Material.LEAVES);
    public static final IMaterial PACKED_ICE = new MCMaterial(Material.PACKED_ICE);
    public static final IMaterial PISTON = new MCMaterial(Material.PISTON);
    public static final IMaterial PLANTS = new MCMaterial(Material.PLANTS);
    public static final IMaterial PORTAL = new MCMaterial(Material.PORTAL);
    public static final IMaterial REDSTONE_LIGHT = new MCMaterial(Material.REDSTONE_LIGHT);
    public static final IMaterial ROCK = new MCMaterial(Material.ROCK);
    public static final IMaterial SAND = new MCMaterial(Material.SAND);
    public static final IMaterial SNOW = new MCMaterial(Material.SNOW);
    public static final IMaterial SPONGE = new MCMaterial(Material.SPONGE);
    public static final IMaterial STRUCTURE_VOID = new MCMaterial(Material.STRUCTURE_VOID);
    public static final IMaterial TNT = new MCMaterial(Material.TNT);
    public static final IMaterial VINE = new MCMaterial(Material.VINE);
    public static final IMaterial WATER = new MCMaterial(Material.WATER);
    public static final IMaterial WEB = new MCMaterial(Material.WEB);
    public static final IMaterial WOOD = new MCMaterial(Material.WOOD);
    
    @ZenMethodStatic
    public static IMaterial air() {
        return AIR;
    }
    
    @ZenMethodStatic
    public static IMaterial anvil() {
        return ANVIL;
    }
    
    @ZenMethodStatic
    public static IMaterial barrier() {
        return BARRIER;
    }
    
    @ZenMethodStatic
    public static IMaterial cactus() {
        return CACTUS;
    }
    
    @ZenMethodStatic
    public static IMaterial cake() {
        return CAKE;
    }
    
    @ZenMethodStatic
    public static IMaterial carpet() {
        return CARPET;
    }
    
    @ZenMethodStatic
    public static IMaterial circuits() {
        return CIRCUITS;
    }
    
    @ZenMethodStatic
    public static IMaterial clay() {
        return CLAY;
    }
    
    @ZenMethodStatic
    public static IMaterial cloth() {
        return CLOTH;
    }
    
    @ZenMethodStatic
    public static IMaterial coral() {
        return CORAL;
    }
    
    @ZenMethodStatic
    public static IMaterial craftedSnow() {
        return CRAFTED_SNOW;
    }
    
    @ZenMethodStatic
    public static IMaterial dragonEgg() {
        return DRAGON_EGG;
    }
    
    @ZenMethodStatic
    public static IMaterial fire() {
        return FIRE;
    }
    
    @ZenMethodStatic
    public static IMaterial glass() {
        return GLASS;
    }
    
    @ZenMethodStatic
    public static IMaterial gourd() {
        return GOURD;
    }
    
    @ZenMethodStatic
    public static IMaterial grass() {
        return GRASS;
    }
    
    @ZenMethodStatic
    public static IMaterial ground() {
        return GROUND;
    }
    
    @ZenMethodStatic
    public static IMaterial ice() {
        return ICE;
    }
    
    @ZenMethodStatic
    public static IMaterial iron() {
        return IRON;
    }
    
    @ZenMethodStatic
    public static IMaterial lava() {
        return LAVA;
    }
    
    @ZenMethodStatic
    public static IMaterial leaves() {
        return LEAVES;
    }
    
    @ZenMethodStatic
    public static IMaterial packedIce() {
        return PACKED_ICE;
    }
    
    @ZenMethodStatic
    public static IMaterial piston() {
        return PISTON;
    }
    
    @ZenMethodStatic
    public static IMaterial plants() {
        return PLANTS;
    }
    
    @ZenMethodStatic
    public static IMaterial portal() {
        return PORTAL;
    }
    
    @ZenMethodStatic
    public static IMaterial redstoneLight() {
        return REDSTONE_LIGHT;
    }
    
    @ZenMethodStatic
    public static IMaterial rock() {
        return ROCK;
    }
    
    @ZenMethodStatic
    public static IMaterial sand() {
        return SAND;
    }
    
    @ZenMethodStatic
    public static IMaterial snow() {
        return SNOW;
    }
    
    @ZenMethodStatic
    public static IMaterial sponge() {
        return SPONGE;
    }
    
    @ZenMethodStatic
    public static IMaterial structureVoid() {
        return STRUCTURE_VOID;
    }
    
    @ZenMethodStatic
    public static IMaterial tnt() {
        return TNT;
    }
    
    @ZenMethodStatic
    public static IMaterial vine() {
        return VINE;
    }
    
    @ZenMethodStatic
    public static IMaterial water() {
        return WATER;
    }
    
    @ZenMethodStatic
    public static IMaterial web() {
        return WEB;
    }
    
    @ZenMethodStatic
    public static IMaterial wood() {
        return WOOD;
    }
    
}
