package crafttweaker.api.recipes;

import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IDimension;

/**
 * @author Stan
 */
public class CraftingInfo implements ICraftingInfo {

    private final ICraftingInventory inventory;
    private final IDimension dimension;

    public CraftingInfo(ICraftingInventory inventory, IDimension dimension) {
        this.inventory = inventory;
        this.dimension = dimension;
    }

    @Override
    public ICraftingInventory getInventory() {
        return inventory;
    }

    @Override
    public IPlayer getPlayer() {
        return inventory.getPlayer();
    }

    @Override
    public IDimension getDimension() {
        return dimension;
    }
}
