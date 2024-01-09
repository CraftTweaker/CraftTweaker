package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LevelEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This is not an event that you can listen to, this just holds fields for use in {@link LevelAccessor#levelEvent(int, BlockPos, int)}
 */
@ZenRegister
@Document("vanilla/api/world/LevelEventConstants")
@ZenCodeType.Name("crafttweaker.api.world.LevelEventConstants")
public class ExpandLevelEventConstants {
    
    @ZenCodeType.Field
    public static final int SOUND_DISPENSER_DISPENSE = LevelEvent.SOUND_DISPENSER_DISPENSE;
    @ZenCodeType.Field
    public static final int SOUND_DISPENSER_FAIL = LevelEvent.SOUND_DISPENSER_FAIL;
    @ZenCodeType.Field
    public static final int SOUND_DISPENSER_PROJECTILE_LAUNCH = LevelEvent.SOUND_DISPENSER_PROJECTILE_LAUNCH;
    @ZenCodeType.Field
    public static final int SOUND_ENDER_EYE_LAUNCH = LevelEvent.SOUND_ENDER_EYE_LAUNCH;
    @ZenCodeType.Field
    public static final int SOUND_FIREWORK_SHOOT = LevelEvent.SOUND_FIREWORK_SHOOT;
    @ZenCodeType.Field
    public static final int SOUND_EXTINGUISH_FIRE = LevelEvent.SOUND_EXTINGUISH_FIRE;
    @ZenCodeType.Field
    public static final int SOUND_PLAY_JUKEBOX_SONG = LevelEvent.SOUND_PLAY_JUKEBOX_SONG;
    @ZenCodeType.Field
    public static final int SOUND_STOP_JUKEBOX_SONG = LevelEvent.SOUND_STOP_JUKEBOX_SONG;
    @ZenCodeType.Field
    public static final int SOUND_GHAST_WARNING = LevelEvent.SOUND_GHAST_WARNING;
    @ZenCodeType.Field
    public static final int SOUND_GHAST_FIREBALL = LevelEvent.SOUND_GHAST_FIREBALL;
    @ZenCodeType.Field
    public static final int SOUND_DRAGON_FIREBALL = LevelEvent.SOUND_DRAGON_FIREBALL;
    @ZenCodeType.Field
    public static final int SOUND_BLAZE_FIREBALL = LevelEvent.SOUND_BLAZE_FIREBALL;
    @ZenCodeType.Field
    public static final int SOUND_ZOMBIE_WOODEN_DOOR = LevelEvent.SOUND_ZOMBIE_WOODEN_DOOR;
    @ZenCodeType.Field
    public static final int SOUND_ZOMBIE_IRON_DOOR = LevelEvent.SOUND_ZOMBIE_IRON_DOOR;
    @ZenCodeType.Field
    public static final int SOUND_ZOMBIE_DOOR_CRASH = LevelEvent.SOUND_ZOMBIE_DOOR_CRASH;
    @ZenCodeType.Field
    public static final int SOUND_WITHER_BLOCK_BREAK = LevelEvent.SOUND_WITHER_BLOCK_BREAK;
    @ZenCodeType.Field
    public static final int SOUND_WITHER_BOSS_SPAWN = LevelEvent.SOUND_WITHER_BOSS_SPAWN;
    @ZenCodeType.Field
    public static final int SOUND_WITHER_BOSS_SHOOT = LevelEvent.SOUND_WITHER_BOSS_SHOOT;
    @ZenCodeType.Field
    public static final int SOUND_BAT_LIFTOFF = LevelEvent.SOUND_BAT_LIFTOFF;
    @ZenCodeType.Field
    public static final int SOUND_ZOMBIE_INFECTED = LevelEvent.SOUND_ZOMBIE_INFECTED;
    @ZenCodeType.Field
    public static final int SOUND_ZOMBIE_CONVERTED = LevelEvent.SOUND_ZOMBIE_CONVERTED;
    @ZenCodeType.Field
    public static final int SOUND_DRAGON_DEATH = LevelEvent.SOUND_DRAGON_DEATH;
    @ZenCodeType.Field
    public static final int SOUND_ANVIL_BROKEN = LevelEvent.SOUND_ANVIL_BROKEN;
    @ZenCodeType.Field
    public static final int SOUND_ANVIL_USED = LevelEvent.SOUND_ANVIL_USED;
    @ZenCodeType.Field
    public static final int SOUND_ANVIL_LAND = LevelEvent.SOUND_ANVIL_LAND;
    @ZenCodeType.Field
    public static final int SOUND_PORTAL_TRAVEL = LevelEvent.SOUND_PORTAL_TRAVEL;
    @ZenCodeType.Field
    public static final int SOUND_CHORUS_GROW = LevelEvent.SOUND_CHORUS_GROW;
    @ZenCodeType.Field
    public static final int SOUND_CHORUS_DEATH = LevelEvent.SOUND_CHORUS_DEATH;
    @ZenCodeType.Field
    public static final int SOUND_BREWING_STAND_BREW = LevelEvent.SOUND_BREWING_STAND_BREW;
    @ZenCodeType.Field
    public static final int SOUND_END_PORTAL_SPAWN = LevelEvent.SOUND_END_PORTAL_SPAWN;
    @ZenCodeType.Field
    public static final int SOUND_PHANTOM_BITE = LevelEvent.SOUND_PHANTOM_BITE;
    @ZenCodeType.Field
    public static final int SOUND_ZOMBIE_TO_DROWNED = LevelEvent.SOUND_ZOMBIE_TO_DROWNED;
    @ZenCodeType.Field
    public static final int SOUND_HUSK_TO_ZOMBIE = LevelEvent.SOUND_HUSK_TO_ZOMBIE;
    @ZenCodeType.Field
    public static final int SOUND_GRINDSTONE_USED = LevelEvent.SOUND_GRINDSTONE_USED;
    @ZenCodeType.Field
    public static final int SOUND_PAGE_TURN = LevelEvent.SOUND_PAGE_TURN;
    @ZenCodeType.Field
    public static final int SOUND_SMITHING_TABLE_USED = LevelEvent.SOUND_SMITHING_TABLE_USED;
    @ZenCodeType.Field
    public static final int SOUND_POINTED_DRIPSTONE_LAND = LevelEvent.SOUND_POINTED_DRIPSTONE_LAND;
    @ZenCodeType.Field
    public static final int SOUND_DRIP_LAVA_INTO_CAULDRON = LevelEvent.SOUND_DRIP_LAVA_INTO_CAULDRON;
    @ZenCodeType.Field
    public static final int SOUND_DRIP_WATER_INTO_CAULDRON = LevelEvent.SOUND_DRIP_WATER_INTO_CAULDRON;
    @ZenCodeType.Field
    public static final int SOUND_SKELETON_TO_STRAY = LevelEvent.SOUND_SKELETON_TO_STRAY;
    @ZenCodeType.Field
    public static final int SOUND_CRAFTER_CRAFT = LevelEvent.SOUND_CRAFTER_CRAFT;
    @ZenCodeType.Field
    public static final int SOUND_CRAFTER_FAIL = LevelEvent.SOUND_CRAFTER_FAIL;
    @ZenCodeType.Field
    public static final int COMPOSTER_FILL = LevelEvent.COMPOSTER_FILL;
    @ZenCodeType.Field
    public static final int LAVA_FIZZ = LevelEvent.LAVA_FIZZ;
    @ZenCodeType.Field
    public static final int REDSTONE_TORCH_BURNOUT = LevelEvent.REDSTONE_TORCH_BURNOUT;
    @ZenCodeType.Field
    public static final int END_PORTAL_FRAME_FILL = LevelEvent.END_PORTAL_FRAME_FILL;
    @ZenCodeType.Field
    public static final int DRIPSTONE_DRIP = LevelEvent.DRIPSTONE_DRIP;
    @ZenCodeType.Field
    public static final int PARTICLES_AND_SOUND_PLANT_GROWTH = LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH;
    @ZenCodeType.Field
    public static final int PARTICLES_SHOOT_SMOKE = LevelEvent.PARTICLES_SHOOT_SMOKE;
    @ZenCodeType.Field
    public static final int PARTICLES_DESTROY_BLOCK = LevelEvent.PARTICLES_DESTROY_BLOCK;
    @ZenCodeType.Field
    public static final int PARTICLES_SPELL_POTION_SPLASH = LevelEvent.PARTICLES_SPELL_POTION_SPLASH;
    @ZenCodeType.Field
    public static final int PARTICLES_EYE_OF_ENDER_DEATH = LevelEvent.PARTICLES_EYE_OF_ENDER_DEATH;
    @ZenCodeType.Field
    public static final int PARTICLES_MOBBLOCK_SPAWN = LevelEvent.PARTICLES_MOBBLOCK_SPAWN;
    @ZenCodeType.Field
    public static final int PARTICLES_PLANT_GROWTH = LevelEvent.PARTICLES_PLANT_GROWTH;
    @ZenCodeType.Field
    public static final int PARTICLES_DRAGON_FIREBALL_SPLASH = LevelEvent.PARTICLES_DRAGON_FIREBALL_SPLASH;
    @ZenCodeType.Field
    public static final int PARTICLES_INSTANT_POTION_SPLASH = LevelEvent.PARTICLES_INSTANT_POTION_SPLASH;
    @ZenCodeType.Field
    public static final int PARTICLES_DRAGON_BLOCK_BREAK = LevelEvent.PARTICLES_DRAGON_BLOCK_BREAK;
    @ZenCodeType.Field
    public static final int PARTICLES_WATER_EVAPORATING = LevelEvent.PARTICLES_WATER_EVAPORATING;
    @ZenCodeType.Field
    public static final int PARTICLES_SHOOT_WHITE_SMOKE = LevelEvent.PARTICLES_SHOOT_WHITE_SMOKE;
    @ZenCodeType.Field
    public static final int ANIMATION_END_GATEWAY_SPAWN = LevelEvent.ANIMATION_END_GATEWAY_SPAWN;
    @ZenCodeType.Field
    public static final int ANIMATION_DRAGON_SUMMON_ROAR = LevelEvent.ANIMATION_DRAGON_SUMMON_ROAR;
    @ZenCodeType.Field
    public static final int PARTICLES_ELECTRIC_SPARK = LevelEvent.PARTICLES_ELECTRIC_SPARK;
    @ZenCodeType.Field
    public static final int PARTICLES_AND_SOUND_WAX_ON = LevelEvent.PARTICLES_AND_SOUND_WAX_ON;
    @ZenCodeType.Field
    public static final int PARTICLES_WAX_OFF = LevelEvent.PARTICLES_WAX_OFF;
    @ZenCodeType.Field
    public static final int PARTICLES_SCRAPE = LevelEvent.PARTICLES_SCRAPE;
    @ZenCodeType.Field
    public static final int PARTICLES_SCULK_CHARGE = LevelEvent.PARTICLES_SCULK_CHARGE;
    @ZenCodeType.Field
    public static final int PARTICLES_SCULK_SHRIEK = LevelEvent.PARTICLES_SCULK_SHRIEK;
    @ZenCodeType.Field
    public static final int PARTICLES_AND_SOUND_BRUSH_BLOCK_COMPLETE = LevelEvent.PARTICLES_AND_SOUND_BRUSH_BLOCK_COMPLETE;
    @ZenCodeType.Field
    public static final int PARTICLES_EGG_CRACK = LevelEvent.PARTICLES_EGG_CRACK;
    @ZenCodeType.Field
    public static final int PARTICLES_GUST_DUST = LevelEvent.PARTICLES_GUST_DUST;
    @ZenCodeType.Field
    public static final int PARTICLES_TRIAL_SPAWNER_SPAWN = LevelEvent.PARTICLES_TRIAL_SPAWNER_SPAWN;
    @ZenCodeType.Field
    public static final int PARTICLES_TRIAL_SPAWNER_SPAWN_MOB_AT = LevelEvent.PARTICLES_TRIAL_SPAWNER_SPAWN_MOB_AT;
    @ZenCodeType.Field
    public static final int PARTICLES_TRIAL_SPAWNER_DETECT_PLAYER = LevelEvent.PARTICLES_TRIAL_SPAWNER_DETECT_PLAYER;
    @ZenCodeType.Field
    public static final int ANIMATION_TRIAL_SPAWNER_EJECT_ITEM = LevelEvent.ANIMATION_TRIAL_SPAWNER_EJECT_ITEM;
    
}
