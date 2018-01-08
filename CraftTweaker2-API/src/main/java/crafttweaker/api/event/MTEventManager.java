package crafttweaker.api.event;

import crafttweaker.util.*;

/**
 * @author Stan
 */
public class MTEventManager implements IEventManager {
    
    private final EventList<PlayerCraftedEvent> elPlayerCrafted = new EventList<>();
    
    // ##########################
    // ### PlayerCraftedEvent ###
    // ##########################
    private final EventList<PlayerSmeltedEvent> elPlayerSmelted = new EventList<>();
    private final EventList<PlayerChangedDimensionEvent> elPlayerChangedDimension = new EventList<>();
    private final EventList<PlayerLoggedInEvent> elPlayerLoggedIn = new EventList<>();
    private final EventList<PlayerLoggedOutEvent> elPlayerLoggedOut = new EventList<>();
    
    // ##########################
    // ### PlayerSmeltedEvent ###
    // ##########################
    private final EventList<PlayerRespawnEvent> elPlayerRespawn = new EventList<>();
    private final EventList<PlayerAttackEntityEvent> elPlayerAttackEntity = new EventList<>();
    private final EventList<PlayerBonemealEvent> elPlayerBonemeal = new EventList<>();
    private final EventList<PlayerInteractEntityEvent> elPlayerInteractEntity = new EventList<>();
    
    // ###################################
    // ### PlayerChangedDimensionEvent ###
    // ###################################
    private final EventList<PlayerPickupEvent> elPlayerPickup = new EventList<>();
    private final EventList<PlayerPickupItemEvent> elPlayerPickupItem = new EventList<>();
    private final EventList<PlayerFillBucketEvent> elPlayerFillBucket = new EventList<>();
    private final EventList<PlayerDeathDropsEvent> elPlayerDeathDrops = new EventList<>();
    
    // ###########################
    // ### PlayerLoggedInEvent ###
    // ###########################
    private final EventList<PlayerInteractEvent> elPlayerInteract = new EventList<>();
    private final EventList<PlayerOpenContainerEvent> elPlayerOpenContainer = new EventList<>();
    private final EventList<PlayerPickupXpEvent> elPlayerPickupXp = new EventList<>();
    private final EventList<PlayerSleepInBedEvent> elPlayerSleepInBed = new EventList<>();
    
    // ############################
    // ### PlayerLoggedOutEvent ###
    // ############################
    private final EventList<PlayerUseHoeEvent> elPlayerUseHoe = new EventList<>();
    private final EventList<PlayerUseItemStartEvent> elPlayerUseItemStart = new EventList<>();
    private final EventList<PlayerUseItemTickEvent> elPlayerUseItemTick = new EventList<>();
    
    public void clear() {
        elPlayerCrafted.clear();
        elPlayerSmelted.clear();
        elPlayerChangedDimension.clear();
        elPlayerLoggedIn.clear();
        elPlayerLoggedOut.clear();
        elPlayerRespawn.clear();
        elPlayerAttackEntity.clear();
        elPlayerBonemeal.clear();
        elPlayerInteractEntity.clear();
        elPlayerPickup.clear();
        elPlayerPickupItem.clear();
        elPlayerFillBucket.clear();
        elPlayerDeathDrops.clear();
        elPlayerInteract.clear();
        elPlayerOpenContainer.clear();
        elPlayerPickupXp.clear();
        elPlayerSleepInBed.clear();
        elPlayerUseHoe.clear();
        elPlayerUseItemStart.clear();
        elPlayerUseItemTick.clear();
    }
    
    // ##########################
    // ### PlayerRespawnEvent ###
    // ##########################
    
    @Override
    public IEventHandle onPlayerCrafted(IEventHandler<PlayerCraftedEvent> ev) {
        return elPlayerCrafted.add(ev);
    }
    
    public boolean hasPlayerCrafted() {
        return elPlayerCrafted.hasHandlers();
    }
    
    public boolean publishPlayerCrafted(PlayerCraftedEvent event) {
        return elPlayerCrafted.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerSmelted(IEventHandler<PlayerSmeltedEvent> ev) {
        return elPlayerSmelted.add(ev);
    }
    
    // ###############################
    // ### PlayerAttackEntityEvent ###
    // ###############################
    
    public boolean hasPlayerSmelted() {
        return elPlayerSmelted.hasHandlers();
    }
    
    public boolean publishPlayerSmelted(PlayerSmeltedEvent event) {
        return elPlayerSmelted.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerChangedDimension(IEventHandler<PlayerChangedDimensionEvent> ev) {
        return elPlayerChangedDimension.add(ev);
    }
    
    public boolean hasPlayerChangedDimension() {
        return elPlayerChangedDimension.hasHandlers();
    }
    
    // ###########################
    // ### PlayerBonemealEvent ###
    // ###########################
    
    public boolean publishPlayerChangedDimension(PlayerChangedDimensionEvent event) {
        return elPlayerChangedDimension.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev) {
        return elPlayerLoggedIn.add(ev);
    }
    
    public boolean hasPlayerLoggedIn() {
        return elPlayerLoggedIn.hasHandlers();
    }
    
    public boolean publishPlayerLoggedIn(PlayerLoggedInEvent event) {
        return elPlayerLoggedIn.publish(event);
    }
    
    // #################################
    // ### PlayerInteractEntityEvent ###
    // #################################
    
    public IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev) {
        return elPlayerLoggedOut.add(ev);
    }
    
    public boolean hasPlayerLoggedOut() {
        return elPlayerLoggedOut.hasHandlers();
    }
    
    public boolean publishPlayerLoggedOut(PlayerLoggedOutEvent event) {
        return elPlayerLoggedOut.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerRespawn(IEventHandler<PlayerRespawnEvent> ev) {
        return elPlayerRespawn.add(ev);
    }
    
    // #########################
    // ### PlayerPickupEvent ###
    // #########################
    
    public boolean hasPlayerRespawn() {
        return elPlayerRespawn.hasHandlers();
    }
    
    public boolean publishPlayerRespawn(PlayerRespawnEvent event) {
        return elPlayerRespawn.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerAttackEntity(IEventHandler<PlayerAttackEntityEvent> ev) {
        return elPlayerAttackEntity.add(ev);
    }
    
    public boolean hasPlayerAttackEntity() {
        return elPlayerAttackEntity.hasHandlers();
    }
    
    // #############################
    // ### PlayerPickupItemEvent ###
    // #############################
    
    public boolean publishPlayerAttackEntity(PlayerAttackEntityEvent event) {
        return elPlayerAttackEntity.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerBonemeal(IEventHandler<PlayerBonemealEvent> ev) {
        return elPlayerBonemeal.add(ev);
    }
    
    public boolean hasPlayerBonemeal() {
        return elPlayerBonemeal.hasHandlers();
    }
    
    public boolean publishPlayerBonemeal(PlayerBonemealEvent event) {
        return elPlayerBonemeal.publish(event);
    }
    
    // #############################
    // ### PlayerFillBucketEvent ###
    // #############################
    
    @Override
    public IEventHandle onPlayerInteractEntity(IEventHandler<PlayerInteractEntityEvent> ev) {
        return elPlayerInteractEntity.add(ev);
    }
    
    public boolean hasPlayerInteractEntity() {
        return elPlayerInteractEntity.hasHandlers();
    }
    
    public boolean publishPlayerInteractEntity(PlayerInteractEntityEvent event) {
        return elPlayerInteractEntity.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerPickup(IEventHandler<PlayerPickupEvent> ev) {
        return elPlayerPickup.add(ev);
    }
    
    // #############################
    // ### PlayerDeathDropsEvent ###
    // #############################
    
    public boolean hasPlayerPickup() {
        return elPlayerPickup.hasHandlers();
    }
    
    public boolean publishPlayerPickup(PlayerPickupEvent event) {
        return elPlayerPickup.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerPickupItem(IEventHandler<PlayerPickupItemEvent> ev) {
        return elPlayerPickupItem.add(ev);
    }
    
    public boolean hasPlayerPickupItem() {
        return elPlayerPickupItem.hasHandlers();
    }
    
    // ###########################
    // ### PlayerInteractEvent ###
    // ###########################
    
    public boolean publishPlayerPickupItem(PlayerPickupItemEvent event) {
        return elPlayerPickupItem.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerFillBucket(IEventHandler<PlayerFillBucketEvent> ev) {
        return elPlayerFillBucket.add(ev);
    }
    
    public boolean hasPlayerFillBucket() {
        return elPlayerFillBucket.hasHandlers();
    }
    
    public boolean publishPlayerFillBucket(PlayerFillBucketEvent event) {
        return elPlayerFillBucket.publish(event);
    }
    
    // ################################
    // ### PlayerOpenContainerEvent ###
    // ################################
    
    @Override
    public IEventHandle onPlayerDeathDrops(IEventHandler<PlayerDeathDropsEvent> ev) {
        return elPlayerDeathDrops.add(ev);
    }
    
    public boolean hasPlayerDeathDrops() {
        return elPlayerDeathDrops.hasHandlers();
    }
    
    public boolean publishPlayerDeathDrops(PlayerDeathDropsEvent event) {
        return elPlayerDeathDrops.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerInteract(IEventHandler<PlayerInteractEvent> ev) {
        return elPlayerInteract.add(ev);
    }
    
    // ###########################
    // ### PlayerPickupXpEvent ###
    // ###########################
    
    public boolean hasPlayerInteract() {
        return elPlayerInteract.hasHandlers();
    }
    
    public boolean publishPlayerInteract(PlayerInteractEvent event) {
        return elPlayerInteract.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerOpenContainer(IEventHandler<PlayerOpenContainerEvent> ev) {
        return elPlayerOpenContainer.add(ev);
    }
    
    public boolean hasPlayerOpenContainer() {
        return elPlayerOpenContainer.hasHandlers();
    }
    
    // #############################
    // ### PlayerSleepInBedEvent ###
    // #############################
    
    public boolean publishPlayerOpenContainer(PlayerOpenContainerEvent event) {
        return elPlayerOpenContainer.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerPickupXp(IEventHandler<PlayerPickupXpEvent> ev) {
        return elPlayerPickupXp.add(ev);
    }
    
    public boolean hasPlayerPickupXp() {
        return elPlayerPickupXp.hasHandlers();
    }
    
    public boolean publishPlayerPickup(PlayerPickupXpEvent event) {
        return elPlayerPickupXp.publish(event);
    }
    
    // #########################
    // ### PlayerUseHoeEvent ###
    // #########################
    
    @Override
    public IEventHandle onPlayerSleepInBed(IEventHandler<PlayerSleepInBedEvent> ev) {
        return elPlayerSleepInBed.add(ev);
    }
    
    public boolean hasPlayerSleepInBed() {
        return elPlayerSleepInBed.hasHandlers();
    }
    
    public boolean publishPlayerSleepInBed(PlayerSleepInBedEvent event) {
        return elPlayerSleepInBed.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerUseHoe(IEventHandler<PlayerUseHoeEvent> ev) {
        return elPlayerUseHoe.add(ev);
    }
    
    // ###############################
    // ### PlayerUseItemStartEvent ###
    // ###############################
    
    public boolean hasPlayerUseHoe() {
        return elPlayerUseHoe.hasHandlers();
    }
    
    public boolean publishPlayerUseHoe(PlayerUseHoeEvent event) {
        return elPlayerUseHoe.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerUseItemStart(IEventHandler<PlayerUseItemStartEvent> ev) {
        return elPlayerUseItemStart.add(ev);
    }
    
    public boolean hasPlayerUseItemStart() {
        return elPlayerUseItemStart.hasHandlers();
    }
    
    // ##############################
    // ### PlayerUseItemTickEvent ###
    // ##############################
    
    public boolean publishPlayerUseItemStart(PlayerUseItemStartEvent event) {
        return elPlayerUseItemStart.publish(event);
    }
    
    @Override
    public IEventHandle onPlayerUseItemTick(IEventHandler<PlayerUseItemTickEvent> ev) {
        return elPlayerUseItemTick.add(ev);
    }
    
    public boolean hasPlayerUseItemTick() {
        return elPlayerUseItemTick.hasHandlers();
    }
    
    public boolean publishPlayerUseItemTick(PlayerUseItemTickEvent event) {
        return elPlayerUseItemTick.publish(event);
    }
}
