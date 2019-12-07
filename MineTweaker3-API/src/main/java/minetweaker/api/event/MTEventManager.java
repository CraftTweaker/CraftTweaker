package minetweaker.api.event;

import minetweaker.util.*;

/**
 * @author Stan
 */
public class MTEventManager implements IEventManager {

    private final EventList<PlayerCraftedEvent> elPlayerCrafted = new EventList<PlayerCraftedEvent>();

    // ##########################
    // ### PlayerCraftedEvent ###
    // ##########################
    private final EventList<PlayerSmeltedEvent> elPlayerSmelted = new EventList<PlayerSmeltedEvent>();
    private final EventList<PlayerChangedDimensionEvent> elPlayerChangedDimension = new EventList<PlayerChangedDimensionEvent>();
    private final EventList<PlayerLoggedInEvent> elPlayerLoggedIn = new EventList<PlayerLoggedInEvent>();
    private final EventList<PlayerLoggedOutEvent> elPlayerLoggedOut = new EventList<PlayerLoggedOutEvent>();

    // ##########################
    // ### PlayerSmeltedEvent ###
    // ##########################
    private final EventList<PlayerRespawnEvent> elPlayerRespawn = new EventList<PlayerRespawnEvent>();
    private final EventList<PlayerAttackEntityEvent> elPlayerAttackEntity = new EventList<PlayerAttackEntityEvent>();
    private final EventList<PlayerBonemealEvent> elPlayerBonemeal = new EventList<PlayerBonemealEvent>();
    private final EventList<PlayerInteractEntityEvent> elPlayerInteractEntity = new EventList<PlayerInteractEntityEvent>();

    // ###################################
    // ### PlayerChangedDimensionEvent ###
    // ###################################
    private final EventList<PlayerPickupEvent> elPlayerPickup = new EventList<PlayerPickupEvent>();
    private final EventList<PlayerPickupItemEvent> elPlayerPickupItem = new EventList<PlayerPickupItemEvent>();
    private final EventList<PlayerFillBucketEvent> elPlayerFillBucket = new EventList<PlayerFillBucketEvent>();
    private final EventList<PlayerDeathDropsEvent> elPlayerDeathDrops = new EventList<PlayerDeathDropsEvent>();

    // ###########################
    // ### PlayerLoggedInEvent ###
    // ###########################
    private final EventList<PlayerInteractEvent> elPlayerInteract = new EventList<PlayerInteractEvent>();
    private final EventList<PlayerOpenContainerEvent> elPlayerOpenContainer = new EventList<PlayerOpenContainerEvent>();
    private final EventList<PlayerPickupXpEvent> elPlayerPickupXp = new EventList<PlayerPickupXpEvent>();
    private final EventList<PlayerSleepInBedEvent> elPlayerSleepInBed = new EventList<PlayerSleepInBedEvent>();

    // ############################
    // ### PlayerLoggedOutEvent ###
    // ############################
    private final EventList<PlayerUseHoeEvent> elPlayerUseHoe = new EventList<PlayerUseHoeEvent>();
    private final EventList<PlayerUseItemStartEvent> elPlayerUseItemStart = new EventList<PlayerUseItemStartEvent>();
    private final EventList<PlayerUseItemTickEvent> elPlayerUseItemTick = new EventList<PlayerUseItemTickEvent>();

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

    public void publishPlayerCrafted(PlayerCraftedEvent event) {
        elPlayerCrafted.publish(event);
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

    public void publishPlayerSmelted(PlayerSmeltedEvent event) {
        elPlayerSmelted.publish(event);
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

    public void publishPlayerChangedDimension(PlayerChangedDimensionEvent event) {
        elPlayerChangedDimension.publish(event);
    }

    public IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev) {
        return elPlayerLoggedIn.add(ev);
    }

    public boolean hasPlayerLoggedIn() {
        return elPlayerLoggedIn.hasHandlers();
    }

    public void publishPlayerLoggedIn(PlayerLoggedInEvent event) {
        elPlayerLoggedIn.publish(event);
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

    public void publishPlayerLoggedOut(PlayerLoggedOutEvent event) {
        elPlayerLoggedOut.publish(event);
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

    public void publishPlayerRespawn(PlayerRespawnEvent event) {
        elPlayerRespawn.publish(event);
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

    public void publishPlayerAttackEntity(PlayerAttackEntityEvent event) {
        elPlayerAttackEntity.publish(event);
    }

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

    public void publishPlayerInteractEntity(PlayerInteractEntityEvent event) {
        elPlayerInteractEntity.publish(event);
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

    public void publishPlayerPickup(PlayerPickupEvent event) {
        elPlayerPickup.publish(event);
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

    public void publishPlayerPickupItem(PlayerPickupItemEvent event) {
        elPlayerPickupItem.publish(event);
    }

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

    public void publishPlayerDeathDrops(PlayerDeathDropsEvent event) {
        elPlayerDeathDrops.publish(event);
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

    public void publishPlayerInteract(PlayerInteractEvent event) {
        elPlayerInteract.publish(event);
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

    public void publishPlayerOpenContainer(PlayerOpenContainerEvent event) {
        elPlayerOpenContainer.publish(event);
    }

    @Override
    public IEventHandle onPlayerPickupXp(IEventHandler<PlayerPickupXpEvent> ev) {
        return elPlayerPickupXp.add(ev);
    }

    public boolean hasPlayerPickupXp() {
        return elPlayerPickupXp.hasHandlers();
    }

    public void publishPlayerPickup(PlayerPickupXpEvent event) {
        elPlayerPickupXp.publish(event);
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

    public void publishPlayerSleepInBed(PlayerSleepInBedEvent event) {
        elPlayerSleepInBed.publish(event);
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

    public void publishPlayerUseHoe(PlayerUseHoeEvent event) {
        elPlayerUseHoe.publish(event);
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

    public void publishPlayerUseItemStart(PlayerUseItemStartEvent event) {
        elPlayerUseItemStart.publish(event);
    }

    @Override
    public IEventHandle onPlayerUseItemTick(IEventHandler<PlayerUseItemTickEvent> ev) {
        return elPlayerUseItemTick.add(ev);
    }

    public boolean hasPlayerUseItemTick() {
        return elPlayerUseItemTick.hasHandlers();
    }

    public void publishPlayerUseItemTick(PlayerUseItemTickEvent event) {
        elPlayerUseItemTick.publish(event);
    }
}
