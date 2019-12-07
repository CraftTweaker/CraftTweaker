package minetweaker.api.mods;

import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.mods.ILoadedMods")
@IterableSimple("minetweaker.mods.IMod")
public interface ILoadedMods extends Iterable<IMod> {
    
    @ZenOperator(OperatorType.CONTAINS)
    boolean contains(String name);
    
    @ZenOperator(OperatorType.INDEXGET)
    IMod get(String name);
}
