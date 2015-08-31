/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.util.EventList;
import minetweaker.util.IEventHandler;

/**
 *
 * @author Stan
 */
public class MTEventManager implements IEventManager {
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
	// ### PlayerCraftedEvent ###
	// ##########################

	private final EventList<PlayerCraftedEvent> elPlayerCrafted = new EventList<PlayerCraftedEvent>();

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

	private final EventList<PlayerSmeltedEvent> elPlayerSmelted = new EventList<PlayerSmeltedEvent>();

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

	private final EventList<PlayerChangedDimensionEvent> elPlayerChangedDimension = new EventList<PlayerChangedDimensionEvent>();

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

	private final EventList<PlayerLoggedInEvent> elPlayerLoggedIn = new EventList<PlayerLoggedInEvent>();

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

	private final EventList<PlayerLoggedOutEvent> elPlayerLoggedOut = new EventList<PlayerLoggedOutEvent>();

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

	private final EventList<PlayerRespawnEvent> elPlayerRespawn = new EventList<PlayerRespawnEvent>();

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

	private final EventList<PlayerAttackEntityEvent> elPlayerAttackEntity = new EventList<PlayerAttackEntityEvent>();

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

	private final EventList<PlayerBonemealEvent> elPlayerBonemeal = new EventList<PlayerBonemealEvent>();

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

	private final EventList<PlayerInteractEntityEvent> elPlayerInteractEntity = new EventList<PlayerInteractEntityEvent>();

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

	// #########################
	// ### PlayerPickupEvent ###
	// #########################

	private final EventList<PlayerPickupEvent> elPlayerPickup = new EventList<PlayerPickupEvent>();

	@Override
	public IEventHandle onPlayerPickup(IEventHandler<PlayerPickupEvent> ev) {
		return elPlayerPickup.add(ev);
	}

	public boolean hasPlayerPickup() {
		return elPlayerPickup.hasHandlers();
	}

	public void publishPlayerPickup(PlayerPickupEvent event) {
		elPlayerPickup.publish(event);
	}

	// #############################
	// ### PlayerPickupItemEvent ###
	// #############################

	private final EventList<PlayerPickupItemEvent> elPlayerPickupItem = new EventList<PlayerPickupItemEvent>();

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

	private final EventList<PlayerFillBucketEvent> elPlayerFillBucket = new EventList<PlayerFillBucketEvent>();

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

	private final EventList<PlayerDeathDropsEvent> elPlayerDeathDrops = new EventList<PlayerDeathDropsEvent>();

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

	private final EventList<PlayerInteractEvent> elPlayerInteract = new EventList<PlayerInteractEvent>();

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

	private final EventList<PlayerOpenContainerEvent> elPlayerOpenContainer = new EventList<PlayerOpenContainerEvent>();

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

	private final EventList<PlayerPickupXpEvent> elPlayerPickupXp = new EventList<PlayerPickupXpEvent>();

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

	// #############################
	// ### PlayerSleepInBedEvent ###
	// #############################

	private final EventList<PlayerSleepInBedEvent> elPlayerSleepInBed = new EventList<PlayerSleepInBedEvent>();

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

	private final EventList<PlayerUseHoeEvent> elPlayerUseHoe = new EventList<PlayerUseHoeEvent>();

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

	private final EventList<PlayerUseItemStartEvent> elPlayerUseItemStart = new EventList<PlayerUseItemStartEvent>();

	@Override
	public IEventHandle onPlayerUseItemStart(IEventHandler<PlayerUseItemStartEvent> ev) {
		return elPlayerUseItemStart.add(ev);
	}

	public boolean hasPlayerUseItemStart() {
		return elPlayerUseItemStart.hasHandlers();
	}

	public void publishPlayerUseItemStart(PlayerUseItemStartEvent event) {
		elPlayerUseItemStart.publish(event);
	}

	// ##############################
	// ### PlayerUseItemTickEvent ###
	// ##############################

	private final EventList<PlayerUseItemTickEvent> elPlayerUseItemTick = new EventList<PlayerUseItemTickEvent>();

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
