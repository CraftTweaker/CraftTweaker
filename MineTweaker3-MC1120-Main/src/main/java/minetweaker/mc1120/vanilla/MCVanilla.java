package minetweaker.mc1120.vanilla;

import minetweaker.api.vanilla.*;
import net.minecraft.world.storage.loot.LootTableManager;

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
