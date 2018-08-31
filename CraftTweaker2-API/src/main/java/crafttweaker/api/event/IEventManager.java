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
    void clear();

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
    IEventHandle onEntityLivingUseItem(IEventHandler<EntityLivingUseItemEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingUseItemStart(IEventHandler<EntityLivingUseItemEvent.Start> ev);

    @ZenMethod
    IEventHandle onEntityLivingUseItemStop(IEventHandler<EntityLivingUseItemEvent.Stop> ev);

    @ZenMethod
    IEventHandle onEntityLivingUseItemTick(IEventHandler<EntityLivingUseItemEvent.Tick> ev);

    @ZenMethod
    IEventHandle onEntityLivingUseItemFinish(IEventHandler<EntityLivingUseItemEvent.Finish> ev);

    @ZenMethod
    IEventHandle onEntityStruckByLightning(IEventHandler<EntityStruckByLightningEvent> ev);

    @ZenMethod
    IEventHandle onEnderTeleport(IEventHandler<EnderTeleportEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingAttacked(IEventHandler<EntityLivingAttackedEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingDeath(IEventHandler<EntityLivingDeathEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingFall(IEventHandler<EntityLivingFallEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingHurt(IEventHandler<EntityLivingHurtEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingJump(IEventHandler<EntityLivingJumpEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingDeathDrops(IEventHandler<EntityLivingDeathDropsEvent> ev);

    @ZenMethod
    IEventHandle onItemExpire(IEventHandler<ItemExpireEvent> ev);

    @ZenMethod
    IEventHandle onItemToss(IEventHandler<ItemTossEvent> ev);

    @ZenMethod
    IEventHandle onPlayerAnvilRepair(IEventHandler<PlayerAnvilRepairEvent> ev);

    @ZenMethod
    IEventHandle onPlayerSetSpawn(IEventHandler<PlayerSetSpawnEvent> ev);

    @ZenMethod
    IEventHandle onPlayerDestroyItem(IEventHandler<PlayerDestroyItemEvent> ev);

    @ZenMethod
    IEventHandle onPlayerBrewedPotion(IEventHandler<PlayerBrewedPotionEvent> ev);

    @ZenMethod
    IEventHandle onPlayerTick(IEventHandler<PlayerTickEvent> ev);

    @ZenMethod
    IEventHandle onBlockBreak(IEventHandler<BlockBreakEvent> ev);

    @ZenMethod
    IEventHandle onBlockHarvestDrops(IEventHandler<BlockHarvestDropsEvent> ev);

    @ZenMethod
    IEventHandle onPlayerBreakSpeed(IEventHandler<PlayerBreakSpeedEvent> ev);

    @ZenMethod
    IEventHandle onPlayerRightClickBlock(IEventHandler<PlayerRightClickBlockEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerInteractBlock(IEventHandler<PlayerRightClickBlockEvent> ev);

    @ZenMethod
    IEventHandle onCommand(IEventHandler<CommandEvent> ev);

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
     * IEventHandle onMinecartCollision(IMinecartCollisionEventHandler ev);
     *
     * IEventHandle onMinecartInteract(IMinecartInteractEventHandler ev);
     *
     */
}
