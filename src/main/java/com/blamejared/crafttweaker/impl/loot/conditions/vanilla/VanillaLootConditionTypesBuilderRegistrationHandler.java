package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;

public final class VanillaLootConditionTypesBuilderRegistrationHandler {
    public static void registerVanillaLootConditionTypes() {
        CTLootConditionBuilder.register(AlternativeLootConditionTypeBuilder.class, AlternativeLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(BlockStatePropertyLootConditionTypeBuilder.class, BlockStatePropertyLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(DamageSourcePropertiesLootConditionTypeBuilder.class, DamageSourcePropertiesLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(EntityPropertiesLootConditionTypeBuilder.class, EntityPropertiesLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(EntityScoresLootConditionTypeBuilder.class, EntityScoresLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(InvertedLootConditionTypeBuilder.class, InvertedLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(KilledByPlayerLootConditionTypeBuilder.class, () -> KilledByPlayerLootConditionTypeBuilder.INSTANCE);
        CTLootConditionBuilder.register(LocationCheckLootConditionTypeBuilder.class, LocationCheckLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(LootTableIdLootConditionTypeBuilder.class, LootTableIdLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(MatchToolLootConditionBuilder.class, MatchToolLootConditionBuilder::new);
        CTLootConditionBuilder.register(RandomChanceLootConditionBuilder.class, RandomChanceLootConditionBuilder::new);
        CTLootConditionBuilder.register(RandomChanceWithLootingLootConditionBuilder.class, RandomChanceWithLootingLootConditionBuilder::new);
        CTLootConditionBuilder.register(ReferenceLootConditionTypeBuilder.class, ReferenceLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(SurvivesExplosionLootConditionTypeBuilder.class, () -> SurvivesExplosionLootConditionTypeBuilder.INSTANCE);
        CTLootConditionBuilder.register(TableBonusLootConditionTypeBuilder.class, TableBonusLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(TimeCheckLootConditionTypeBuilder.class, TimeCheckLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(WeatherCheckLootConditionTypeBuilder.class, WeatherCheckLootConditionTypeBuilder::new);
    }
}
