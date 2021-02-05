package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.event.BlockHarvestDropsEvent;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MCBlockHarvestDropsEvent extends MCBlockEvent implements BlockHarvestDropsEvent {

    private final BlockEvent.HarvestDropsEvent event;

    public MCBlockHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public float getDropChance() {
        return event.getDropChance();
    }

    @Override
    public void setDropChance(float dropChance) {
        event.setDropChance(dropChance);
    }

    @Override
    public int getFortuneLevel() {
        return event.getFortuneLevel();
    }

    @Override
    public IBlock getBlock() {
        return getBlockState().getBlock();
    }

    @Override
    public List<WeightedItemStack> getDrops() {
        return CraftTweakerMC.getWeightedItemStackList(event.getDrops());
    }

    @Override
    public void setDrops(List<WeightedItemStack> drops) {
        event.getDrops().clear();
        for (WeightedItemStack drop : drops) {
            addItem(drop);
        }
    }

    @Override
    public void addItem(WeightedItemStack itemStack) {
        if (event.getWorld().rand.nextFloat() <= itemStack.getChance())
            event.getDrops().add(CraftTweakerMC.getItemStack(itemStack.getStack()));
    }

    @Override
    public boolean isSilkTouch() {
        return event.isSilkTouching();
    }

    @Override
    public boolean isPlayer() {
        return event.getHarvester() instanceof EntityPlayerMP || event.getHarvester() instanceof EntityPlayerSP;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getHarvester());
    }
}
