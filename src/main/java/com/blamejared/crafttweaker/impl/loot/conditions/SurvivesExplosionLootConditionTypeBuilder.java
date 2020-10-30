package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.SurvivesExplosion")
@Document("vanilla/api/loot/conditions/SurvivesExplosion")
public final class SurvivesExplosionLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    static final SurvivesExplosionLootConditionTypeBuilder INSTANCE = new SurvivesExplosionLootConditionTypeBuilder();
    private static final ILootCondition SURVIVES_EXPLOSION = context -> {
        final float radius = context.getExplosionRadius();
        return radius > 0.0F && context.getInternal().getRandom().nextFloat() <= (1.0F / radius);
    };

    private SurvivesExplosionLootConditionTypeBuilder() {}

    @Override
    public ILootCondition finish() {
        return SURVIVES_EXPLOSION;
    }
}
