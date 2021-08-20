package crafttweaker.api.dispenser;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.dispenser.IDispenserSoundFunction")
public interface IDispenserSoundFunction {
    DispenserSound apply(IBlockSource source);
}
