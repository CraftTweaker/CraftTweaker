package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("vanilla.IVanilla")
@ZenRegister
public interface IVanilla {

    @ZenGetter("seeds")
    ISeedRegistry getSeeds();
}
