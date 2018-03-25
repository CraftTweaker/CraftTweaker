package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerBrewedPotionEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCPlayerBrewedPotionEvent implements PlayerBrewedPotionEvent {
    
    private final net.minecraftforge.event.brewing.PlayerBrewedPotionEvent event;
    
    public MCPlayerBrewedPotionEvent(net.minecraftforge.event.brewing.PlayerBrewedPotionEvent event) {
        this.event = event;
    }
    
    @Override
    public IItemStack getPotion() {
        return CraftTweakerMC.getIItemStack(event.getStack());
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
