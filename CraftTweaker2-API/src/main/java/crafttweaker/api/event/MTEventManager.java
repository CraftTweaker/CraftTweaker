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
    private final EventList<LivingEntityUseItemEvent.LivingEntityUseItemStartEvent> elEntityLivingUseItemStart = new EventList<>();
    private final EventList<LivingEntityUseItemEvent.LivingEntityUseItemStopEvent> elEntityLivingUseItemStop = new EventList<>();
    private final EventList<LivingEntityUseItemEvent.LivingEntityUseItemTickEvent> elEntityLivingUseItemTick = new EventList<>();
    private final EventList<LivingEntityUseItemEvent.LivingEntityUseItemFinishEvent> elEntityLivingUseItemFinish = new EventList<>();
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
    
    /**
     * Clears all EventLists
     */
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
    // ### PlayerUseItemStartEvent ###
    // ###############################
    
    @Override
    public IEventHandle onEntityLivingUseItemStart(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemStartEvent> ev) {
        return elEntityLivingUseItemStart.add(ev);
    }
    
    public boolean hasEntityLivingUseItemStart() {
        return elEntityLivingUseItemStart.hasHandlers();
    }
    
    public void publishEntityLivingUseItemStart(LivingEntityUseItemEvent.LivingEntityUseItemStartEvent event) {
        elEntityLivingUseItemStart.publish(event);
    }
    
    
    // ##############################
    // ### PlayerUseItemStopEvent ###
    // ##############################
    
    @Override
    public IEventHandle onEntityLivingUseItemStop(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemStopEvent> ev) {
        return elEntityLivingUseItemStop.add(ev);
    }
    
    public boolean hasEntityLivingUseItemStop() {
        return elEntityLivingUseItemStop.hasHandlers();
    }
    
    public void publishEntityLivingUseItemStop(LivingEntityUseItemEvent.LivingEntityUseItemStopEvent event) {
        elEntityLivingUseItemStop.publish(event);
    }
    
    
    // ###############################
    // ### PlayerUseItemStartEvent ###
    // ###############################
    
    @Override
    public IEventHandle onEntityLivingUseItemTick(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemTickEvent> ev) {
        return elEntityLivingUseItemTick.add(ev);
    }
    
    public boolean hasEntityLivingUseItemTick() {
        return elEntityLivingUseItemTick.hasHandlers();
    }
    
    public void publishEntityLivingUseItemTick(LivingEntityUseItemEvent.LivingEntityUseItemTickEvent event) {
        elEntityLivingUseItemTick.publish(event);
    }
    
    
    // ################################
    // ### PlayerUseItemFinishEvent ###
    // ################################
    
    @Override
    public IEventHandle onEntityLivingUseItemFinish(IEventHandler<LivingEntityUseItemEvent.LivingEntityUseItemFinishEvent> ev) {
        return elEntityLivingUseItemFinish.add(ev);
    }
    
    public boolean hasEntityLivingUseItemFinish() {
        return elEntityLivingUseItemFinish.hasHandlers();
    }
    
    public void publishEntityLivingUseItemFinish(LivingEntityUseItemEvent.LivingEntityUseItemFinishEvent event) {
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
}
