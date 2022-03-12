package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.entity.AccessLightningBolt;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LightningBolt;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/misc/LightningBolt")
@NativeTypeRegistration(value = LightningBolt.class, zenCodeName = "crafttweaker.api.entity.type.misc.LightningBolt")
public class ExpandLightningBolt {
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("visualOnly")
    public static void setVisualOnly(LightningBolt internal, boolean visualOnly) {
        
        internal.setVisualOnly(visualOnly);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("visualOnly")
    public static boolean isVisualOnly(LightningBolt internal) {
        
        return ((AccessLightningBolt) internal).crafttweaker$isVisualOnly();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("cause")
    public static ServerPlayer getCause(LightningBolt internal) {
        
        return internal.getCause();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blocksSetOnFire")
    public static int getBlocksSetOnFire(LightningBolt internal) {
        
        return internal.getBlocksSetOnFire();
    }
    
    
}
