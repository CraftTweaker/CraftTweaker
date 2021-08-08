package crafttweaker.mc1120.dispenser;

import crafttweaker.api.dispenser.IDispenserBehavior;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

/**
 * @author youyihj
 */
public class CTDispenserBehaviorWrapper implements IBehaviorDispenseItem {
    private final IDispenserBehavior dispenserBehavior;

    public CTDispenserBehaviorWrapper(IDispenserBehavior dispenserBehavior) {
        this.dispenserBehavior = dispenserBehavior;
    }

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        return CraftTweakerMC.getItemStack(dispenserBehavior.apply(CraftTweakerMC.getBlockSource(source), CraftTweakerMC.getIItemStack(stack)));
    }
}
