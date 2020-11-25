package crafttweaker.mc1120.world.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBiome;
import crafttweaker.api.world.IBiomeType;
import crafttweaker.mc1120.world.MCBiome;
import net.minecraftforge.common.BiomeDictionary;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.List;
import java.util.stream.Collectors;

@ZenExpansion("crafttweaker.world.IBiomeType")
@ZenRegister
public class ExpandBiomeType {
    private static BiomeDictionary.Type getInternal(IBiomeType expanded) {
        return CraftTweakerMC.getBiomeType(expanded);
    }

    @ZenGetter("biomes")
    public static List<IBiome> getBiomes(IBiomeType biomeType) {
        return BiomeDictionary.getBiomes(getInternal(biomeType)).stream().map(MCBiome::new).collect(Collectors.toList());
    }
}