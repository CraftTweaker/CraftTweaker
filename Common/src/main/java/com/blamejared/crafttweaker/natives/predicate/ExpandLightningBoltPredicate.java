package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.predicate.AccessLightningBoltPredicate;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LighthingBoltPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/LightningBoltPredicate")
@NativeTypeRegistration(value = LighthingBoltPredicate.class, zenCodeName = "crafttweaker.api.predicate.LightningBoltPredicate")
public final class ExpandLightningBoltPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static LighthingBoltPredicate create(final MinMaxBounds.Ints blocksSetOnFire) {
        
        return LighthingBoltPredicate.blockSetOnFire(blocksSetOnFire);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LighthingBoltPredicate create(final EntityPredicate struckEntity) {
        
        return create(MinMaxBounds.Ints.ANY, struckEntity);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LighthingBoltPredicate create(final MinMaxBounds.Ints blocksSetOnFire, final EntityPredicate struckEntity) {
        
        return AccessLightningBoltPredicate.crafttweaker$of(blocksSetOnFire, struckEntity);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LighthingBoltPredicate create(final MinMaxBounds.Ints blockSetOnFire, final EntityPredicate.Builder struckEntity) {
        
        return create(blockSetOnFire, struckEntity.build());
    }
    
}
