package minetweaker.mc1102.block;

import minetweaker.api.block.IBlockDefinition;
import net.minecraft.block.Block;

/**
 * @author Stan
 */
public class MCBlockDefinition implements IBlockDefinition {

    private final Block block;

    public MCBlockDefinition(Block block) {
        this.block = block;
    }

    public Block getInternalBlock() {
        return block;
    }

    @Override
    public String getId() {
        return Block.REGISTRY.getNameForObject(block).toString();
    }

    @Override
    public String getDisplayName() {
        return block.getLocalizedName();
    }
}
