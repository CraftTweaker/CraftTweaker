package crafttweaker.api.recipes;

import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;

/**
 * @author Stan
 */
public class CraftingInfo implements ICraftingInfo {
    
    private final ICraftingInventory inventory;
    private final IWorld world;
    
    public CraftingInfo(ICraftingInventory inventory, IWorld world) {
        this.inventory = inventory;
        this.world = world;
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
    public int getDimension() {
        return world.getDimension();
    }
    
    @Override
    public IWorld getWorld() {
        return null;
    }
}
