package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public class MCLivingEntityUseItemEvent implements crafttweaker.api.event.LivingEntityUseItemEvent {
    
    private final LivingEntityUseItemEvent event;
    
    public MCLivingEntityUseItemEvent(LivingEntityUseItemEvent event) {
        this.event = event;
    }
    
    @Override
    public IItemStack getItem() {
        return CraftTweakerMC.getIItemStack(event.getItem());
    }
    
    @Override
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }
    
    @Override
    public boolean isPlayer() {
        return event.getEntityLiving() instanceof EntityPlayer;
    }
    
    @Override
    public IPlayer getPlayer() {
        return event.getEntityLiving() instanceof EntityPlayer ? CraftTweakerMC.getIPlayer((EntityPlayer) event.getEntityLiving()) : null;
    }
    
    @Override
    public int getDuration() {
        return event.getDuration();
    }
    
    @Override
    public void setDuration(int duration) {
        event.setDuration(duration);
    }
    
    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }
    
    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
    
    
    // ######################
    // ### Actual classes ###
    // ######################
    
    public static class MCEntityUseItemStartEvent extends MCLivingEntityUseItemEvent implements crafttweaker.api.event.LivingEntityUseItemEvent.LivingEntityUseItemStartEvent {
        public MCEntityUseItemStartEvent(LivingEntityUseItemEvent.Start event) {
            super(event);
        }
    }
    
    public static class MCEntityUseItemStopEvent extends MCLivingEntityUseItemEvent implements crafttweaker.api.event.LivingEntityUseItemEvent.LivingEntityUseItemStopEvent {
        public MCEntityUseItemStopEvent(LivingEntityUseItemEvent.Stop event) {
            super(event);
        }
    }
    
    public static class MCEntityUseItemTickEvent extends MCLivingEntityUseItemEvent implements crafttweaker.api.event.LivingEntityUseItemEvent.LivingEntityUseItemTickEvent {
        public MCEntityUseItemTickEvent(LivingEntityUseItemEvent.Tick event) {
            super(event);
        }
    }
    
    public static class MCEntityUseItemFinishEvent extends MCLivingEntityUseItemEvent implements crafttweaker.api.event.LivingEntityUseItemEvent.LivingEntityUseItemFinishEvent {
        public MCEntityUseItemFinishEvent(LivingEntityUseItemEvent.Finish event) {
            super(event);
        }
    }
}