package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("vanilla.IVanilla")
@ZenRegister
public interface IVanilla {
    
    @ZenGetter("seeds")
    ISeedRegistry getSeeds();
}
