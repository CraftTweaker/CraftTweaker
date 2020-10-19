package com.blamejared.crafttweaker.impl.actions.loot;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import java.util.Map;
import java.util.function.Supplier;

public abstract class ActionLootModifier implements IRuntimeAction {
    private final Supplier<Map<ResourceLocation, IGlobalLootModifier>> modifiersMapGetter;

    public ActionLootModifier(final Supplier<Map<ResourceLocation, IGlobalLootModifier>> modifiersMapGetter) {
        this.modifiersMapGetter = modifiersMapGetter;
    }

    public Map<ResourceLocation, IGlobalLootModifier> getModifiersMap() {
        return this.modifiersMapGetter.get();
    }

    @Override
    public boolean validate(final ILogger logger) {
        if (this.getModifiersMap() == null) {
            logger.throwingErr("Unable to modify loot modifier registry without access to it", new NullPointerException("Illegal loot modifiers registry getter"));
            return false;
        }
        if (this.getModifiersMap() instanceof ImmutableMap) {
            logger.throwingErr("Unable to modify loot modifier registry because it's frozen", new IllegalAccessError("Loot modifiers registry is frozen"));
            return false;
        }
        return true;
    }
}
