package crafttweaker.mc1120.util.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IRandom;
import crafttweaker.mc1120.util.MCRandom;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.Random;

@ZenExpansion("crafttweaker.util.IRandom")
@ZenRegister
public class ExpandRandom {
    @ZenMethodStatic
    public static IRandom getInstance(long seed) {
        return new MCRandom(new Random(seed));
    }
}
