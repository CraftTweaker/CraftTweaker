package crafttweaker.api.block;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.liquid.ILiquidDefinition;
import stanhebben.zenscript.annotations.*;

/**
 * Block interface. Used to interact with blocks in the world.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.block.IBlock")
@ZenRegister
public interface IBlock extends IBlockPattern {
    
    /**
     * Gets the block definition.
     *
     * @return block definition
     */
    @ZenGetter("definition")
    IBlockDefinition getDefinition();
    
    @ZenGetter("meta")
    int getMeta();
    
    @ZenGetter("data")
    IData getTileData();

    @ZenGetter("fluid")
    ILiquidDefinition getFluid();

    default IData getTileDataInternal() {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + " doesn't override IBlock::getTileDataInternal! It is a bug!");
        return null;
    }
}
