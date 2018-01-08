package crafttweaker.mc1120.world.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.world.IWorld")
@ZenRegister
public class ExpandWorld {
    
    @ZenMethodStatic
    public static IWorld getFromID(int id) {
        return CraftTweakerMC.getWorldByID(id);
    }
}
