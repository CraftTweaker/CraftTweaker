package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IWorldInfo")
@ZenRegister
public interface IWorldInfo {
    
    @ZenMethod
    @ZenGetter("commandsAllowed")
    boolean isCommandsAllowed();
    
    @ZenMethod
    @ZenGetter("borderCenterX")
    double getBorderCenterX();
    
    @ZenMethod
    @ZenGetter("borderCenterZ")
    double getBorderCenterZ();
    
    
    @ZenMethod
    @ZenGetter("borderDamagePerBlock")
    double getBorderDamagePerBlock();
    
    
    @ZenMethod
    @ZenGetter("borderSafeZone")
    double getBorderSafeZone();
    
    
    @ZenMethod
    @ZenGetter("borderSize")
    double getBorderSize();
    
    
    @ZenMethod
    @ZenGetter("borderWarningDistance")
    int getBorderWarningDistance();
    
    
    @ZenMethod
    @ZenGetter("borderWarningTime")
    int getBorderWarningTime();
    
    
    @ZenMethod
    @ZenGetter("cleanWeatherTime")
    double getCleanWeatherTime();
    
    
    @ZenMethod
    @ZenGetter("difficulty")
    String getDifficulty();
    
    
    @ZenMethod
    @ZenGetter("generatorOptions")
    String getGeneratorOptions();
    
    
    @ZenMethod
    @ZenGetter("lastTimePlayed")
    long getLastTimePlayed();
    
    
    @ZenMethod
    @ZenGetter("rainTime")
    int getRainTime();
    
    
    @ZenMethod
    @ZenGetter("saveVersion")
    int getSaveVersion();
    
    
    @ZenMethod
    @ZenGetter("seed")
    long getSeed();
    
    
    @ZenMethod
    @ZenGetter("spawnX")
    int getSpawnX();
    
    
    @ZenMethod
    @ZenGetter("spawnY")
    int getSpawnY();
    
    
    @ZenMethod
    @ZenGetter("spawnZ")
    int getSpawnZ();
    
    
    @ZenMethod
    @ZenGetter("thunderTime")
    int getThunderTime();
    
    
    @ZenMethod
    @ZenGetter("versionId")
    int getVersionId();
    
    
    @ZenMethod
    @ZenGetter("versionName")
    String getVersionName();
    
    
    @ZenMethod
    @ZenGetter("worldName")
    String getWorldName();
    
    
    @ZenMethod
    @ZenGetter("worldTotalTime")
    long getWorldTotalTime();
    
    
    @ZenMethod
    @ZenGetter("boderLerpTarget")
    double getBoderLerpTarget();
    
    
    @ZenMethod
    @ZenGetter("boderLerpTime")
    long getBoderLerpTime();
    
    @ZenMethod
    @ZenGetter("difficultyLocked")
    boolean isDifficultyLocked();
    
    @ZenMethod
    @ZenGetter("hardcoreModeEnabled")
    boolean isHardcoreModeEnabled();
    
    @ZenMethod
    @ZenGetter("initialized")
    boolean isInitialized();
    
    @ZenMethod
    @ZenGetter("mapFeaturesEnabled")
    boolean isMapFeaturesEnabled();
    
    @ZenMethod
    @ZenGetter("thundering")
    boolean isThundering();
    
    @ZenMethod
    @ZenGetter("versionSnapshot")
    boolean isVersionSnapshot();
    
    Object getInternal();
    
}
