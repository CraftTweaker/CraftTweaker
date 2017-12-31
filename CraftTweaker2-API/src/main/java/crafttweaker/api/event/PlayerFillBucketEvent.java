package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockGroup;
import crafttweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerFillBucketEvent")
@ZenRegister
public class PlayerFillBucketEvent {

    private final IPlayer player;
    private final IBlockGroup blocks;
    private final int x;
    private final int y;
    private final int z;
    private boolean canceled;
    private IItemStack result;

    public PlayerFillBucketEvent(IPlayer player, IBlockGroup blocks, int x, int y, int z) {
        this.player = player;
        this.blocks = blocks;
        this.x = x;
        this.y = y;
        this.z = z;

        canceled = false;
        result = null;
    }

    @ZenMethod
    public void cancel() {
        canceled = true;
    }

    @ZenGetter("canceled")
    public boolean isCanceled() {
        return canceled;
    }

    @ZenGetter("result")
    public IItemStack getResult() {
        return result;
    }

    /**
     * Sets the resulting item. Automatically processes the event, too.
     *
     * @param result resulting item
     */
    @ZenSetter("result")
    public void setResult(IItemStack result) {
        this.result = result;
    }

    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }

    @ZenGetter("blocks")
    public IBlockGroup getBlocks() {
        return blocks;
    }

    @ZenGetter("x")
    public int getX() {
        return x;
    }

    @ZenGetter("y")
    public int getY() {
        return y;
    }

    @ZenGetter("z")
    public int getZ() {
        return z;
    }

    @ZenGetter("block")
    public IBlock getBlock() {
        return blocks.getBlock(x, y, z);
    }

    @ZenGetter("dimension")
    public IDimension getDimension() {
        return blocks.getDimension();
    }
}
