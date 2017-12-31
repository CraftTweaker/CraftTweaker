package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("vanilla.ILootRegistry")
@ZenRegister
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
