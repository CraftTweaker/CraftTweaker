package crafttweaker.api.event;

import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;

/**
 * @author Stan
 */
public class MTEventManager implements IEventManager {

    // ##################
    // ### EventLists ###
    // ##################
    private final EventList<PlayerCraftedEvent> elPlayerCrafted = new EventList<>();
    private final EventList<PlayerInteractEvent> elPlayerInteract = new EventList<>();
    private final EventList<PlayerOpenContainerEvent> elPlayerOpenContainer = new EventList<>();
    private final EventList<PlayerPickupXpEvent> elPlayerPickupXp = new EventList<>();
    private final EventList<PlayerSleepInBedEvent> elPlayerSleepInBed = new EventList<>();
    private final EventList<PlayerWakeUpEvent> elPlayerWakeUp = new EventList<>();
    private final EventList<PlayerUseHoeEvent> elPlayerUseHoe = new EventList<>();
    private final EventList<EntityLivingUseItemEvent.Start> elEntityLivingUseItemStart = new EventList<>();
    private final EventList<EntityLivingUseItemEvent.Stop> elEntityLivingUseItemStop = new EventList<>();
    private final EventList<EntityLivingUseItemEvent.Tick> elEntityLivingUseItemTick = new EventList<>();
    private final EventList<EntityLivingUseItemEvent.Finish> elEntityLivingUseItemFinish = new EventList<>();
    private final EventList<EntityLivingUseItemEvent> elEntityLivingUseItem = new EventList<>();
    private final EventList<PlayerPickupItemEvent> elPlayerPickupItem = new EventList<>();
    private final EventList<PlayerFillBucketEvent> elPlayerFillBucket = new EventList<>();
    private final EventList<PlayerDeathDropsEvent> elPlayerDeathDrops = new EventList<>();
    private final EventList<PlayerRespawnEvent> elPlayerRespawn = new EventList<>();
    private final EventList<PlayerAttackEntityEvent> elPlayerAttackEntity = new EventList<>();
    private final EventList<PlayerBonemealEvent> elPlayerBonemeal = new EventList<>();
    private final EventList<PlayerInteractEntityEvent> elPlayerInteractEntity = new EventList<>();
    private final EventList<PlayerSmeltedEvent> elPlayerSmelted = new EventList<>();
    private final EventList<PlayerChangedDimensionEvent> elPlayerChangedDimension = new EventList<>();
    private final EventList<PlayerLoggedInEvent> elPlayerLoggedIn = new EventList<>();
    private final EventList<PlayerLoggedOutEvent> elPlayerLoggedOut = new EventList<>();
    private final EventList<EntityStruckByLightningEvent> elEntityStruckByLightning = new EventList<>();
    private final EventList<EnderTeleportEvent> elEnderTeleport = new EventList<>();
    private final EventList<EntityLivingAttackedEvent> elEntityLivingAttacked = new EventList<>();
    private final EventList<EntityLivingDeathEvent> elEntityLivingDeath = new EventList<>();
    private final EventList<EntityLivingFallEvent> elEntityLivingFall = new EventList<>();
    private final EventList<EntityLivingHurtEvent> elEntityLivingHurt = new EventList<>();
    private final EventList<EntityLivingJumpEvent> elEntityLivingJump = new EventList<>();
    private final EventList<EntityLivingDeathDropsEvent> elEntityLivingDeathDrops = new EventList<>();
    private final EventList<ItemExpireEvent> elItemExpire = new EventList<>();
    private final EventList<ItemTossEvent> elItemToss = new EventList<>();
    private final EventList<PlayerAnvilRepairEvent> elPlayerAnvilRepair = new EventList<>();
    private final EventList<PlayerAnvilUpdateEvent> elPlayerAnvilUpdate = new EventList<>();
    private final EventList<PlayerSetSpawnEvent> elPlayerSetSpawn = new EventList<>();
    private final EventList<PlayerDestroyItemEvent> elPlayerDestroyItem = new EventList<>();
    private final EventList<PlayerBrewedPotionEvent> elPlayerBrewedPotion = new EventList<>();
    private final EventList<PlayerTickEvent> elPlayerTick = new EventList<>();
    private final EventList<ClientTickEvent> elClientTick = new EventList<>();
    private final EventList<ServerTickEvent> elServerTick = new EventList<>();
    private final EventList<RenderTickEvent> elRenderTick = new EventList<>();
    private final EventList<WorldTickEvent> elWorldTick = new EventList<>();
    private final EventList<BlockBreakEvent> elBlockBreak = new EventList<>();
    private final EventList<BlockHarvestDropsEvent> elBlockHarvestDrops = new EventList<>();
    private final EventList<PlayerBreakSpeedEvent> elPlayerBreakSpeed = new EventList<>();
    private final EventList<PlayerRightClickBlockEvent> elPlayerRightClickBlock = new EventList<>();
    private final EventList<BlockNeighborNotifyEvent> elBlockNeighborNotify = new EventList<>();
    private final EventList<PortalSpawnEvent> elPortalSpawn = new EventList<>();

    /**
     * Clears all EventLists
     */
    @Override
    public void clear() {
        elPlayerCrafted.clear();
        elPlayerCrafted.clear();
        elPlayerInteract.clear();
        elPlayerOpenContainer.clear();
        elPlayerPickupXp.clear();
        elPlayerSleepInBed.clear();
        elPlayerWakeUp.clear();
        elPlayerUseHoe.clear();
        elEntityLivingUseItemStart.clear();
        elEntityLivingUseItemStop.clear();
        elEntityLivingUseItemTick.clear();
        elEntityLivingUseItemFinish.clear();
        elEntityLivingUseItem.clear();
        elPlayerPickupItem.clear();
        elPlayerFillBucket.clear();
        elPlayerDeathDrops.clear();
        elPlayerRespawn.clear();
        elPlayerAttackEntity.clear();
        elPlayerBonemeal.clear();
        elPlayerInteractEntity.clear();
        elPlayerSmelted.clear();
        elPlayerChangedDimension.clear();
        elPlayerLoggedIn.clear();
        elPlayerLoggedOut.clear();
        elEntityStruckByLightning.clear();
        elEnderTeleport.clear();
        elEntityLivingAttacked.clear();
        elEntityLivingDeath.clear();
        elEntityLivingFall.clear();
        elEntityLivingHurt.clear();
        elEntityLivingJump.clear();
        elEntityLivingDeathDrops.clear();
        elItemExpire.clear();
        elItemToss.clear();
        elPlayerAnvilRepair.clear();
        elPlayerSetSpawn.clear();
        elPlayerDestroyItem.clear();
        elPlayerBrewedPotion.clear();
        elPlayerTick.clear();
        elClientTick.clear();
        elServerTick.clear();
        elRenderTick.clear();
        elWorldTick.clear();
        elBlockBreak.clear();
        elBlockHarvestDrops.clear();
        elPlayerBreakSpeed.clear();
        elPlayerRightClickBlock.clear();
        elAnimalTame.clear();
        elFarmlandTrample.clear();
        elEnchantmentLevelSet.clear();
        elEntityMountEvent.clear();
        elExplosionStartEvent.clear();
        elExplosionDetonateEvent.clear();
        elItemFishedEvent.clear();
        elCropGrowPost.clear();
        elCropGrowPre.clear();
        elBlockPlace.clear();
        elMobGriefing.clear();
        elEntityTravelToDimension.clear();
        elLivingDestroyBlock.clear();
        elLivingExperienceDrop.clear();
        elLivingKnockBack.clear();
        elMinecartCollision.clear();
        elMinecartInteract.clear();
        elMinecartUpdate.clear();
        elPlayerCloseContainer.clear();
        elPlayerItemPickup.clear();
        elPlayerVisibility.clear();
        elPlayerLeftClickBlock.clear();
        elPlayerRightClickItem.clear();
        elSleepingLocationCheck.clear();
        elSleepingTimeCheck.clear();
        elPotionBrewPre.clear();
        elPotionBrewPost.clear();
        elProjectileImpactArrow.clear();
        elProjectileImpactFireball.clear();
        elProjectileImpactThrowable.clear();
        elAllowDespawn.clear();
        elCheckSpawn.clear();
        elCommand.clear();
        elCriticalHit.clear();
        elLootingLevel.clear();
        elPlayerAdvancement.clear();
        elSpecialSpawn.clear();
        elArrowLoose.clear();
        elArrowNock.clear();
        elEntityJoinWorld.clear();
        elEntityLivingEquipmentChange.clear();
        elEntityLivingDamage.clear();
        elEntityLivingHeal.clear();
        elEntityLivingUpdateEvent.clear();
        elPotionEffectAdded.clear();
        elPlayerCloneEvent.clear();
        elBlockNeighborNotify.clear();
        elPortalSpawn.clear();
        elNoteBlock.clear();
        elNoteBlockChange.clear();
        elNoteBlockPlay.clear();
    }

    // ##########################
    // ### PlayerCraftedEvent ###
    // ##########################


    @Override
    public IEventHandle onPlayerCrafted(IEventHandler<PlayerCraftedEvent> ev) {
        return elPlayerCrafted.add(ev);
    }

    public boolean hasPlayerCrafted() {
        return elPlayerCrafted.hasHandlers();
    }

    public void publishPlayerCrafted(PlayerCraftedEvent event) {
        elPlayerCrafted.publish(event);
    }

    // ##########################
    // ### PlayerSmeltedEvent ###
    // ##########################


    @Override
    public IEventHandle onPlayerSmelted(IEventHandler<PlayerSmeltedEvent> ev) {
        return elPlayerSmelted.add(ev);
    }

    public boolean hasPlayerSmelted() {
        return elPlayerSmelted.hasHandlers();
    }

    public void publishPlayerSmelted(PlayerSmeltedEvent event) {
        elPlayerSmelted.publish(event);
    }

    // ###################################
    // ### PlayerChangedDimensionEvent ###
    // ###################################


    @Override
    public IEventHandle onPlayerChangedDimension(IEventHandler<PlayerChangedDimensionEvent> ev) {
        return elPlayerChangedDimension.add(ev);
    }

    public boolean hasPlayerChangedDimension() {
        return elPlayerChangedDimension.hasHandlers();
    }

    public void publishPlayerChangedDimension(PlayerChangedDimensionEvent event) {
        elPlayerChangedDimension.publish(event);
    }

    // ###########################
    // ### PlayerLoggedInEvent ###
    // ###########################


    @Override
    public IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev) {
        return elPlayerLoggedIn.add(ev);
    }

    public boolean hasPlayerLoggedIn() {
        return elPlayerLoggedIn.hasHandlers();
    }

    public void publishPlayerLoggedIn(PlayerLoggedInEvent event) {
        elPlayerLoggedIn.publish(event);
    }

    // ############################
    // ### PlayerLoggedOutEvent ###
    // ############################


    public IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev) {
        return elPlayerLoggedOut.add(ev);
    }

    public boolean hasPlayerLoggedOut() {
        return elPlayerLoggedOut.hasHandlers();
    }

    public void publishPlayerLoggedOut(PlayerLoggedOutEvent event) {
        elPlayerLoggedOut.publish(event);
    }

    // ##########################
    // ### PlayerRespawnEvent ###
    // ##########################


    @Override
    public IEventHandle onPlayerRespawn(IEventHandler<PlayerRespawnEvent> ev) {
        return elPlayerRespawn.add(ev);
    }

    public boolean hasPlayerRespawn() {
        return elPlayerRespawn.hasHandlers();
    }

    public void publishPlayerRespawn(PlayerRespawnEvent event) {
        elPlayerRespawn.publish(event);
    }

    // ###############################
    // ### PlayerAttackEntityEvent ###
    // ###############################


    @Override
    public IEventHandle onPlayerAttackEntity(IEventHandler<PlayerAttackEntityEvent> ev) {
        return elPlayerAttackEntity.add(ev);
    }

    public boolean hasPlayerAttackEntity() {
        return elPlayerAttackEntity.hasHandlers();
    }

    public void publishPlayerAttackEntity(PlayerAttackEntityEvent event) {
        elPlayerAttackEntity.publish(event);
    }

    // ###########################
    // ### PlayerBonemealEvent ###
    // ###########################


    @Override
    public IEventHandle onPlayerBonemeal(IEventHandler<PlayerBonemealEvent> ev) {
        return elPlayerBonemeal.add(ev);
    }

    public boolean hasPlayerBonemeal() {
        return elPlayerBonemeal.hasHandlers();
    }

    public void publishPlayerBonemeal(PlayerBonemealEvent event) {
        elPlayerBonemeal.publish(event);
    }

    // #################################
    // ### PlayerInteractEntityEvent ###
    // #################################


    @Override
    public IEventHandle onPlayerInteractEntity(IEventHandler<PlayerInteractEntityEvent> ev) {
        return elPlayerInteractEntity.add(ev);
    }

    public boolean hasPlayerInteractEntity() {
        return elPlayerInteractEntity.hasHandlers();
    }

    public void publishPlayerInteractEntity(PlayerInteractEntityEvent event) {
        elPlayerInteractEntity.publish(event);
    }

    // #############################
    // ### PlayerPickupItemEvent ###
    // #############################


    @Override
    public IEventHandle onPlayerPickupItem(IEventHandler<PlayerPickupItemEvent> ev) {
        return elPlayerPickupItem.add(ev);
    }

    public boolean hasPlayerPickupItem() {
        return elPlayerPickupItem.hasHandlers();
    }

    public void publishPlayerPickupItem(PlayerPickupItemEvent event) {
        elPlayerPickupItem.publish(event);
    }

    // #############################
    // ### PlayerFillBucketEvent ###
    // #############################


    @Override
    public IEventHandle onPlayerFillBucket(IEventHandler<PlayerFillBucketEvent> ev) {
        return elPlayerFillBucket.add(ev);
    }

    public boolean hasPlayerFillBucket() {
        return elPlayerFillBucket.hasHandlers();
    }

    public void publishPlayerFillBucket(PlayerFillBucketEvent event) {
        elPlayerFillBucket.publish(event);
    }

    // #############################
    // ### PlayerDeathDropsEvent ###
    // #############################


    @Override
    public IEventHandle onPlayerDeathDrops(IEventHandler<PlayerDeathDropsEvent> ev) {
        return elPlayerDeathDrops.add(ev);
    }

    public boolean hasPlayerDeathDrops() {
        return elPlayerDeathDrops.hasHandlers();
    }

    public void publishPlayerDeathDrops(PlayerDeathDropsEvent event) {
        elPlayerDeathDrops.publish(event);
    }

    // ###########################
    // ### PlayerInteractEvent ###
    // ###########################


    @Override
    public IEventHandle onPlayerInteract(IEventHandler<PlayerInteractEvent> ev) {
        return elPlayerInteract.add(ev);
    }

    public boolean hasPlayerInteract() {
        return elPlayerInteract.hasHandlers();
    }

    public void publishPlayerInteract(PlayerInteractEvent event) {
        elPlayerInteract.publish(event);
    }

    // ################################
    // ### PlayerOpenContainerEvent ###
    // ################################


    @Override
    public IEventHandle onPlayerOpenContainer(IEventHandler<PlayerOpenContainerEvent> ev) {
        return elPlayerOpenContainer.add(ev);
    }

    public boolean hasPlayerOpenContainer() {
        return elPlayerOpenContainer.hasHandlers();
    }

    public void publishPlayerOpenContainer(PlayerOpenContainerEvent event) {
        elPlayerOpenContainer.publish(event);
    }

    // ###########################
    // ### PlayerPickupXpEvent ###
    // ###########################


    @Override
    public IEventHandle onPlayerPickupXp(IEventHandler<PlayerPickupXpEvent> ev) {
        return elPlayerPickupXp.add(ev);
    }

    public boolean hasPlayerPickupXp() {
        return elPlayerPickupXp.hasHandlers();
    }

    public void publishPlayerPickupXp(PlayerPickupXpEvent event) {
        elPlayerPickupXp.publish(event);
    }

    // #############################
    // ### PlayerSleepInBedEvent ###
    // #############################


    @Override
    public IEventHandle onPlayerSleepInBed(IEventHandler<PlayerSleepInBedEvent> ev) {
        return elPlayerSleepInBed.add(ev);
    }

    public boolean hasPlayerSleepInBed() {
        return elPlayerSleepInBed.hasHandlers();
    }

    public void publishPlayerSleepInBed(PlayerSleepInBedEvent event) {
        elPlayerSleepInBed.publish(event);
    }

    // #############################
    // ### PlayerWakeUpEvent ###
    // #############################


    @Override
    public IEventHandle onPlayerWakeUp(IEventHandler<PlayerWakeUpEvent> ev) {
        return elPlayerWakeUp.add(ev);
    }

    public boolean hasPlayerWakeUp() {
        return elPlayerWakeUp.hasHandlers();
    }

    public void publishPlayerWakeUp(PlayerWakeUpEvent event) {
        elPlayerWakeUp.publish(event);
    }

    // #########################
    // ### PlayerUseHoeEvent ###
    // #########################


    @Override
    public IEventHandle onPlayerUseHoe(IEventHandler<PlayerUseHoeEvent> ev) {
        return elPlayerUseHoe.add(ev);
    }

    public boolean hasPlayerUseHoe() {
        return elPlayerUseHoe.hasHandlers();
    }

    public void publishPlayerUseHoe(PlayerUseHoeEvent event) {
        elPlayerUseHoe.publish(event);
    }

    // ###############################
    // ### EntityUseItemStartEvent ###
    // ###############################


    @Override
    public IEventHandle onEntityLivingUseItem(IEventHandler<EntityLivingUseItemEvent> ev) {
        return elEntityLivingUseItem.add(ev);
    }

    public boolean hasEntityLivingUseItem() {
        return elEntityLivingUseItem.hasHandlers();
    }

    public void publishEntityLivingUseItem(EntityLivingUseItemEvent event) {
        elEntityLivingUseItem.publish(event);
    }

    // ###############################
    // ### EntityUseItemStartEvent ###
    // ###############################


    @Override
    public IEventHandle onEntityLivingUseItemStart(IEventHandler<EntityLivingUseItemEvent.Start> ev) {
        return elEntityLivingUseItemStart.add(ev);
    }

    public boolean hasEntityLivingUseItemStart() {
        return elEntityLivingUseItemStart.hasHandlers();
    }

    public void publishEntityLivingUseItemStart(EntityLivingUseItemEvent.Start event) {
        elEntityLivingUseItemStart.publish(event);
    }

    // ##############################
    // ### EntityUseItemStopEvent ###
    // ##############################


    @Override
    public IEventHandle onEntityLivingUseItemStop(IEventHandler<EntityLivingUseItemEvent.Stop> ev) {
        return elEntityLivingUseItemStop.add(ev);
    }

    public boolean hasEntityLivingUseItemStop() {
        return elEntityLivingUseItemStop.hasHandlers();
    }

    public void publishEntityLivingUseItemStop(EntityLivingUseItemEvent.Stop event) {
        elEntityLivingUseItemStop.publish(event);
    }

    // ###############################
    // ### EntityUseItemStartEvent ###
    // ###############################


    @Override
    public IEventHandle onEntityLivingUseItemTick(IEventHandler<EntityLivingUseItemEvent.Tick> ev) {
        return elEntityLivingUseItemTick.add(ev);
    }

    public boolean hasEntityLivingUseItemTick() {
        return elEntityLivingUseItemTick.hasHandlers();
    }

    public void publishEntityLivingUseItemTick(EntityLivingUseItemEvent.Tick event) {
        elEntityLivingUseItemTick.publish(event);
    }

    // ################################
    // ### EntityUseItemFinishEvent ###
    // ################################


    @Override
    public IEventHandle onEntityLivingUseItemFinish(IEventHandler<EntityLivingUseItemEvent.Finish> ev) {
        return elEntityLivingUseItemFinish.add(ev);
    }

    public boolean hasEntityLivingUseItemFinish() {
        return elEntityLivingUseItemFinish.hasHandlers();
    }

    public void publishEntityLivingUseItemFinish(EntityLivingUseItemEvent.Finish event) {
        elEntityLivingUseItemFinish.publish(event);
    }


    // ###############################
    // ### EntityStruckByLightning ###
    // ###############################

    @Override
    public IEventHandle onEntityStruckByLightning(IEventHandler<EntityStruckByLightningEvent> ev) {
        return elEntityStruckByLightning.add(ev);
    }

    public boolean hasEntityStruckByLightning() {
        return elEntityStruckByLightning.hasHandlers();
    }

    public void publishEntityStruckByLightning(EntityStruckByLightningEvent event) {
        elEntityStruckByLightning.publish(event);
    }


    // ###############################
    // ### EntityStruckByLightning ###
    // ###############################

    @Override
    public IEventHandle onEnderTeleport(IEventHandler<EnderTeleportEvent> ev) {
        return elEnderTeleport.add(ev);
    }

    public boolean hasEnderTeleport() {
        return elEnderTeleport.hasHandlers();
    }

    public void publishEnderTeleport(EnderTeleportEvent event) {
        elEnderTeleport.publish(event);
    }


    // ############################
    // ### LivingEntityAttacked ###
    // ############################

    @Override
    public IEventHandle onEntityLivingAttacked(IEventHandler<EntityLivingAttackedEvent> ev) {
        return elEntityLivingAttacked.add(ev);
    }

    public boolean hasEntityLivingAttacked() {
        return elEntityLivingAttacked.hasHandlers();
    }

    public void publishEntityLivingAttacked(EntityLivingAttackedEvent event) {
        elEntityLivingAttacked.publish(event);
    }


    // #########################
    // ### LivingEntityDeath ###
    // #########################

    @Override
    public IEventHandle onEntityLivingDeath(IEventHandler<EntityLivingDeathEvent> ev) {
        return elEntityLivingDeath.add(ev);
    }

    public boolean hasEntityLivingDeath() {
        return elEntityLivingDeath.hasHandlers();
    }

    public void publishEntityLivingDeath(EntityLivingDeathEvent event) {
        elEntityLivingDeath.publish(event);
    }


    // ########################
    // ### LivingEntityFall ###
    // ########################

    @Override
    public IEventHandle onEntityLivingFall(IEventHandler<EntityLivingFallEvent> ev) {
        return elEntityLivingFall.add(ev);
    }

    public boolean hasEntityLivingFall() {
        return elEntityLivingFall.hasHandlers();
    }

    public void publishEntityLivingFall(EntityLivingFallEvent event) {
        elEntityLivingFall.publish(event);
    }


    // ########################
    // ### LivingEntityHurt ###
    // ########################

    @Override
    public IEventHandle onEntityLivingHurt(IEventHandler<EntityLivingHurtEvent> ev) {
        return elEntityLivingHurt.add(ev);
    }

    public boolean hasEntityLivingHurt() {
        return elEntityLivingHurt.hasHandlers();
    }

    public void publishEntityLivingHurt(EntityLivingHurtEvent event) {
        elEntityLivingHurt.publish(event);
    }


    // ########################
    // ### LivingEntityJump ###
    // ########################

    @Override
    public IEventHandle onEntityLivingJump(IEventHandler<EntityLivingJumpEvent> ev) {
        return elEntityLivingJump.add(ev);
    }

    public boolean hasEntityLivingJump() {
        return elEntityLivingJump.hasHandlers();
    }

    public void publishEntityLivingJump(EntityLivingJumpEvent event) {
        elEntityLivingJump.publish(event);
    }


    // ##############################
    // ### EntityLivingDeathDrops ###
    // ##############################

    @Override
    public IEventHandle onEntityLivingDeathDrops(IEventHandler<EntityLivingDeathDropsEvent> ev) {
        return elEntityLivingDeathDrops.add(ev);
    }

    public boolean hasEntityLivingDeathDrops() {
        return elEntityLivingDeathDrops.hasHandlers();
    }

    public void publishEntityLivingDeathDrops(EntityLivingDeathDropsEvent event) {
        elEntityLivingDeathDrops.publish(event);
    }


    // ##################
    // ### ItemExpire ###
    // ##################

    @Override
    public IEventHandle onItemExpire(IEventHandler<ItemExpireEvent> ev) {
        return elItemExpire.add(ev);
    }

    public boolean hasItemExpire() {
        return elItemExpire.hasHandlers();
    }

    public void publishItemExpire(ItemExpireEvent event) {
        elItemExpire.publish(event);
    }


    // ################
    // ### ItemToss ###
    // ################

    @Override
    public IEventHandle onItemToss(IEventHandler<ItemTossEvent> ev) {
        return elItemToss.add(ev);
    }

    public boolean hasItemToss() {
        return elItemToss.hasHandlers();
    }

    public void publishItemToss(ItemTossEvent event) {
        elItemToss.publish(event);
    }


    // #########################
    // ### PlayerAnvilRepair ###
    // #########################


    @Override
    public IEventHandle onPlayerAnvilRepair(IEventHandler<PlayerAnvilRepairEvent> ev) {
        return elPlayerAnvilRepair.add(ev);
    }

    public boolean hasPlayerAnvilRepair() {
        return elPlayerAnvilRepair.hasHandlers();
    }

    public void publishPlayerAnvilRepair(PlayerAnvilRepairEvent event) {
        elPlayerAnvilRepair.publish(event);
    }
    
    
    // #########################
    // ### PlayerAnvilUpdate ###
    // #########################

    @Override
    public IEventHandle onPlayerAnvilUpdate(IEventHandler<PlayerAnvilUpdateEvent> ev) {
        return elPlayerAnvilUpdate.add(ev);
    }

    public boolean hasPlayerAnvilUpdate() {
        return elPlayerAnvilUpdate.hasHandlers();
    }

    public void publishPlayerAnvilUpdate(PlayerAnvilUpdateEvent event) {
        elPlayerAnvilUpdate.publish(event);
    }
    
    
    // ######################
    // ### PlayerSetSpawn ###
    // ######################


    @Override
    public IEventHandle onPlayerSetSpawn(IEventHandler<PlayerSetSpawnEvent> ev) {
        return elPlayerSetSpawn.add(ev);
    }

    public boolean hasPlayerSetSpawn() {
        return elPlayerSetSpawn.hasHandlers();
    }

    public void publishPlayerSetSpawn(PlayerSetSpawnEvent event) {
        elPlayerSetSpawn.publish(event);
    }

    // #########################
    // ### PlayerDestroyItem ###
    // #########################


    @Override
    public IEventHandle onPlayerDestroyItem(IEventHandler<PlayerDestroyItemEvent> ev) {
        return elPlayerDestroyItem.add(ev);
    }

    public boolean hasPlayerDestroyItem() {
        return elPlayerDestroyItem.hasHandlers();
    }

    public void publishPlayerDestroyItem(PlayerDestroyItemEvent event) {
        elPlayerDestroyItem.publish(event);
    }
    // ##########################
    // ### PlayerBrewedPotion ###
    // ##########################


    @Override
    public IEventHandle onPlayerBrewedPotion(IEventHandler<PlayerBrewedPotionEvent> ev) {
        return elPlayerBrewedPotion.add(ev);
    }

    public boolean hasPlayerBrewedPotion() {
        return elPlayerBrewedPotion.hasHandlers();
    }

    public void publishPlayerBrewedPotion(PlayerBrewedPotionEvent event) {
        elPlayerBrewedPotion.publish(event);
    }


    // ##################
    // ### PlayerTick ###
    // ##################


    @Override
    public IEventHandle onPlayerTick(IEventHandler<PlayerTickEvent> ev) {
        return elPlayerTick.add(ev);
    }

    public boolean hasPlayerTick() {
        return elPlayerTick.hasHandlers();
    }

    public void publishPlayerTick(PlayerTickEvent event) {
        elPlayerTick.publish(event);
    }

    
    // ##################
    // ### ClientTick ###
    // ##################


    @Override
    public IEventHandle onClientTick(IEventHandler<ClientTickEvent> ev) {
        return elClientTick.add(ev);
    }

    public boolean hasClientTick() {
        return elClientTick.hasHandlers();
    }

    public void publishClientTick(ClientTickEvent event) {
        elClientTick.publish(event);
    }

    
    // ##################
    // ### ServerTick ###
    // ##################


    @Override
    public IEventHandle onServerTick(IEventHandler<ServerTickEvent> ev) {
        return elServerTick.add(ev);
    }

    public boolean hasServerTick() {
        return elServerTick.hasHandlers();
    }

    public void publishServerTick(ServerTickEvent event) {
        elServerTick.publish(event);
    }

    
    // ##################
    // ### RenderTick ###
    // ##################


    @Override
    public IEventHandle onRenderTick(IEventHandler<RenderTickEvent> ev) {
        return elRenderTick.add(ev);
    }

    public boolean hasRenderTick() {
        return elRenderTick.hasHandlers();
    }

    public void publishRenderTick(RenderTickEvent event) {
        elRenderTick.publish(event);
    }

    
    // ##################
    // ### WorldTick ###
    // ##################


    @Override
    public IEventHandle onWorldTick(IEventHandler<WorldTickEvent> ev) {
        return elWorldTick.add(ev);
    }

    public boolean hasWorldTick() {
        return elWorldTick.hasHandlers();
    }

    public void publishWorldTick(WorldTickEvent event) {
        elWorldTick.publish(event);
    }


    // ##################
    // ### BlockBreak ###
    // ##################

    @Override
    public IEventHandle onBlockBreak(IEventHandler<BlockBreakEvent> ev) {
        return elBlockBreak.add(ev);
    }

    public boolean hasBlockBreak() {
        return elBlockBreak.hasHandlers();
    }

    public void publishBlockBreak(BlockBreakEvent event) {
        elBlockBreak.publish(event);
    }


    // #########################
    // ### BlockHarvestDrops ###
    // #########################

    @Override
    public IEventHandle onBlockHarvestDrops(IEventHandler<BlockHarvestDropsEvent> ev) {
        return elBlockHarvestDrops.add(ev);
    }

    public boolean hasBlockHarvestDrops() {
        return elBlockHarvestDrops.hasHandlers();
    }

    public void publishBlockHarvestDrops(BlockHarvestDropsEvent event) {
        elBlockHarvestDrops.publish(event);
    }


    // ########################
    // ### PlayerBreakSpeed ###
    // ########################

    @Override
    public IEventHandle onPlayerBreakSpeed(IEventHandler<PlayerBreakSpeedEvent> ev) {
        return elPlayerBreakSpeed.add(ev);
    }

    public boolean hasPlayerBreakSpeed() {
        return elPlayerBreakSpeed.hasHandlers();
    }

    public void publishPlayerBreakSpeed(PlayerBreakSpeedEvent event) {
        elPlayerBreakSpeed.publish(event);
    }


    // #############################
    // ### PlayerRightClickBlock ###
    // #############################

    @Override
    public IEventHandle onPlayerRightClickBlock(IEventHandler<PlayerRightClickBlockEvent> ev) {
        return elPlayerRightClickBlock.add(ev);
    }

    public boolean hasPlayerRightClickBlock() {
        return elPlayerRightClickBlock.hasHandlers();
    }

    public void publishPlayerRightClickBlock(PlayerRightClickBlockEvent event) {
        elPlayerRightClickBlock.publish(event);
    }


    //Only due to a wrong name... Well, now they can use it
    @Override
    public IEventHandle onPlayerInteractBlock(IEventHandler<PlayerRightClickBlockEvent> ev) {
        return onPlayerRightClickBlock(ev);
    }

    // ###############
    // ### Command ###
    // ###############

    private final EventList<CommandEvent> elCommand = new EventList<>();

    @Override
    public IEventHandle onCommand(IEventHandler<CommandEvent> ev) {
        return elCommand.add(ev);
    }

    public boolean hasCommand() {
        return elCommand.hasHandlers();
    }

    public void publishCommand(CommandEvent event) {
        elCommand.publish(event);
    }


    // #########################
    // ### PlayerAdvancement ###
    // #########################

    private final EventList<PlayerAdvancementEvent> elPlayerAdvancement = new EventList<>();

    @Override
    public IEventHandle onPlayerAdvancement(IEventHandler<PlayerAdvancementEvent> ev) {
        return elPlayerAdvancement.add(ev);
    }

    public boolean hasPlayerAdvancement() {
        return elPlayerAdvancement.hasHandlers();
    }

    public void publishPlayerAdvancement(PlayerAdvancementEvent event) {
        elPlayerAdvancement.publish(event);
    }

    // ##################
    // ### CheckSpawn ###
    // ##################

    private final EventList<EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent> elCheckSpawn = new EventList<>();

    @Override
    public IEventHandle onCheckSpawn(IEventHandler<EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent> ev) {
        return elCheckSpawn.add(ev);
    }

    public boolean hasCheckSpawn() {
        return elCheckSpawn.hasHandlers();
    }

    public void publishCheckSpawn(EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent event) {
        elCheckSpawn.publish(event);
    }

    // ####################
    // ### SpecialSpawn ###
    // ####################

    private final EventList<EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent> elSpecialSpawn = new EventList<>();

    @Override
    public IEventHandle onSpecialSpawn(IEventHandler<EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent> ev) {
        return elSpecialSpawn.add(ev);
    }

    public boolean hasSpecialSpawn() {
        return elSpecialSpawn.hasHandlers();
    }

    public void publishSpecialSpawn(EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent event) {
        elSpecialSpawn.publish(event);
    }


    // ####################
    // ### AllowDespawn ###
    // ####################

    private final EventList<EntityLivingSpawnEvent> elAllowDespawn = new EventList<>();

    @Override
    public IEventHandle onAllowDespawn(IEventHandler<EntityLivingSpawnEvent> ev) {
        return elAllowDespawn.add(ev);
    }

    public boolean hasAllowDespawn() {
        return elAllowDespawn.hasHandlers();
    }

    public void publishAllowDespawn(EntityLivingSpawnEvent event) {
        elAllowDespawn.publish(event);
    }

    // ########################
    // ###  AnimalTameEvent ###
    // ########################

    private final EventList<AnimalTameEvent> elAnimalTame = new EventList<>();

    @Override
    public IEventHandle onAnimalTame(IEventHandler<AnimalTameEvent> ev) {
        return elAnimalTame.add(ev);
    }

    public boolean hasAnimalTame() {
        return elAnimalTame.hasHandlers();
    }

    public void publishAnimalTame(AnimalTameEvent event) {
        elAnimalTame.publish(event);
    }

    // ##############################
    // ###  FarmlandTrampleEvent  ###
    // ##############################

    private final EventList<BlockFarmlandTrampleEvent> elFarmlandTrample = new EventList<>();

    @Override
    public IEventHandle onFarmlandTrample(IEventHandler<BlockFarmlandTrampleEvent> ev) {
        return elFarmlandTrample.add(ev);
    }

    public boolean hasFarmlandTrample() {
        return elFarmlandTrample.hasHandlers();
    }

    public void publishFarmlandTrample(BlockFarmlandTrampleEvent event) {
        elFarmlandTrample.publish(event);
    }

    // ##########################
    // ###  CriticalHitEvent  ###
    // ##########################

    private final EventList<CriticalHitEvent> elCriticalHit = new EventList<>();

    @Override
    public IEventHandle onCriticalHit(IEventHandler<CriticalHitEvent> ev) {
        return elCriticalHit.add(ev);
    }

    public boolean hasCriticalHit() {
        return elCriticalHit.hasHandlers();
    }

    public void publishCriticalHit(CriticalHitEvent event) {
        elCriticalHit.publish(event);
    }

    // ##################################
    // ###  EnchantmentLevelSetEvent  ###
    // ##################################

    private final EventList<EnchantmentLevelSetEvent> elEnchantmentLevelSet = new EventList<>();

    @Override
    public IEventHandle onEnchantmentLevelSet(IEventHandler<EnchantmentLevelSetEvent> ev) {
        return elEnchantmentLevelSet.add(ev);
    }

    public boolean hasEnchantmentLevelSet() {
        return elEnchantmentLevelSet.hasHandlers();
    }

    public void publishEnchantmentLevelSet(EnchantmentLevelSetEvent event) {
        elEnchantmentLevelSet.publish(event);
    }

    // ##########################
    // ###  EntityMountEvent  ###
    // ##########################

    private final EventList<EntityMountEvent> elEntityMountEvent = new EventList<>();

    @Override
    public IEventHandle onEntityMount(IEventHandler<EntityMountEvent> ev) {
        return elEntityMountEvent.add(ev);
    }

    public boolean hasEntityMount() {
        return elEntityMountEvent.hasHandlers();
    }

    public void publishEntityMount(EntityMountEvent event) {
        elEntityMountEvent.publish(event);
    }

    // #############################
    // ###  ExplosionStartEvent  ###
    // #############################

    private final EventList<ExplosionStartEvent> elExplosionStartEvent = new EventList<>();

    @Override
    public IEventHandle onExplosionStart(IEventHandler<ExplosionStartEvent> ev) {
        return elExplosionStartEvent.add(ev);
    }

    public boolean hasExplosionStart() {
        return elExplosionStartEvent.hasHandlers();
    }

    public void publishExplosionStart(ExplosionStartEvent event) {
        elExplosionStartEvent.publish(event);
    }

    // ################################
    // ###  ExplosionDetonateEvent  ###
    // ################################

    private final EventList<ExplosionDetonateEvent> elExplosionDetonateEvent = new EventList<>();

    @Override
    public IEventHandle onExplosionDetonate(IEventHandler<ExplosionDetonateEvent> ev) {
        return elExplosionDetonateEvent.add(ev);
    }

    public boolean hasExplosionDetonate() {
        return elExplosionDetonateEvent.hasHandlers();
    }

    public void publishExplosionDetonate(ExplosionDetonateEvent event) {
        elExplosionDetonateEvent.publish(event);
    }

    // #########################
    // ###  ItemFishedEvent  ###
    // #########################

    private final EventList<ItemFishedEvent> elItemFishedEvent = new EventList<>();

    @Override
    public IEventHandle onItemFished(IEventHandler<ItemFishedEvent> ev) {
        return elItemFishedEvent.add(ev);
    }

    public boolean hasItemFished() {
        return elItemFishedEvent.hasHandlers();
    }

    public void publishItemFished(ItemFishedEvent event) {
        elItemFishedEvent.publish(event);
    }

    // ##########################
    // ###  CropGrowEventPre  ###
    // ##########################

    private final EventList<CropGrowPreEvent> elCropGrowPre = new EventList<>();

    @Override
    public IEventHandle onCropGrowPre(IEventHandler<CropGrowPreEvent> ev) {
        return elCropGrowPre.add(ev);
    }

    public boolean hasCropGrowPre() {
        return elCropGrowPre.hasHandlers();
    }

    public void publishCropGrowPre(CropGrowPreEvent event) {
        elCropGrowPre.publish(event);
    }

    // ###########################
    // ###  CropGrowEventPost  ###
    // ###########################

    private final EventList<CropGrowPostEvent> elCropGrowPost = new EventList<>();

    @Override
    public IEventHandle onCropGrowPost(IEventHandler<CropGrowPostEvent> ev) {
        return elCropGrowPost.add(ev);
    }

    public boolean hasCropGrowPost() {
        return elCropGrowPost.hasHandlers();
    }

    public void publishCropGrowPost(CropGrowPostEvent event) {
        elCropGrowPost.publish(event);
    }

    // #########################
    // ###  BlockPlaceEvent  ###
    // #########################

    private final EventList<BlockPlaceEvent> elBlockPlace = new EventList<>();

    @Override
    public IEventHandle onBlockPlace(IEventHandler<BlockPlaceEvent> ev) {
        return elBlockPlace.add(ev);
    }

    public boolean hasBlockPlace() {
        return elBlockPlace.hasHandlers();
    }

    public void publishBlockPlace(BlockPlaceEvent event) {
        elBlockPlace.publish(event);
    }

    // #########################
    // ###  MobGriefingEvent ###
    // #########################

    private final EventList<MobGriefingEvent> elMobGriefing = new EventList<>();

    @Override
    public IEventHandle onMobGriefing(IEventHandler<MobGriefingEvent> ev) {
        return elMobGriefing.add(ev);
    }

    public boolean hasMobGriefing() {
        return elMobGriefing.hasHandlers();
    }

    public void publishMobGriefing(MobGriefingEvent event) {
        elMobGriefing.publish(event);
    }

    // #################################
    // ###  EntityTravelToDimension  ###
    // #################################

    private final EventList<EntityTravelToDimensionEvent> elEntityTravelToDimension = new EventList<>();

    @Override
    public IEventHandle onEntityTravelToDimension(IEventHandler<EntityTravelToDimensionEvent> ev) {
        return elEntityTravelToDimension.add(ev);
    }

    public boolean hasEntityTravelToDimension() {
        return elEntityTravelToDimension.hasHandlers();
    }

    public void publishEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        elEntityTravelToDimension.publish(event);
    }

    // #################################
    // ###  LivingDestroyBlockEvent  ###
    // #################################

    private final EventList<LivingDestroyBlockEvent> elLivingDestroyBlock = new EventList<>();

    @Override
    public IEventHandle onLivingDestroyBlock(IEventHandler<LivingDestroyBlockEvent> ev) {
        return elLivingDestroyBlock.add(ev);
    }

    public boolean hasLivingDestroyBlock() {
        return elLivingDestroyBlock.hasHandlers();
    }

    public void publishLivingDestroyBlock(LivingDestroyBlockEvent event) {
        elLivingDestroyBlock.publish(event);
    }

    // #################################
    // ###  LivingExperienceDropEvent  ###
    // #################################

    private final EventList<LivingExperienceDropEvent> elLivingExperienceDrop = new EventList<>();

    @Override
    public IEventHandle onLivingExperienceDrop(IEventHandler<LivingExperienceDropEvent> ev) {
        return elLivingExperienceDrop.add(ev);
    }

    public boolean hasLivingExperienceDrop() {
        return elLivingExperienceDrop.hasHandlers();
    }

    public void publishLivingExperienceDrop(LivingExperienceDropEvent event) {
        elLivingExperienceDrop.publish(event);
    }

    // ##############################
    // ###  LivingKnockBackEvent  ###
    // ##############################

    private final EventList<LivingKnockBackEvent> elLivingKnockBack = new EventList<>();

    @Override
    public IEventHandle onLivingKnockBack(IEventHandler<LivingKnockBackEvent> ev) {
        return elLivingKnockBack.add(ev);
    }

    public boolean hasLivingKnockBack() {
        return elLivingKnockBack.hasHandlers();
    }

    public void publishLivingKnockBack(LivingKnockBackEvent event) {
        elLivingKnockBack.publish(event);
    }

    // ###########################
    // ###  LootingLevelEvent  ###
    // ###########################

    private final EventList<LootingLevelEvent> elLootingLevel = new EventList<>();

    @Override
    public IEventHandle onLootingLevel(IEventHandler<LootingLevelEvent> ev) {
        return elLootingLevel.add(ev);
    }

    public boolean hasLootingLevel() {
        return elLootingLevel.hasHandlers();
    }

    public void publishLootingLevel(LootingLevelEvent event) {
        elLootingLevel.publish(event);
    }

    // ################################
    // ###  MinecartCollisionEvent  ###
    // ################################

    private final EventList<MinecartCollisionEvent> elMinecartCollision = new EventList<>();

    @Override
    public IEventHandle onMinecartCollision(IEventHandler<MinecartCollisionEvent> ev) {
        return elMinecartCollision.add(ev);
    }

    public boolean hasMinecartCollision() {
        return elMinecartCollision.hasHandlers();
    }

    public void publishMinecartCollision(MinecartCollisionEvent event) {
        elMinecartCollision.publish(event);
    }

    // ###############################
    // ###  MinecartInteractEvent  ###
    // ###############################

    private final EventList<MinecartInteractEvent> elMinecartInteract = new EventList<>();

    @Override
    public IEventHandle onMinecartInteract(IEventHandler<MinecartInteractEvent> ev) {
        return elMinecartInteract.add(ev);
    }

    public boolean hasMinecartInteract() {
        return elMinecartInteract.hasHandlers();
    }

    public void publishMinecartInteract(MinecartInteractEvent event) {
        elMinecartInteract.publish(event);
    }

    // ###############################
    // ###   MinecartUpdateEvent   ###
    // ###############################

    private final EventList<MinecartUpdateEvent> elMinecartUpdate = new EventList<>();

    @Override
    public IEventHandle onMinecartUpdate(IEventHandler<MinecartUpdateEvent> ev) {
        return elMinecartUpdate.add(ev);
    }

    public boolean hasMinecartUpdate() {
        return elMinecartUpdate.hasHandlers();
    }

    public void publishMinecartUpdate(MinecartUpdateEvent event) {
        elMinecartUpdate.publish(event);
    }

    // ###############################
    // ###     NoteBlockEvent      ###
    // ###############################

    private final EventList<INoteBlockEvent> elNoteBlock = new EventList<>();

    @Override
    public IEventHandle onNoteBlock(IEventHandler<INoteBlockEvent> ev) {
        return elNoteBlock.add(ev);
    }

    public boolean hasNoteBlock() {
        return elNoteBlock.hasHandlers();
    }

    public void publishNoteBlock(INoteBlockEvent event) {
        elNoteBlock.publish(event);
    }

    // ###############################
    // ###  NoteBlockChangeEvent   ###
    // ###############################

    private final EventList<NoteBlockChangeEvent> elNoteBlockChange = new EventList<>();

    @Override
    public IEventHandle onNoteBlockChange(IEventHandler<NoteBlockChangeEvent> ev) {
        return elNoteBlockChange.add(ev);
    }

    public boolean hasNoteBlockChange() {
        return elNoteBlockChange.hasHandlers();
    }

    public void publishNoteBlockChange(NoteBlockChangeEvent event) {
        elNoteBlockChange.publish(event);
    }

    // ###############################
    // ###   NoteBlockPlayEvent    ###
    // ###############################

    private final EventList<NoteBlockPlayEvent> elNoteBlockPlay = new EventList<>();

    @Override
    public IEventHandle onNoteBlockPlay(IEventHandler<NoteBlockPlayEvent> ev) {
        return elNoteBlockPlay.add(ev);
    }

    public boolean hasNoteBlockPlay() {
        return elNoteBlockPlay.hasHandlers();
    }

    public void publishNoteBlockPlay(NoteBlockPlayEvent event) {
        elNoteBlockPlay.publish(event);
    }

    // ###################################
    // ###  PlayerCloseContainerEvent  ###
    // ###################################

    private final EventList<PlayerCloseContainerEvent> elPlayerCloseContainer = new EventList<>();

    @Override
    public IEventHandle onPlayerCloseContainer(IEventHandler<PlayerCloseContainerEvent> ev) {
        return elPlayerCloseContainer.add(ev);
    }

    public boolean hasPlayerCloseContainer() {
        return elPlayerCloseContainer.hasHandlers();
    }

    public void publishPlayerCloseContainer(PlayerCloseContainerEvent event) {
        elPlayerCloseContainer.publish(event);
    }

    // ###############################
    // ###  PlayerItemPickupEvent  ###
    // ###############################

    private final EventList<PlayerItemPickupEvent> elPlayerItemPickup = new EventList<>();

    @Override
    public IEventHandle onPlayerItemPickup(IEventHandler<PlayerItemPickupEvent> ev) {
        return elPlayerItemPickup.add(ev);
    }

    public boolean hasPlayerItemPickup() {
        return elPlayerItemPickup.hasHandlers();
    }

    public void publishPlayerItemPickup(PlayerItemPickupEvent event) {
        elPlayerItemPickup.publish(event);
    }

    // ###############################
    // ###  PlayerVisibilityEvent  ###
    // ###############################

    private final EventList<PlayerVisibilityEvent> elPlayerVisibility = new EventList<>();

    @Override
    public IEventHandle onPlayerVisibility(IEventHandler<PlayerVisibilityEvent> ev) {
        return elPlayerVisibility.add(ev);
    }

    public boolean hasPlayerVisibility() {
        return elPlayerVisibility.hasHandlers();
    }

    public void publishPlayerVisibility(PlayerVisibilityEvent event) {
        elPlayerVisibility.publish(event);
    }

    // ###################################
    // ###  PlayerLeftClickBlockEvent  ###
    // ###################################

    private final EventList<PlayerLeftClickBlockEvent> elPlayerLeftClickBlock = new EventList<>();

    @Override
    public IEventHandle onPlayerLeftClickBlock(IEventHandler<PlayerLeftClickBlockEvent> ev) {
        return elPlayerLeftClickBlock.add(ev);
    }

    public boolean hasPlayerLeftClickBlock() {
        return elPlayerLeftClickBlock.hasHandlers();
    }

    public void publishPlayerLeftClickBlock(PlayerLeftClickBlockEvent event) {
        elPlayerLeftClickBlock.publish(event);
    }

    // ####################################
    // ###  PlayerRightClickItemEvent  ###
    // ####################################

    private final EventList<PlayerRightClickItemEvent> elPlayerRightClickItem = new EventList<>();

    @Override
    public IEventHandle onPlayerRightClickItem(IEventHandler<PlayerRightClickItemEvent> ev) {
        return elPlayerRightClickItem.add(ev);
    }

    public boolean hasPlayerRightClickItem() {
        return elPlayerRightClickItem.hasHandlers();
    }

    public void publishPlayerRightClickItem(PlayerRightClickItemEvent event) {
        elPlayerRightClickItem.publish(event);
    }

    // ####################################
    // ###  SleepingLocationCheckEvent  ###
    // ####################################

    private final EventList<SleepingLocationCheckEvent> elSleepingLocationCheck = new EventList<>();

    @Override
    public IEventHandle onSleepingLocationCheck(IEventHandler<SleepingLocationCheckEvent> ev) {
        return elSleepingLocationCheck.add(ev);
    }

    public boolean hasSleepingLocationCheck() {
        return elSleepingLocationCheck.hasHandlers();
    }

    public void publishSleepingLocationCheck(SleepingLocationCheckEvent event) {
        elSleepingLocationCheck.publish(event);
    }

    // ################################
    // ###  SleepingTimeCheckEvent  ###
    // ################################

    private final EventList<SleepingTimeCheckEvent> elSleepingTimeCheck = new EventList<>();

    @Override
    public IEventHandle onSleepingTimeCheck(IEventHandler<SleepingTimeCheckEvent> ev) {
        return elSleepingTimeCheck.add(ev);
    }

    public boolean hasSleepingTimeCheck() {
        return elSleepingTimeCheck.hasHandlers();
    }

    public void publishSleepingTimeCheck(SleepingTimeCheckEvent event) {
        elSleepingTimeCheck.publish(event);
    }

    // ############################
    // ###  PotionBrewPreEvent  ###
    // ############################

    private final EventList<PotionBrewPreEvent> elPotionBrewPre = new EventList<>();

    @Override
    public IEventHandle onPotionBrewPre(IEventHandler<PotionBrewPreEvent> ev) {
        return elPotionBrewPre.add(ev);
    }

    public boolean hasPotionBrewPre() {
        return elPotionBrewPre.hasHandlers();
    }

    public void publishPotionBrewPre(PotionBrewPreEvent event) {
        elPotionBrewPre.publish(event);
    }

    // #############################
    // ###  PotionBrewPostEvent  ###
    // #############################

    private final EventList<PotionBrewPostEvent> elPotionBrewPost = new EventList<>();

    @Override
    public IEventHandle onPotionBrewPost(IEventHandler<PotionBrewPostEvent> ev) {
        return elPotionBrewPost.add(ev);
    }

    public boolean hasPotionBrewPost() {
        return elPotionBrewPost.hasHandlers();
    }

    public void publishPotionBrewPost(PotionBrewPostEvent event) {
        elPotionBrewPost.publish(event);
    }

    // ####################################
    // ###  ProjectileImpactArrowEvent  ###
    // ####################################

    private final EventList<ProjectileImpactArrowEvent> elProjectileImpactArrow = new EventList<>();

    @Override
    public IEventHandle onProjectileImpactArrow(IEventHandler<ProjectileImpactArrowEvent> ev) {
        return elProjectileImpactArrow.add(ev);
    }

    public boolean hasProjectileImpactArrow() {
        return elProjectileImpactArrow.hasHandlers();
    }

    public void publishProjectileImpactArrow(ProjectileImpactArrowEvent event) {
        elProjectileImpactArrow.publish(event);
    }

    // #######################################
    // ###  ProjectileImpactFireballEvent  ###
    // #######################################

    private final EventList<ProjectileImpactFireballEvent> elProjectileImpactFireball = new EventList<>();

    @Override
    public IEventHandle onProjectileImpactFireball(IEventHandler<ProjectileImpactFireballEvent> ev) {
        return elProjectileImpactFireball.add(ev);
    }

    public boolean hasProjectileImpactFireball() {
        return elProjectileImpactFireball.hasHandlers();
    }

    public void publishProjectileImpactFireball(ProjectileImpactFireballEvent event) {
        elProjectileImpactFireball.publish(event);
    }

    // ########################################
    // ###  ProjectileImpactThrowableEvent  ###
    // ########################################

    private final EventList<ProjectileImpactThrowableEvent> elProjectileImpactThrowable = new EventList<>();

    @Override
    public IEventHandle onProjectileImpactThrowable(IEventHandler<ProjectileImpactThrowableEvent> ev) {
        return elProjectileImpactThrowable.add(ev);
    }

    public boolean hasProjectileImpactThrowable() {
        return elProjectileImpactThrowable.hasHandlers();
    }

    public void publishProjectileImpactThrowable(ProjectileImpactThrowableEvent event) {
        elProjectileImpactThrowable.publish(event);
    }

    // #########################
    // ###  ArrowLooseEvent  ###
    // #########################

    private final EventList<ArrowLooseEvent> elArrowLoose = new EventList<>();

    @Override
    public IEventHandle onArrowLoose(IEventHandler<ArrowLooseEvent> ev) {
        return elArrowLoose.add(ev);
    }

    public boolean hasArrowLoose() {
        return elArrowLoose.hasHandlers();
    }

    public void publishArrowLoose(ArrowLooseEvent event) {
    	elArrowLoose.publish(event);
    }

    // ########################
    // ###  ArrowNockEvent  ###
    // ########################

    private final EventList<ArrowNockEvent> elArrowNock = new EventList<>();

    @Override
    public IEventHandle onArrowNock(IEventHandler<ArrowNockEvent> ev) {
        return elArrowNock.add(ev);
    }

    public boolean hasArrowNock() {
        return elArrowNock.hasHandlers();
    }

    public void publishArrowNock(ArrowNockEvent event) {
    	elArrowNock.publish(event);
    }
    
    // ##############################
    // ###  EntityJoinWorldEvent  ###
    // ##############################

    private final EventList<EntityJoinWorldEvent> elEntityJoinWorld = new EventList<>();

    @Override
    public IEventHandle onEntityJoinWorld(IEventHandler<EntityJoinWorldEvent> ev) {
        return elEntityJoinWorld.add(ev);
    }

    public boolean hasEntityJoinWorld() {
        return elEntityJoinWorld.hasHandlers();
    }

    public void publishEntityJoinWorld(EntityJoinWorldEvent event) {
    	elEntityJoinWorld.publish(event);
    }
    
    // ##########################################
    // ###  EntityLivingEquipmentChangeEvent  ###
    // ##########################################

    private final EventList<EntityLivingEquipmentChangeEvent> elEntityLivingEquipmentChange = new EventList<>();

    @Override
    public IEventHandle onEntityLivingEquipmentChange(IEventHandler<EntityLivingEquipmentChangeEvent> ev) {
        return elEntityLivingEquipmentChange.add(ev);
    }

    public boolean hasEntityLivingEquipmentChange() {
        return elEntityLivingEquipmentChange.hasHandlers();
    }

    public void publishEntityLivingEquipmentChange(EntityLivingEquipmentChangeEvent event) {
    	elEntityLivingEquipmentChange.publish(event);
    }
    
    // ##########################
    // ### LivingEntityDamage ###
    // ##########################
    
    private final EventList<EntityLivingDamageEvent> elEntityLivingDamage = new EventList<>();

    @Override
    public IEventHandle onEntityLivingDamage(IEventHandler<EntityLivingDamageEvent> ev) {
        return elEntityLivingDamage.add(ev);
    }

    public boolean hasEntityLivingDamage() {
        return elEntityLivingDamage.hasHandlers();
    }

    public void publishEntityLivingDamage(EntityLivingDamageEvent event) {
        elEntityLivingDamage.publish(event);
    }
    
    // ########################
    // ### LivingEntityHeal ###
    // ########################
    
    private final EventList<EntityLivingHealEvent> elEntityLivingHeal = new EventList<>();

    @Override
    public IEventHandle onEntityLivingHeal(IEventHandler<EntityLivingHealEvent> ev) {
        return elEntityLivingHeal.add(ev);
    }

    public boolean hasEntityLivingHeal() {
        return elEntityLivingHeal.hasHandlers();
    }

    public void publishEntityLivingHeal(EntityLivingHealEvent event) {
        elEntityLivingHeal.publish(event);
    }

    // ###############################
    // ### EntityLivingUpdateEvent ###
    // ###############################

    private final EventList<EntityLivingUpdateEvent> elEntityLivingUpdateEvent = new EventList<>();

    @Override
    public IEventHandle onEntityLivingUpdate(IEventHandler<EntityLivingUpdateEvent> ev) {
        return elEntityLivingUpdateEvent.add(ev);
    }

    public boolean hasEntityLivingUpdateEvent() {
        return elEntityLivingUpdateEvent.hasHandlers();
    }

    public void publishEntityLivingUpdateEvent(EntityLivingUpdateEvent event) {
        elEntityLivingUpdateEvent.publish(event);
    }

    // #########################
    // ### PotionEffectAdded ###
    // #########################

    private final EventList<PotionEffectAddedEvent> elPotionEffectAdded = new EventList<>();

    @Override
    public IEventHandle onPotionEffectAdded(IEventHandler<PotionEffectAddedEvent> ev) {
        return elPotionEffectAdded.add(ev);
    }

    public boolean hasPotionEffectAdded() {
        return elPotionEffectAdded.hasHandlers();
    }

    public void publishPotionEffectAdded(PotionEffectAddedEvent event) {
        elPotionEffectAdded.publish(event);
    }

    // ########################
    // ### PlayerCloneEvent ###
    // ########################

    private final EventList<PlayerCloneEvent> elPlayerCloneEvent = new EventList<>();

    @Override
    public IEventHandle onPlayerClone(IEventHandler<PlayerCloneEvent> ev) {
        return elPlayerCloneEvent.add(ev);
    }

    public boolean hasPlayerCloneEvent() {
        return elPlayerCloneEvent.hasHandlers();
    }

    public void publishPlayerCloneEvent(PlayerCloneEvent event) {
        elPlayerCloneEvent.publish(event);
    }

    // #################################
    // ### BlockNeighborNotifyEvent ###
    // #################################

    @Override
    public IEventHandle onBlockNeighborNotify(IEventHandler<BlockNeighborNotifyEvent> ev) {
        return elBlockNeighborNotify.add(ev);
    }

    public boolean hasBlockNeighborNotifyEvent() {
        return elBlockNeighborNotify.hasHandlers();
    }

    public void publishBlockNeighborNotifyEvent(BlockNeighborNotifyEvent ev) {
        elBlockNeighborNotify.publish(ev);
    }

    // ########################
    // ### PortalSpawnEvent ###
    // ########################


    @Override
    public IEventHandle onPortalSpawn(IEventHandler<PortalSpawnEvent> ev) {
        return elPortalSpawn.add(ev);
    }

    public boolean hasPortalSpawnEvent() {
        return elPortalSpawn.hasHandlers();
    }

    public void publishPortalSpawnEvent(PortalSpawnEvent ev) {
        elPortalSpawn.publish(ev);
    }
}