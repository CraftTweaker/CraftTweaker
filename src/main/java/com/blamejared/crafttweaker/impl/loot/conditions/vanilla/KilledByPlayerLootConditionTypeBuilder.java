package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.loot.LootParameters;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.KilledByPlayer")
@Document("vanilla/api/loot/conditions/vanilla/KilledByPlayer")
public final class KilledByPlayerLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    static final KilledByPlayerLootConditionTypeBuilder INSTANCE = new KilledByPlayerLootConditionTypeBuilder();
    private static final ILootCondition KILLED_BY_PLAYER = context -> context.has(LootParameters.LAST_DAMAGE_PLAYER);

    private KilledByPlayerLootConditionTypeBuilder() {}

    @Override
    public ILootCondition finish() {
        return KILLED_BY_PLAYER;
    }
}
