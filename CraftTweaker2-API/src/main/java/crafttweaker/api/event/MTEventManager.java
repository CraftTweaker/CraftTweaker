package crafttweaker.api.event;

import crafttweaker.util.*;

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
    private final EventList<PlayerSetSpawnEvent> elPlayerSetSpawn = new EventList<>();
    private final EventList<PlayerDestroyItemEvent> elPlayerDestroyItem = new EventList<>();
    private final EventList<PlayerBrewedPotionEvent> elPlayerBrewedPotion = new EventList<>();
    private final EventList<PlayerTickEvent> elPlayerTick = new EventList<>();
    private final EventList<BlockBreakEvent> elBlockBreak = new EventList<>();
    private final EventList<BlockHarvestDropsEvent> elBlockHarvestDrops = new EventList<>();
    private final EventList<PlayerBreakSpeedEvent> elPlayerBreakSpeed = new EventList<>();
    private final EventList<PlayerRightClickBlockEvent> elPlayerRightClickBlock = new EventList<>();
    
    
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
        elBlockBreak.clear();
        elBlockHarvestDrops.clear();
        elPlayerBreakSpeed.clear();
        elPlayerRightClickBlock.clear();
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


}
