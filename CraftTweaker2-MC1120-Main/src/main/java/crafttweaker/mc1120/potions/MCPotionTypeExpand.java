package crafttweaker.mc1120.potions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionType;
import net.minecraft.potion.PotionType;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.potions.IPotionType")
@ZenRegister
public class MCPotionTypeExpand {

    @ZenMethodStatic
    public static IPotionType fromString(String id) {
        return CraftTweakerMC.getIPotionType(PotionType.getPotionTypeForName(id));
    }
}