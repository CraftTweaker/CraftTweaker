package crafttweaker.api.mods;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.mods.ILoadedMods")
@IterableSimple("crafttweaker.mods.IMod")
@ZenRegister
public interface ILoadedMods extends Iterable<IMod> {

    @ZenOperator(OperatorType.CONTAINS)
    @ZenMethod
    boolean contains(String name);

    @ZenOperator(OperatorType.INDEXGET)
    IMod get(String name);
}
