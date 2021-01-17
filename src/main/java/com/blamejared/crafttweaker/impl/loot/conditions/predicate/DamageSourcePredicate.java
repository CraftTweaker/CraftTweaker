package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.conditions.TriState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.DamageSourcePredicate")
@Document("vanilla/api/loot/conditions/predicate/DamageSourcePredicate")
public final class DamageSourcePredicate {
    private TriState bypassesArmor;
    private TriState bypassesInvulnerability;
    private TriState bypassesMagic;
    private TriState isExplosion;
    private TriState isFire;
    private TriState isMagic;
    private TriState isProjectile;
    private TriState isLightning;
    private EntityPredicate directEntity;
    private EntityPredicate sourceEntity;

    public DamageSourcePredicate() {
        this.bypassesArmor = TriState.UNSET;
        this.bypassesInvulnerability = TriState.UNSET;
        this.bypassesMagic = TriState.UNSET;
        this.isExplosion = TriState.UNSET;
        this.isFire = TriState.UNSET;
        this.isMagic = TriState.UNSET;
        this.isProjectile = TriState.UNSET;
        this.isLightning = TriState.UNSET;
        this.directEntity = new EntityPredicate();
        this.sourceEntity = new EntityPredicate();
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withArmorBypass() {
        this.bypassesArmor = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutArmorBypass() {
        this.bypassesArmor = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withInvulnerabilityBypass() {
        this.bypassesInvulnerability = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutInvulnerabilityBypass() {
        this.bypassesInvulnerability = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withMagicBypass() {
        this.bypassesMagic = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutMagicBypass() {
        this.bypassesMagic = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withExplosionSource() {
        this.isExplosion = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutExplosionSource() {
        this.isExplosion = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withFireSource() {
        this.isFire = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutFireSource() {
        this.isFire = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withMagicSource() {
        this.isMagic = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutMagicSource() {
        this.isMagic = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withProjectileSource() {
        this.isProjectile = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutProjectileSource() {
        this.isProjectile = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withLightningSource() {
        this.isLightning = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withoutLightningSource() {
        this.isLightning = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withDirectEntityPredicate(final Consumer<EntityPredicate> builder) {
        final EntityPredicate directEntity = new EntityPredicate();
        builder.accept(directEntity);
        this.directEntity = directEntity;
        return this;
    }

    @ZenCodeType.Method
    public DamageSourcePredicate withSourceEntityPredicate(final Consumer<EntityPredicate> builder) {
        final EntityPredicate sourceEntity = new EntityPredicate();
        builder.accept(sourceEntity);
        this.sourceEntity = sourceEntity;
        return this;
    }

    boolean isAny() {
        return this.bypassesArmor == TriState.UNSET && this.bypassesInvulnerability == TriState.UNSET && this.bypassesMagic == TriState.UNSET
                && this.isExplosion == TriState.UNSET && this.isFire == TriState.UNSET && this.isMagic == TriState.UNSET
                && this.isProjectile == TriState.UNSET && this.isLightning == TriState.UNSET && this.directEntity.isAny()
                && this.sourceEntity.isAny();
    }

    public net.minecraft.advancements.criterion.DamageSourcePredicate toVanilla() {
        if (this.isAny()) return net.minecraft.advancements.criterion.DamageSourcePredicate.ANY;

        return new net.minecraft.advancements.criterion.DamageSourcePredicate(
                this.isProjectile.toBoolean(),
                this.isExplosion.toBoolean(),
                this.bypassesArmor.toBoolean(),
                this.bypassesInvulnerability.toBoolean(),
                this.bypassesMagic.toBoolean(),
                this.isFire.toBoolean(),
                this.isMagic.toBoolean(),
                this.isLightning.toBoolean(),
                this.directEntity.toVanilla(),
                this.sourceEntity.toVanilla()
        );
    }
}
