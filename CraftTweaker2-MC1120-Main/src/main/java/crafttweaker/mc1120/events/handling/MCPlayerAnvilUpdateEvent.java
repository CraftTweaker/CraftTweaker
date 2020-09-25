package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerAnvilUpdateEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCPlayerAnvilUpdateEvent implements PlayerAnvilUpdateEvent {
    
    private final AnvilUpdateEvent event;
    
    public MCPlayerAnvilUpdateEvent(AnvilUpdateEvent event) {
        this.event = event;
    }
    
    @Override
    public IItemStack getLeftItem() {
        return CraftTweakerMC.getIItemStack(event.getLeft());
    }
    
    @Override
    public IItemStack getRightItem() {
        return CraftTweakerMC.getIItemStack(event.getRight());
    }
    
    @Override
    public IItemStack getOutputItem() {
        return CraftTweakerMC.getIItemStack(event.getOutput());
    }
    
    @Override
    public void setOutputItem(IItemStack output) {
        event.setOutput(CraftTweakerMC.getItemStack(output));
    }
    
    @Override
    public String getItemName() {
        return event.getName();
    }
    
    @Override
    public int getXPCost() {
        return event.getCost();
    }
    
    @Override
    public void setXPCost(int xp) {
        event.setCost(xp);
    }
    
    @Override
    public int getMaterialCost() {
        return event.getMaterialCost();
    }
    
    @Override
    public void setMaterialCost(int materialCost) {
        event.setMaterialCost(materialCost);
    }
    
    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}
