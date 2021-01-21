package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.SurvivesExplosion")
@Document("vanilla/api/loot/conditions/vanilla/SurvivesExplosion")
public final class SurvivesExplosionLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    static final SurvivesExplosionLootConditionTypeBuilder INSTANCE = new SurvivesExplosionLootConditionTypeBuilder();
    private static final ILootCondition SURVIVES_EXPLOSION = context -> {
        final float radius = ExpandLootContext.getExplosionRadius(context);
        return radius > 0.0F && context.getRandom().nextFloat() <= (1.0F / radius);
    };

    private SurvivesExplosionLootConditionTypeBuilder() {}

    @Override
    public ILootCondition finish() {
        return SURVIVES_EXPLOSION;
    }
}
