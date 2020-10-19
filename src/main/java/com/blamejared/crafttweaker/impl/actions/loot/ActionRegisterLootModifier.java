package com.blamejared.crafttweaker.impl.actions.loot;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.api.loot.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import java.util.Map;
import java.util.function.Supplier;

public class ActionRegisterLootModifier extends ActionLootModifier {
    private final ResourceLocation name;
    private final Supplier<IGlobalLootModifier> glm;

    public ActionRegisterLootModifier(final ResourceLocation name, final ILootCondition[] conditions, final ILootModifier function,
                                      final Supplier<Map<ResourceLocation, IGlobalLootModifier>> mapGetter) {
        super(mapGetter);
        this.name = name;
        this.glm = () -> new CTLootModifier(this.name.toString(), conditions, function);
    }

    @Override
    public void apply() {
        this.getModifiersMap().put(this.name, this.glm.get());
    }

    @Override
    public String describe() {
        return "Registering loot modifier with name '" + this.name + "'";
    }

    @Override
    public boolean validate(ILogger logger) {
        if (!super.validate(logger)) return false;
        if (this.glm.get() == null) {
            logger.throwingErr("Unable to register a null loot modifier!", new NullPointerException("Null loot modifier"));
            return false;
        }
        if (this.getModifiersMap().get(this.name) != null) {
            final Throwable throwable = new IllegalStateException('\'' + this.name.toString() + "' is already in use");
            logger.throwingErr("Unable to register a loot modifier with name '" + this.name + "' since it already exists", throwable);
            return false;
        }
        return true;
    }
}
