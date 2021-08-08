package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.BlockDispenser;

/**
 * @author youyihj
 */
public class ActionRemoveDispenserBehavior implements IAction {
    private final IItemDefinition item;

    public ActionRemoveDispenserBehavior(IItemDefinition item) {
        this.item = item;
    }

    @Override
    public void apply() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.registryObjects.remove(CraftTweakerMC.getItem(item));
    }

    @Override
    public String describe() {
        return "Removing dispenser behavior for item: " + item.getId();
    }
}
