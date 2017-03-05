package minetweaker.api.block;

import stanhebben.zenscript.annotations.*;

/**
 * Blocks definitions provide additional information about blocks.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.block.IBlockDefinition")
public interface IBlockDefinition {

    @ZenGetter("id")
    String getId();

    @ZenGetter("displayName")
    String getDisplayName();

}
