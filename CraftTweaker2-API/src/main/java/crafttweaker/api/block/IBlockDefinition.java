package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * Blocks definitions provide additional information about blocks.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.block.IBlockDefinition")
@ZenRegister
public interface IBlockDefinition {
    
    @ZenGetter("id")
    String getId();
    
    @ZenGetter("displayName")
    String getDisplayName();
    
}
