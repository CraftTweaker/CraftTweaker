package minetweaker.api.event;

/**
 * 37 kinds of event handlers!
 * 
 * @author Stan
 */
public interface IEventManager {
	public IEventHandle onPlayerCrafted(IPlayerCraftedEventHandler ev);
	
	public IEventHandle onPlayerSmelted(IPlayerSmeltedEventHandler ev);
	
	public IEventHandle onPlayerChangedDimension(IPlayerChangedDimensionEventHandler ev);
	
	public IEventHandle onPlayerRespawn(IPlayerRespawnEventHandler ev);
	
	public IEventHandle onPlayerAttackEntity(IPlayerAttackEntityEventHandler ev);
	
	public IEventHandle onPlayerBonemeal(IPlayerBonemealEventHandler ev);
	
	public IEventHandle onPlayerInteractEntity(IPlayerInteractEntityEventHandler ev);
	
	public IEventHandle onPlayerPickup(IPlayerPickupEventHandler ev);
	
	public IEventHandle onPlayerPickupItem(IPlayerPickupItemEventHandler ev);
	
	public IEventHandle onPlayerFillBucket(IPlayerFillBucketEventHandler ev);
	
	public IEventHandle onPlayerDeathDrops(IPlayerDeathDropsEventHandler ev);
	
	public IEventHandle onPlayerInteract(IPlayerInteractEventHandler ev);
	
	public IEventHandle onPlayerOpenContainer(IPlayerOpenContainerEventHandler ev);
	
	public IEventHandle onPlayerPickupXp(IPlayerPickupXpEventHandler ev);
	
	public IEventHandle onPlayerSleepInBed(IPlayerSleepInBedEventHandler ev);
	
	public IEventHandle onPlayerUseHoe(IPlayerUseHoeEventHandler ev);
	
	public IEventHandle onPlayerUseItemStart(IPlayerUseItemStartEventHandler ev);
	
	public IEventHandle onPlayerUseItemTick(IPlayerUseItemTickEventHandler ev);
	
	/*public IEventHandle onPlayerUseItemStop(IPlayerUseItemStopEventHandler ev);
	
	public IEventHandle onPlayerUseItemFinish(IPlayerUserItemFinishEventHandler ev);
	
	public IEventHandle onPlayerChat(IPlayerChatEventHandler ev);
	
	public IEventHandle onTimerSingle(int millis, ITimerEventHandler ev);
	
	public IEventHandle onTimerRepeat(int millis, ITimerEventHandler ev);
	
	public IEventHandle onEntityJoinWorld(IEntityJoinWorldEventHandler ev);
	
	public IEventHandle onEntityStruckByLightning(IEntityStruckByLightningEventHandler ev);
	
	public IEventHandle onLivingEnderTeleport(ILivingEnderTeleportEventHandler ev);
	
	public IEventHandle onLivingAttackEvent(ILivingAttackEventHandler ev);
	
	public IEventHandle onLivingDeathEvent(ILivingDeathEventHandler ev);
	
	public IEventHandle onLivingJumpEvent(ILivingJumpEventHandler ev);
	
	public IEventHandle onLivingFallEvent(ILivingFallEventHandler ev);
	
	public IEventHandle onLivingHurtEvent(ILivingHurtEventHandler ev);
	
	public IEventHandle onLivingDeathDropsEvent(ILivingDeathDropsEventHandler ev);
	
	public IEventHandle onItemTossed(IItemTossedEventHandler ev);
	
	public IEventHandle onItemExpired(IItemExpiredEventHandler ev);
	
	public IEventHandle onMinecartCollision(IMinecartCollisionEventHandler ev);
	
	public IEventHandle onMinecartInteract(IMinecartInteractEventHandler ev);
	
	public IEventHandle onCommand(ICommandEventHandler ev);*/
}
