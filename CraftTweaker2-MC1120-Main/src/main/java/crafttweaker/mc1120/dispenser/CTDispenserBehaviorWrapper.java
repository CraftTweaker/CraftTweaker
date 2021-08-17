package crafttweaker.mc1120.dispenser;

import crafttweaker.api.dispenser.DispenserSound;
import crafttweaker.api.dispenser.IDispenserBehavior;
import crafttweaker.api.dispenser.IDispenserSoundFunction;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

/**
 * @author youyihj
 */
public class CTDispenserBehaviorWrapper extends BehaviorDefaultDispenseItem {
    private final IDispenserBehavior dispenserBehavior;
    private final IDispenserSoundFunction dispenserSoundFunction;

    public CTDispenserBehaviorWrapper(IDispenserBehavior dispenserBehavior, IDispenserSoundFunction dispenserSoundFunction) {
        this.dispenserBehavior = dispenserBehavior;
        this.dispenserSoundFunction = dispenserSoundFunction;
    }

    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        return CraftTweakerMC.getItemStack(dispenserBehavior.apply(CraftTweakerMC.getBlockSource(source), CraftTweakerMC.getIItemStack(stack)).mutable());
    }

    @Override
    protected void playDispenseSound(IBlockSource source) {
        DispenserSound sound = dispenserSoundFunction == null ? DispenserSound.DISPENSE : dispenserSoundFunction.apply(CraftTweakerMC.getBlockSource(source));
        source.getWorld().playEvent(sound.getSoundType(), source.getBlockPos(), 0);
    }
}
