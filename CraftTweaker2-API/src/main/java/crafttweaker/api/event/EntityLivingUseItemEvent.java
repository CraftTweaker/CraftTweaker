package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("crafttweaker.event.EntityLivingUseItemEvent")
public interface EntityLivingUseItemEvent extends IEventCancelable, ILivingEvent{
    
    @ZenGetter("item")
    IItemStack getItem();
    
    @ZenGetter
    boolean isPlayer();
    
    @ZenGetter("player")
    IPlayer getPlayer();
    
    @ZenGetter("duration")
    int getDuration();
    
    @ZenSetter("duration")
    void setDuration(int duration);
    
    
    // ######################
    // ### SubInterfaces  ###
    // ######################
    
    @ZenRegister
    @ZenClass("crafttweaker.event.EntityLivingUseItemEvent.Start")
    interface Start extends EntityLivingUseItemEvent {}
    
    @ZenRegister
    @ZenClass("crafttweaker.event.EntityLivingUseItemEvent.Stop")
    interface Stop extends EntityLivingUseItemEvent {}
    
    @ZenRegister
    @ZenClass("crafttweaker.event.EntityLivingUseItemEvent.Finish")
    interface Finish extends EntityLivingUseItemEvent {}
    
    @ZenRegister
    @ZenClass("crafttweaker.event.EntityLivingUseItemEvent.Tick")
    interface Tick extends EntityLivingUseItemEvent {}
}