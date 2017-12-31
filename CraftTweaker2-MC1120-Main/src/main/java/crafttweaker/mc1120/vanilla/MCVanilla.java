package crafttweaker.mc1120.vanilla;

import crafttweaker.api.vanilla.ISeedRegistry;
import crafttweaker.api.vanilla.IVanilla;

/**
 * @author Stan
 */
public class MCVanilla implements IVanilla {

    private final MCSeedRegistry seedRegistry = new MCSeedRegistry();

    @Override
    public ISeedRegistry getSeeds() {
        return seedRegistry;
    }
}
