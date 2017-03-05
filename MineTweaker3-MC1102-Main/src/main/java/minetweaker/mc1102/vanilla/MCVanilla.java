package minetweaker.mc1102.vanilla;

import minetweaker.api.vanilla.*;

/**
 * @author Stan
 */
public class MCVanilla implements IVanilla {

    //    private final MCLootRegistry lootRegistry = new MCLootRegistry();
    private final MCSeedRegistry seedRegistry = new MCSeedRegistry();

    @Override
    public ILootRegistry getLoot() {
        return null;
    }

    @Override
    public ISeedRegistry getSeeds() {
        return seedRegistry;
    }
}
