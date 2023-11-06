package com.blamejared.crafttweaker.natives.entity.type.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/player/ServerPlayer")
@NativeTypeRegistration(value = ServerPlayer.class, zenCodeName = "crafttweaker.api.entity.type.player.ServerPlayer")
public class ExpandServerPlayer {
    
    /**
     * Gets the {@link PlayerAdvancements} for this player.
     *
     * @return The {@link PlayerAdvancements} for this player.
     */
    @ZenCodeType.Getter("advancements")
    public static PlayerAdvancements getAdvancements(ServerPlayer internal) {
        
        return internal.getAdvancements();
    }
    
    /**
     * Gets the persistent NBT data for this player.
     *
     * <p>Persistent data is kept through deaths</p>
     *
     * @return The persistent data for this Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("persistentData")
    public static MapData getPersistentData(ServerPlayer internal) {
        
        return new MapData(Services.PLATFORM.getPersistentData(internal));
    }
    
    /**
     * Updates the persistent NBT data for this player.
     *
     * <p>Persistent data is kept through deaths</p>
     *
     * @param data The custom data to store.
     *
     * @docParam data {custom: "data"}
     */
    @ZenCodeType.Method
    public static void updatePersistentData(ServerPlayer internal, MapData data) {
        
        CompoundTag persistentData = Services.PLATFORM.getPersistentData(internal);
        persistentData.merge(data.getInternal());
    }
    
}
