package com.blamejared.crafttweaker.api.action.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.Supplier;

public class ActionRegisterLootModifier extends ActionLootModifier {
    private final ResourceLocation name;
    private final Supplier<ILootModifier> modifier;
    
    public ActionRegisterLootModifier(final ResourceLocation name, final Supplier<ILootModifier> modifierCreator,
                                      final Supplier<Map<ResourceLocation, ILootModifier>> mapGetter) {
        super(mapGetter);
        this.name = name;
        this.modifier = Suppliers.memoize(modifierCreator::get);
    }
    
    @Override
    public void apply() {
        
        this.modifiersMap().put(this.name, this.modifier.get());
    }
    
    @Override
    public String describe() {
        
        return "Registering loot modifier with name '" + this.name + "'";
    }
    
    @Override
    public boolean validate(final Logger logger) {
        
        if (!super.validate(logger)) {
            return false;
        }
        
        if (this.modifier.get() == null) {
            logger.error("Unable to register a null loot modifier", new NullPointerException("Null loot modifier"));
            return false;
        }
        
        if (this.modifiersMap().containsKey(this.name)) {
            final Throwable throwable = new IllegalStateException('\'' + this.name.toString() + "' is already in use");
            logger.error("Unable to register a loot modifier with name '" + this.name + "' since it already exists", throwable);
            return false;
        }
        
        return true;
    }
    
}
