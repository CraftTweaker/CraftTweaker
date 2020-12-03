package crafttweaker.mc1120.potions;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import crafttweaker.api.potions.IPotionType;
import net.minecraft.potion.PotionType;

public class MCPotionType implements IPotionType {
    public final PotionType potionType;

    public MCPotionType(PotionType potionType) {
        this.potionType = potionType;
    }

    @Override
    public PotionType getInternal() {
        return potionType;
    }

    @Override
    public IPotionEffect[] getPotionEffects() {
        return CraftTweakerMC.getIPotionEffects(potionType.getEffects());
    }
}