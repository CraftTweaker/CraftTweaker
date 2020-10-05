package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.PlayerInteractEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;

/**
 * @author Stan
 */
public class MCPlayerInteractEvent implements PlayerInteractEvent {

    private final net.minecraftforge.event.entity.player.PlayerInteractEvent event;

    public MCPlayerInteractEvent(net.minecraftforge.event.entity.player.PlayerInteractEvent event) {
        this.event = event;
    }

    @Override
    public void damageItem(int amount) {
        event.getItemStack().damageItem(amount, event.getEntityPlayer());
    }

    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }

    @Override
    public IBlock getBlock() {
        return getWorld().getBlock(getPosition());
    }

    @Override
    public IBlockState getBlockState() {
        return CraftTweakerMC.getBlockState(event.getWorld().getBlockState(event.getPos()));
    }

    @Override
    public int getDimension() {
        return getWorld().getDimension();
    }

    @Override
    public String getHand() {
        return String.valueOf(event.getHand());
    }

    @Override
    public IItemStack getUsedItem() {
        return CraftTweakerMC.getIItemStack(event.getItemStack());
    }

    @Override
    public IBlockPos getPosition() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
