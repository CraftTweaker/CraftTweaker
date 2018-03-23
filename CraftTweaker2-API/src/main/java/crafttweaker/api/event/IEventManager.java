package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.*;

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
    IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerCrafted(IEventHandler<PlayerCraftedEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerSmelted(IEventHandler<PlayerSmeltedEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerChangedDimension(IEventHandler<PlayerChangedDimensionEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerRespawn(IEventHandler<PlayerRespawnEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerAttackEntity(IEventHandler<PlayerAttackEntityEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerBonemeal(IEventHandler<PlayerBonemealEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerInteractEntity(IEventHandler<PlayerInteractEntityEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerPickupItem(IEventHandler<PlayerPickupItemEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerFillBucket(IEventHandler<PlayerFillBucketEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerDeathDrops(IEventHandler<PlayerDeathDropsEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerInteract(IEventHandler<PlayerInteractEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerOpenContainer(IEventHandler<PlayerOpenContainerEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerPickupXp(IEventHandler<PlayerPickupXpEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerSleepInBed(IEventHandler<PlayerSleepInBedEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerUseHoe(IEventHandler<PlayerUseHoeEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityLivingUseItemStart(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemStartEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityLivingUseItemStop(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemStopEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityLivingUseItemTick(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemTickEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityLivingUseItemFinish(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemFinishEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityStruckByLightning(IEventHandler<EntityStruckByLightningEvent> ev);
    
    @ZenMethod
    IEventHandle onEnderTeleport(IEventHandler<EnderTeleportEvent> ev);
    
    /*
     *
     * IEventHandle onPlayerChat(IPlayerChatEventHandler ev);
     *
     * IEventHandle onTimerSingle(int millis, ITimerEventHandler ev);
     *
     * IEventHandle onTimerRepeat(int millis, ITimerEventHandler ev);
     *
     * IEventHandle onEntityJoinWorld(IEntityJoinWorldEventHandler ev);
     *
     *
     *
     * IEventHandle onLivingEnderTeleport(ILivingEnderTeleportEventHandler ev);
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
