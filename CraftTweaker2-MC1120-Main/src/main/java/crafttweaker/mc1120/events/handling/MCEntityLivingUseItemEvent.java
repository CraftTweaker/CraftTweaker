package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingUseItemEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public class MCEntityLivingUseItemEvent implements EntityLivingUseItemEvent {
    
    private final LivingEntityUseItemEvent event;
    
    public MCEntityLivingUseItemEvent(LivingEntityUseItemEvent event) {
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
    
    public static class Start extends MCEntityLivingUseItemEvent implements EntityLivingUseItemEvent.Start {
        public Start(LivingEntityUseItemEvent.Start event) {
            super(event);
        }
    }
    
    public static class Stop extends MCEntityLivingUseItemEvent implements EntityLivingUseItemEvent.Stop {
        public Stop(LivingEntityUseItemEvent.Stop event) {
            super(event);
        }
    }
    
    public static class Tick extends MCEntityLivingUseItemEvent implements EntityLivingUseItemEvent.Tick {
        public Tick(LivingEntityUseItemEvent.Tick event) {
            super(event);
        }
    }
    
    public static class Finish extends MCEntityLivingUseItemEvent implements EntityLivingUseItemEvent.Finish {
        public Finish(LivingEntityUseItemEvent.Finish event) {
            super(event);
        }
    }
}