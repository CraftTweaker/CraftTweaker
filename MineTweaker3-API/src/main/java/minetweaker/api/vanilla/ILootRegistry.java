package minetweaker.api.vanilla;

import minetweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("vanilla.ILootRegistry")
public interface ILootRegistry {

    @ZenMethod
    void addChestLoot(String name, WeightedItemStack item);

    @ZenMethod
    void addChestLoot(String name, WeightedItemStack item, int min, int max);

    @ZenMethod
    void removeChestLoot(String name, IIngredient ingredient);

    @ZenMethod
    List<LootEntry> getLoot(String name);

    @ZenGetter("lootTypes")
    List<String> getLootTypes();
}
