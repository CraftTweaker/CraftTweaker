package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerAnvilRepairEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

public class MCPlayerAnvilRepairEvent implements PlayerAnvilRepairEvent {

    private final AnvilRepairEvent event;

    public MCPlayerAnvilRepairEvent(AnvilRepairEvent event) {
        this.event = event;
    }

    @Override
    public IItemStack getItemResult() {
        return CraftTweakerMC.getIItemStack(event.getItemResult());
    }

    @Override
    public IItemStack getItemIngredient() {
        return CraftTweakerMC.getIItemStack(event.getIngredientInput());
    }

    @Override
    public IItemStack getItemInput() {
        return CraftTweakerMC.getIItemStack(event.getItemInput());
    }

    @Override
    public float getBreakChance() {
        return event.getBreakChance();
    }

    @Override
    public void setBreakChance(float breakChance) {
        event.setBreakChance(breakChance);
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
