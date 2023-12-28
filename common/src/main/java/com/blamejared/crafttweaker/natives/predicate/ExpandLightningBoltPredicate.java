package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LightningBoltPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;

@ZenRegister
@Document("vanilla/api/predicate/LightningBoltPredicate")
@NativeTypeRegistration(value = LightningBoltPredicate.class, zenCodeName = "crafttweaker.api.predicate.LightningBoltPredicate")
public final class ExpandLightningBoltPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static LightningBoltPredicate create(final MinMaxBounds.Ints blocksSetOnFire) {
        
        return LightningBoltPredicate.blockSetOnFire(blocksSetOnFire);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LightningBoltPredicate create(final EntityPredicate struckEntity) {
        
        return create(MinMaxBounds.Ints.ANY, struckEntity);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LightningBoltPredicate create(final MinMaxBounds.Ints blocksSetOnFire, final EntityPredicate struckEntity) {
        
        return new LightningBoltPredicate(blocksSetOnFire, Optional.of(struckEntity));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LightningBoltPredicate create(final MinMaxBounds.Ints blockSetOnFire, final EntityPredicate.Builder struckEntity) {
        
        return create(blockSetOnFire, struckEntity.build());
    }
    
}
