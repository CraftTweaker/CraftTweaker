package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LevelEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This is not an event that you can listen to, this just holds fields for use in {@link ExpandLevelAccessor#levelEvent(LevelAccessor, int, BlockPos, int)}
 */
@ZenRegister
@Document("vanilla/api/world/LevelEventConstants")
@NativeTypeRegistration(value = LevelEvent.class, zenCodeName = "crafttweaker.api.world.LevelEventConstants")
public class ExpandLevelEventConstants {
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_DISPENSER_DISPENSE() {
        
        return LevelEvent.SOUND_DISPENSER_DISPENSE;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_DISPENSER_FAIL() {
        
        return LevelEvent.SOUND_DISPENSER_FAIL;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_DISPENSER_PROJECTILE_LAUNCH() {
        
        return LevelEvent.SOUND_DISPENSER_PROJECTILE_LAUNCH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ENDER_EYE_LAUNCH() {
        
        return LevelEvent.SOUND_ENDER_EYE_LAUNCH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_FIREWORK_SHOOT() {
        
        return LevelEvent.SOUND_FIREWORK_SHOOT;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_EXTINGUISH_FIRE() {
        
        return LevelEvent.SOUND_EXTINGUISH_FIRE;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_PLAY_RECORDING() {
        
        return LevelEvent.SOUND_PLAY_RECORDING;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_GHAST_WARNING() {
        
        return LevelEvent.SOUND_GHAST_WARNING;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_GHAST_FIREBALL() {
        
        return LevelEvent.SOUND_GHAST_FIREBALL;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_DRAGON_FIREBALL() {
        
        return LevelEvent.SOUND_DRAGON_FIREBALL;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_BLAZE_FIREBALL() {
        
        return LevelEvent.SOUND_BLAZE_FIREBALL;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ZOMBIE_WOODEN_DOOR() {
        
        return LevelEvent.SOUND_ZOMBIE_WOODEN_DOOR;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ZOMBIE_IRON_DOOR() {
        
        return LevelEvent.SOUND_ZOMBIE_IRON_DOOR;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ZOMBIE_DOOR_CRASH() {
        
        return LevelEvent.SOUND_ZOMBIE_DOOR_CRASH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_WITHER_BLOCK_BREAK() {
        
        return LevelEvent.SOUND_WITHER_BLOCK_BREAK;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_WITHER_BOSS_SPAWN() {
        
        return LevelEvent.SOUND_WITHER_BOSS_SPAWN;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_WITHER_BOSS_SHOOT() {
        
        return LevelEvent.SOUND_WITHER_BOSS_SHOOT;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_BAT_LIFTOFF() {
        
        return LevelEvent.SOUND_BAT_LIFTOFF;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ZOMBIE_INFECTED() {
        
        return LevelEvent.SOUND_ZOMBIE_INFECTED;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ZOMBIE_CONVERTED() {
        
        return LevelEvent.SOUND_ZOMBIE_CONVERTED;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_DRAGON_DEATH() {
        
        return LevelEvent.SOUND_DRAGON_DEATH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ANVIL_BROKEN() {
        
        return LevelEvent.SOUND_ANVIL_BROKEN;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ANVIL_USED() {
        
        return LevelEvent.SOUND_ANVIL_USED;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ANVIL_LAND() {
        
        return LevelEvent.SOUND_ANVIL_LAND;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_PORTAL_TRAVEL() {
        
        return LevelEvent.SOUND_PORTAL_TRAVEL;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_CHORUS_GROW() {
        
        return LevelEvent.SOUND_CHORUS_GROW;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_CHORUS_DEATH() {
        
        return LevelEvent.SOUND_CHORUS_DEATH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_BREWING_STAND_BREW() {
        
        return LevelEvent.SOUND_BREWING_STAND_BREW;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_END_PORTAL_SPAWN() {
        
        return LevelEvent.SOUND_END_PORTAL_SPAWN;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_PHANTOM_BITE() {
        
        return LevelEvent.SOUND_PHANTOM_BITE;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_ZOMBIE_TO_DROWNED() {
        
        return LevelEvent.SOUND_ZOMBIE_TO_DROWNED;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_HUSK_TO_ZOMBIE() {
        
        return LevelEvent.SOUND_HUSK_TO_ZOMBIE;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_GRINDSTONE_USED() {
        
        return LevelEvent.SOUND_GRINDSTONE_USED;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_PAGE_TURN() {
        
        return LevelEvent.SOUND_PAGE_TURN;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_SMITHING_TABLE_USED() {
        
        return LevelEvent.SOUND_SMITHING_TABLE_USED;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_POINTED_DRIPSTONE_LAND() {
        
        return LevelEvent.SOUND_POINTED_DRIPSTONE_LAND;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_DRIP_LAVA_INTO_CAULDRON() {
        
        return LevelEvent.SOUND_DRIP_LAVA_INTO_CAULDRON;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_DRIP_WATER_INTO_CAULDRON() {
        
        return LevelEvent.SOUND_DRIP_WATER_INTO_CAULDRON;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int SOUND_SKELETON_TO_STRAY() {
        
        return LevelEvent.SOUND_SKELETON_TO_STRAY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int COMPOSTER_FILL() {
        
        return LevelEvent.COMPOSTER_FILL;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int LAVA_FIZZ() {
        
        return LevelEvent.LAVA_FIZZ;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int REDSTONE_TORCH_BURNOUT() {
        
        return LevelEvent.REDSTONE_TORCH_BURNOUT;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int END_PORTAL_FRAME_FILL() {
        
        return LevelEvent.END_PORTAL_FRAME_FILL;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int DRIPSTONE_DRIP() {
        
        return LevelEvent.DRIPSTONE_DRIP;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_AND_SOUND_PLANT_GROWTH() {
        
        return LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_SHOOT() {
        
        return LevelEvent.PARTICLES_SHOOT;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_DESTROY_BLOCK() {
        
        return LevelEvent.PARTICLES_DESTROY_BLOCK;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_SPELL_POTION_SPLASH() {
        
        return LevelEvent.PARTICLES_SPELL_POTION_SPLASH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_EYE_OF_ENDER_DEATH() {
        
        return LevelEvent.PARTICLES_EYE_OF_ENDER_DEATH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_MOBBLOCK_SPAWN() {
        
        return LevelEvent.PARTICLES_MOBBLOCK_SPAWN;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_PLANT_GROWTH() {
        
        return LevelEvent.PARTICLES_PLANT_GROWTH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_DRAGON_FIREBALL_SPLASH() {
        
        return LevelEvent.PARTICLES_DRAGON_FIREBALL_SPLASH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_INSTANT_POTION_SPLASH() {
        
        return LevelEvent.PARTICLES_INSTANT_POTION_SPLASH;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_DRAGON_BLOCK_BREAK() {
        
        return LevelEvent.PARTICLES_DRAGON_BLOCK_BREAK;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_WATER_EVAPORATING() {
        
        return LevelEvent.PARTICLES_WATER_EVAPORATING;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int ANIMATION_END_GATEWAY_SPAWN() {
        
        return LevelEvent.ANIMATION_END_GATEWAY_SPAWN;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int ANIMATION_DRAGON_SUMMON_ROAR() {
        
        return LevelEvent.ANIMATION_DRAGON_SUMMON_ROAR;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_ELECTRIC_SPARK() {
        
        return LevelEvent.PARTICLES_ELECTRIC_SPARK;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_AND_SOUND_WAX_ON() {
        
        return LevelEvent.PARTICLES_AND_SOUND_WAX_ON;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_WAX_OFF() {
        
        return LevelEvent.PARTICLES_WAX_OFF;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_SCRAPE() {
        
        return LevelEvent.PARTICLES_SCRAPE;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_SCULK_CHARGE() {
        
        return LevelEvent.PARTICLES_SCULK_CHARGE;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int PARTICLES_SCULK_SHRIEK() {
        
        return LevelEvent.PARTICLES_SCULK_SHRIEK;
    }
    
}
