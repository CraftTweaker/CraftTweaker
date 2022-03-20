package com.blamejared.crafttweaker.api.tag.manager.factory;

import com.blamejared.crafttweaker.api.annotation.TaggableElementManagerFactory;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.tag.manager.type.EntityTypeTagManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

@ZenRegister
@TaggableElementManagerFactory("minecraft:entity_type")
public class EntityTypeTagFactory implements TagManagerFactory<EntityType<Entity>, EntityTypeTagManager> {
    
    @Override
    public EntityTypeTagManager apply(ResourceKey<? extends Registry<EntityType<Entity>>> resourceKey, Class<EntityType<Entity>> entityTypeClass) {
        
        return new EntityTypeTagManager(resourceKey, entityTypeClass);
    }
    
}
