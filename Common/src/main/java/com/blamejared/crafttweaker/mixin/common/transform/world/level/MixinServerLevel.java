package com.blamejared.crafttweaker.mixin.common.transform.world.level;

import com.blamejared.crafttweaker.api.level.CraftTweakerSavedData;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedDataHolder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level implements CraftTweakerSavedDataHolder {
    
    @Shadow
    public abstract DimensionDataStorage getDataStorage();
    
    @Unique
    public CraftTweakerSavedData crafttweaker$crafttweakerSavedData;
    
    protected MixinServerLevel(WritableLevelData $$0, ResourceKey<Level> $$1, Holder<DimensionType> $$2, Supplier<ProfilerFiller> $$3, boolean $$4, boolean $$5, long $$6) {
        
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
    }
    
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void crafttwaeker$init(MinecraftServer $$0, Executor $$1, LevelStorageSource.LevelStorageAccess $$2, ServerLevelData $$3, ResourceKey<Level> $$4, Holder<DimensionType> $$5, ChunkProgressListener $$6, ChunkGenerator $$7, boolean $$8, long $$9, List $$10, boolean $$11, CallbackInfo ci) {
        
        this.crafttweaker$crafttweakerSavedData = this.getDataStorage()
                .computeIfAbsent(CraftTweakerSavedData::load, CraftTweakerSavedData::new, "crafttweaker_saved_data");
    }
    
    @Override
    public CraftTweakerSavedData crafttweaker$getSavedData() {
        
        return crafttweaker$crafttweakerSavedData;
    }
    
}
