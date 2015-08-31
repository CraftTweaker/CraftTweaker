package minetweaker.api.event;

import minetweaker.util.IEventHandler;

/**
 * 37 kinds of event handlers!
 * 
 * NOTE: not all of these are implemented yet, but will be later on.
 * 
 * @author Stan
 */
public interface IEventManager {
	// implemented
	public IEventHandle onPlayerCrafted(IEventHandler<PlayerCraftedEvent> ev);

	// implemented
	public IEventHandle onPlayerSmelted(IEventHandler<PlayerSmeltedEvent> ev);

	public IEventHandle onPlayerChangedDimension(IEventHandler<PlayerChangedDimensionEvent> ev);

	public IEventHandle onPlayerRespawn(IEventHandler<PlayerRespawnEvent> ev);

	public IEventHandle onPlayerAttackEntity(IEventHandler<PlayerAttackEntityEvent> ev);

	public IEventHandle onPlayerBonemeal(IEventHandler<PlayerBonemealEvent> ev);

	public IEventHandle onPlayerInteractEntity(IEventHandler<PlayerInteractEntityEvent> ev);

	public IEventHandle onPlayerPickup(IEventHandler<PlayerPickupEvent> ev);

	public IEventHandle onPlayerPickupItem(IEventHandler<PlayerPickupItemEvent> ev);

	public IEventHandle onPlayerFillBucket(IEventHandler<PlayerFillBucketEvent> ev);

	public IEventHandle onPlayerDeathDrops(IEventHandler<PlayerDeathDropsEvent> ev);

	public IEventHandle onPlayerInteract(IEventHandler<PlayerInteractEvent> ev);

	public IEventHandle onPlayerOpenContainer(IEventHandler<PlayerOpenContainerEvent> ev);

	public IEventHandle onPlayerPickupXp(IEventHandler<PlayerPickupXpEvent> ev);

	public IEventHandle onPlayerSleepInBed(IEventHandler<PlayerSleepInBedEvent> ev);

	public IEventHandle onPlayerUseHoe(IEventHandler<PlayerUseHoeEvent> ev);

	public IEventHandle onPlayerUseItemStart(IEventHandler<PlayerUseItemStartEvent> ev);

	public IEventHandle onPlayerUseItemTick(IEventHandler<PlayerUseItemTickEvent> ev);

	/*
	 * public IEventHandle
	 * onPlayerUseItemStop(IEventHandler<PlayerUseItemStopEvent> ev);
	 * 
	 * public IEventHandle
	 * onPlayerUseItemFinish(IPlayerUserItemFinishEventHandler ev);
	 * 
	 * public IEventHandle onPlayerChat(IPlayerChatEventHandler ev);
	 * 
	 * public IEventHandle onTimerSingle(int millis, ITimerEventHandler ev);
	 * 
	 * public IEventHandle onTimerRepeat(int millis, ITimerEventHandler ev);
	 * 
	 * public IEventHandle onEntityJoinWorld(IEntityJoinWorldEventHandler ev);
	 * 
	 * public IEventHandle
	 * onEntityStruckByLightning(IEntityStruckByLightningEventHandler ev);
	 * 
	 * public IEventHandle
	 * onLivingEnderTeleport(ILivingEnderTeleportEventHandler ev);
	 * 
	 * public IEventHandle onLivingAttackEvent(ILivingAttackEventHandler ev);
	 * 
	 * public IEventHandle onLivingDeathEvent(ILivingDeathEventHandler ev);
	 * 
	 * public IEventHandle onLivingJumpEvent(ILivingJumpEventHandler ev);
	 * 
	 * public IEventHandle onLivingFallEvent(ILivingFallEventHandler ev);
	 * 
	 * public IEventHandle onLivingHurtEvent(ILivingHurtEventHandler ev);
	 * 
	 * public IEventHandle onLivingDeathDropsEvent(ILivingDeathDropsEventHandler
	 * ev);
	 * 
	 * public IEventHandle onItemTossed(IItemTossedEventHandler ev);
	 * 
	 * public IEventHandle onItemExpired(IItemExpiredEventHandler ev);
	 * 
	 * public IEventHandle onMinecartCollision(IMinecartCollisionEventHandler
	 * ev);
	 * 
	 * public IEventHandle onMinecartInteract(IMinecartInteractEventHandler ev);
	 * 
	 * public IEventHandle onCommand(ICommandEventHandler ev);
	 */
}
