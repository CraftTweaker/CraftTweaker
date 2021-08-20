package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.api.dispenser.IDispenserBehavior;
import crafttweaker.api.dispenser.IDispenserSoundFunction;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.dispenser.CTDispenserBehaviorWrapper;
import net.minecraft.block.BlockDispenser;

/**
 * @author youyihj
 */
public class ActionAddDispenserBehavior implements IAction {
    private final IItemDefinition item;
    private final IDispenserBehavior behavior;
    private final IDispenserSoundFunction soundFunction;

    public ActionAddDispenserBehavior(IItemDefinition item, IDispenserBehavior behavior, IDispenserSoundFunction soundFunction) {
        this.item = item;
        this.behavior = behavior;
        this.soundFunction = soundFunction;
    }

    @Override
    public void apply() {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CraftTweakerMC.getItem(item), new CTDispenserBehaviorWrapper(behavior, soundFunction));
    }

    @Override
    public String describe() {
        return "Adding a dispenser behavior for item: " + item.getId();
    }
}
