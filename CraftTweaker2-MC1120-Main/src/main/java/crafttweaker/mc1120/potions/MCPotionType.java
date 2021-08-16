package crafttweaker.mc1120.potions;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import crafttweaker.api.potions.IPotionType;
import net.minecraft.potion.PotionType;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCPotionType that = (MCPotionType) o;
        return Objects.equals(potionType, that.potionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(potionType);
    }
}