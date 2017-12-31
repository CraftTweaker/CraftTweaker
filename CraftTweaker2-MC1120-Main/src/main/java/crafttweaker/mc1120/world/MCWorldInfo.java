package crafttweaker.mc1120.world;

import crafttweaker.api.world.IWorldInfo;
import net.minecraft.world.storage.WorldInfo;

public class MCWorldInfo implements IWorldInfo {

    private final WorldInfo info;

    public MCWorldInfo(WorldInfo info) {
        this.info = info;
    }

    @Override
    public boolean isCommandsAllowed() {
        return info.areCommandsAllowed();
    }

    @Override
    public double getBorderCenterX() {
        return info.getBorderCenterX();
    }

    @Override
    public double getBorderCenterZ() {
        return info.getBorderCenterZ();
    }

    @Override
    public double getBorderDamagePerBlock() {
        return info.getBorderDamagePerBlock();
    }

    @Override
    public double getBoderLerpTarget() {
        return info.getBorderLerpTarget();
    }

    @Override
    public long getBoderLerpTime() {
        return info.getBorderLerpTime();
    }


    @Override
    public double getBorderSafeZone() {
        return info.getBorderSafeZone();
    }


    @Override
    public double getBorderSize() {
        return info.getBorderSize();
    }


    @Override
    public int getBorderWarningDistance() {
        return info.getBorderWarningDistance();
    }


    @Override
    public int getBorderWarningTime() {
        return info.getBorderWarningTime();
    }


    @Override
    public double getCleanWeatherTime() {
        return info.getCleanWeatherTime();
    }


    @Override
    public String getDifficulty() {
        return info.getDifficulty().toString();
    }


    @Override
    public String getGeneratorOptions() {
        return info.getGeneratorOptions();
    }


    @Override
    public long getLastTimePlayed() {
        return info.getLastTimePlayed();
    }


    @Override
    public int getRainTime() {
        return info.getRainTime();
    }


    @Override
    public int getSaveVersion() {
        return info.getSaveVersion();
    }


    @Override
    public long getSeed() {
        return info.getSeed();
    }


    @Override
    public int getSpawnX() {
        return info.getSpawnX();
    }


    @Override
    public int getSpawnY() {
        return info.getSpawnY();
    }


    @Override
    public int getSpawnZ() {
        return info.getSpawnZ();
    }


    @Override
    public int getThunderTime() {
        return info.getThunderTime();
    }


    @Override
    public int getVersionId() {
        return info.getVersionId();
    }


    @Override
    public String getVersionName() {
        return info.getVersionName();
    }


    @Override
    public String getWorldName() {
        return info.getWorldName();
    }


    @Override
    public long getWorldTotalTime() {
        return info.getWorldTotalTime();
    }

    @Override
    public boolean isDifficultyLocked() {
        return info.isDifficultyLocked();
    }

    @Override
    public boolean isHardcoreModeEnabled() {
        return info.isHardcoreModeEnabled();
    }

    @Override
    public boolean isInitialized() {
        return info.isInitialized();
    }

    @Override
    public boolean isMapFeaturesEnabled() {
        return info.isMapFeaturesEnabled();
    }

    @Override
    public boolean isThundering() {
        return info.isThundering();
    }

    @Override
    public boolean isVersionSnapshot() {
        return info.isVersionSnapshot();
    }

    @Override
    public Object getInternal() {
        return info;
    }


}
