package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("crafttweaker.event.LivingEntityUseItemEvent")
public interface LivingEntityUseItemEvent extends IEventCancelable, ILivingEvent{
    
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
    @ZenClass("crafttweaker.event.LivingEntityUseItemStartEvent")
    interface LivingEntityUseItemStartEvent extends LivingEntityUseItemEvent {}
    
    @ZenRegister
    @ZenClass("crafttweaker.event.LivingEntityUseItemStopEvent")
    interface LivingEntityUseItemStopEvent extends LivingEntityUseItemEvent {}
    
    @ZenRegister
    @ZenClass("crafttweaker.event.LivingEntityUseItemFinishEvent")
    interface LivingEntityUseItemFinishEvent extends LivingEntityUseItemEvent {}
    
    @ZenRegister
    @ZenClass("crafttweaker.event.LivingEntityUseItemTickEvent")
    interface LivingEntityUseItemTickEvent extends LivingEntityUseItemEvent {}
}