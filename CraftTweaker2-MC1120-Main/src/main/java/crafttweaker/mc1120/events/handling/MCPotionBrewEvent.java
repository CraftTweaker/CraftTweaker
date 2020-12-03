package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.IPotionBrewEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.brewing.PotionBrewEvent;

public class MCPotionBrewEvent implements IPotionBrewEvent {
    private final PotionBrewEvent event;

    public MCPotionBrewEvent(PotionBrewEvent event) {
        this.event = event;
    }

    @Override
    public int getLength() {
        return event.getLength();
    }

    @Override
    public IItemStack getItem(int index) {
        return CraftTweakerMC.getIItemStack(event.getItem(index));
    }

    @Override
    public void setItem(int index, IItemStack stack) {
        event.setItem(index, CraftTweakerMC.getItemStack(stack));
    }
}
