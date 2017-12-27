package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * 37 kinds of event handlers!
 * <p>
 * NOTE: not all of these are implemented yet, but will be later on.
 *
 * @author Stan
 */
@ZenClass("crafttweaker.events.IEventManager")
@ZenRegister
public interface IEventManager {
    
	@ZenMethod
	IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev);
	
    // implemented
	@ZenMethod
    IEventHandle onPlayerCrafted(IEventHandler<PlayerCraftedEvent> ev);
    
    // implemented
	@ZenMethod
    IEventHandle onPlayerSmelted(IEventHandler<PlayerSmeltedEvent> ev);
    
    IEventHandle onPlayerChangedDimension(IEventHandler<PlayerChangedDimensionEvent> ev);
    
    IEventHandle onPlayerRespawn(IEventHandler<PlayerRespawnEvent> ev);
    
    IEventHandle onPlayerAttackEntity(IEventHandler<PlayerAttackEntityEvent> ev);
    
    IEventHandle onPlayerBonemeal(IEventHandler<PlayerBonemealEvent> ev);
    
    IEventHandle onPlayerInteractEntity(IEventHandler<PlayerInteractEntityEvent> ev);
    
    IEventHandle onPlayerPickup(IEventHandler<PlayerPickupEvent> ev);
    
    IEventHandle onPlayerPickupItem(IEventHandler<PlayerPickupItemEvent> ev);
    
    IEventHandle onPlayerFillBucket(IEventHandler<PlayerFillBucketEvent> ev);
    
    IEventHandle onPlayerDeathDrops(IEventHandler<PlayerDeathDropsEvent> ev);
    
    IEventHandle onPlayerInteract(IEventHandler<PlayerInteractEvent> ev);
    
    IEventHandle onPlayerOpenContainer(IEventHandler<PlayerOpenContainerEvent> ev);
    
    IEventHandle onPlayerPickupXp(IEventHandler<PlayerPickupXpEvent> ev);
    
    IEventHandle onPlayerSleepInBed(IEventHandler<PlayerSleepInBedEvent> ev);
    
    IEventHandle onPlayerUseHoe(IEventHandler<PlayerUseHoeEvent> ev);
    
    IEventHandle onPlayerUseItemStart(IEventHandler<PlayerUseItemStartEvent> ev);
    
    IEventHandle onPlayerUseItemTick(IEventHandler<PlayerUseItemTickEvent> ev);

	/*
     * IEventHandle
	 * onPlayerUseItemStop(IEventHandler<PlayerUseItemStopEvent> ev);
	 * 
	 * IEventHandle
	 * onPlayerUseItemFinish(IPlayerUserItemFinishEventHandler ev);
	 * 
	 * IEventHandle onPlayerChat(IPlayerChatEventHandler ev);
	 * 
	 * IEventHandle onTimerSingle(int millis, ITimerEventHandler ev);
	 * 
	 * IEventHandle onTimerRepeat(int millis, ITimerEventHandler ev);
	 * 
	 * IEventHandle onEntityJoinWorld(IEntityJoinWorldEventHandler ev);
	 * 
	 * IEventHandle
	 * onEntityStruckByLightning(IEntityStruckByLightningEventHandler ev);
	 * 
	 * IEventHandle
	 * onLivingEnderTeleport(ILivingEnderTeleportEventHandler ev);
	 * 
	 * IEventHandle onLivingAttackEvent(ILivingAttackEventHandler ev);
	 * 
	 * IEventHandle onLivingDeathEvent(ILivingDeathEventHandler ev);
	 * 
	 * IEventHandle onLivingJumpEvent(ILivingJumpEventHandler ev);
	 * 
	 * IEventHandle onLivingFallEvent(ILivingFallEventHandler ev);
	 * 
	 * IEventHandle onLivingHurtEvent(ILivingHurtEventHandler ev);
	 * 
	 * IEventHandle onLivingDeathDropsEvent(ILivingDeathDropsEventHandler
	 * ev);
	 * 
	 * IEventHandle onItemTossed(IItemTossedEventHandler ev);
	 * 
	 * IEventHandle onItemExpired(IItemExpiredEventHandler ev);
	 * 
	 * IEventHandle onMinecartCollision(IMinecartCollisionEventHandler
	 * ev);
	 * 
	 * IEventHandle onMinecartInteract(IMinecartInteractEventHandler ev);
	 * 
	 * IEventHandle onCommand(ICommandEventHandler ev);
	 */
}
