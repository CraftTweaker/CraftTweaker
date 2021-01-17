package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;

public final class CraftTweakerLootConditionTypeBuilderRegistrationHandler {
    public static void registerCraftTweakerLootConditionTypes() {
        CTLootConditionBuilder.register(BlockStateLootConditionTypeBuilder.class, BlockStateLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(DelegateLootConditionTypeBuilder.class, DelegateLootConditionTypeBuilder::new);
        CTLootConditionBuilder.register(JsonLootConditionTypeBuilder.class, JsonLootConditionTypeBuilder::new);
    }
}
