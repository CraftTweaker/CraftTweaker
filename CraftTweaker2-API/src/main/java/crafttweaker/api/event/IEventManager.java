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
    IEventHandle onPlayerAdvancement(IEventHandler<PlayerAdvancementEvent> ev);

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
    IEventHandle onPlayerWakeUp(IEventHandler<PlayerWakeUpEvent> ev);

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
    IEventHandle onPlayerAnvilUpdate(IEventHandler<PlayerAnvilUpdateEvent> ev);

    @ZenMethod
    IEventHandle onPlayerSetSpawn(IEventHandler<PlayerSetSpawnEvent> ev);

    @ZenMethod
    IEventHandle onPlayerDestroyItem(IEventHandler<PlayerDestroyItemEvent> ev);

    @ZenMethod
    IEventHandle onPlayerBrewedPotion(IEventHandler<PlayerBrewedPotionEvent> ev);

    @ZenMethod
    IEventHandle onPlayerTick(IEventHandler<PlayerTickEvent> ev);

    @ZenMethod
    IEventHandle onClientTick(IEventHandler<ClientTickEvent> ev);

    @ZenMethod
    IEventHandle onServerTick(IEventHandler<ServerTickEvent> ev);

    @ZenMethod
    IEventHandle onRenderTick(IEventHandler<RenderTickEvent> ev);

    @ZenMethod
    IEventHandle onWorldTick(IEventHandler<WorldTickEvent> ev);

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

    @ZenMethod
    IEventHandle onCheckSpawn(IEventHandler<EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent> ev);

    @ZenMethod
    IEventHandle onSpecialSpawn(IEventHandler<EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent> ev);

    @ZenMethod
    IEventHandle onAllowDespawn(IEventHandler<EntityLivingSpawnEvent> ev);

    @ZenMethod
    IEventHandle onAnimalTame(IEventHandler<AnimalTameEvent> ev);

    @ZenMethod
    IEventHandle onFarmlandTrample(IEventHandler<BlockFarmlandTrampleEvent> ev);

    @ZenMethod
    IEventHandle onCriticalHit(IEventHandler<CriticalHitEvent> ev);

    @ZenMethod
    IEventHandle onEnchantmentLevelSet(IEventHandler<EnchantmentLevelSetEvent> ev);

    @ZenMethod
    IEventHandle onEntityMount(IEventHandler<EntityMountEvent> ev);

    @ZenMethod
    IEventHandle onExplosionStart(IEventHandler<ExplosionStartEvent> ev);

    @ZenMethod
    IEventHandle onExplosionDetonate(IEventHandler<ExplosionDetonateEvent> ev);

    @ZenMethod
    IEventHandle onItemFished(IEventHandler<ItemFishedEvent> ev);

    @ZenMethod
    IEventHandle onCropGrowPre(IEventHandler<CropGrowPreEvent> ev);

    @ZenMethod
    IEventHandle onCropGrowPost(IEventHandler<CropGrowPostEvent> ev);

    @ZenMethod
    IEventHandle onBlockPlace(IEventHandler<BlockPlaceEvent> ev);

    @ZenMethod
    IEventHandle onMobGriefing(IEventHandler<MobGriefingEvent> ev);

    @ZenMethod
    IEventHandle onEntityTravelToDimension(IEventHandler<EntityTravelToDimensionEvent> ev);

    @ZenMethod
    IEventHandle onLivingDestroyBlock(IEventHandler<LivingDestroyBlockEvent> ev);

    @ZenMethod
    IEventHandle onLivingExperienceDrop(IEventHandler<LivingExperienceDropEvent> ev);

    @ZenMethod
    IEventHandle onLivingKnockBack(IEventHandler<LivingKnockBackEvent> ev);

    @ZenMethod
    IEventHandle onLootingLevel(IEventHandler<LootingLevelEvent> ev);

    @ZenMethod
    IEventHandle onMinecartCollision(IEventHandler<MinecartCollisionEvent> ev);

    @ZenMethod
    IEventHandle onMinecartInteract(IEventHandler<MinecartInteractEvent> ev);

    @ZenMethod
    IEventHandle onMinecartUpdate(IEventHandler<MinecartUpdateEvent> ev);

    @ZenMethod
    IEventHandle onNoteBlock(IEventHandler<INoteBlockEvent> ev);

    @ZenMethod
    IEventHandle onNoteBlockChange(IEventHandler<NoteBlockChangeEvent> ev);

    @ZenMethod
    IEventHandle onNoteBlockPlay(IEventHandler<NoteBlockPlayEvent> ev);

    @ZenMethod
    IEventHandle onPlayerCloseContainer(IEventHandler<PlayerCloseContainerEvent> ev);

    @ZenMethod
    IEventHandle onPlayerItemPickup(IEventHandler<PlayerItemPickupEvent> ev);

    @ZenMethod
    IEventHandle onPlayerVisibility(IEventHandler<PlayerVisibilityEvent> ev);

    @ZenMethod
    IEventHandle onPlayerLeftClickBlock(IEventHandler<PlayerLeftClickBlockEvent> ev);

    @ZenMethod
    IEventHandle onPlayerRightClickItem(IEventHandler<PlayerRightClickItemEvent> ev);

    @ZenMethod
    IEventHandle onSleepingLocationCheck(IEventHandler<SleepingLocationCheckEvent> ev);

    @ZenMethod
    IEventHandle onSleepingTimeCheck(IEventHandler<SleepingTimeCheckEvent> ev);

    @ZenMethod
    IEventHandle onPotionBrewPre(IEventHandler<PotionBrewPreEvent> ev);

    @ZenMethod
    IEventHandle onPotionBrewPost(IEventHandler<PotionBrewPostEvent> ev);

    @ZenMethod
    IEventHandle onProjectileImpactArrow(IEventHandler<ProjectileImpactArrowEvent> ev);

    @ZenMethod
    IEventHandle onProjectileImpactFireball(IEventHandler<ProjectileImpactFireballEvent> ev);

    @ZenMethod
    IEventHandle onProjectileImpactThrowable(IEventHandler<ProjectileImpactThrowableEvent> ev);
    
    @ZenMethod
    IEventHandle onArrowLoose(IEventHandler<ArrowLooseEvent> ev);

    @ZenMethod
    IEventHandle onArrowNock(IEventHandler<ArrowNockEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityJoinWorld(IEventHandler<EntityJoinWorldEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityLivingEquipmentChange(IEventHandler<EntityLivingEquipmentChangeEvent> ev);
    
    @ZenMethod
    IEventHandle onEntityLivingDamage(IEventHandler<EntityLivingDamageEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingHeal(IEventHandler<EntityLivingHealEvent> ev);

    @ZenMethod
    IEventHandle onEntityLivingUpdate(IEventHandler<EntityLivingUpdateEvent> ev);

    @ZenMethod
    IEventHandle onPotionEffectAdded(IEventHandler<PotionEffectAddedEvent> ev);

    @ZenMethod
    IEventHandle onPlayerClone(IEventHandler<PlayerCloneEvent> ev);

    @ZenMethod
    IEventHandle onBlockNeighborNotify(IEventHandler<BlockNeighborNotifyEvent> ev);

    @ZenMethod
    IEventHandle onPortalSpawn(IEventHandler<PortalSpawnEvent> ev);

    /*
     *
     * IEventHandle onPlayerChat(IPlayerChatEventHandler ev);
     *
     * IEventHandle onTimerSingle(int millis, ITimerEventHandler ev);
     *
     * IEventHandle onTimerRepeat(int millis, ITimerEventHandler ev);
     *
     */
}