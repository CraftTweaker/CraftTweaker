package crafttweaker.mc1120.events.handling;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.event.ItemFishedEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCItemFishedEvent implements ItemFishedEvent {
    private net.minecraftforge.event.entity.player.ItemFishedEvent event;
    private IItemStack[] drops;

    public MCItemFishedEvent(net.minecraftforge.event.entity.player.ItemFishedEvent event) {
        this.event = event;
        this.drops = event.getDrops().stream().map(CraftTweakerMC::getIItemStack).toArray(IItemStack[]::new);
    }

    @Override
    public int getRodDamage() {
        return event.getRodDamage();
    }

    @Override
    public void additionalDamage(int damage) {
        if (damage < 0) {
            CraftTweakerAPI.logError("ItemFishedEvent: additional damage applied must be non-negative");
        } else {
            event.damageRodBy(damage);
        }
    }

    @Override
    public IItemStack[] getDrops() {
        return this.drops;
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
