package com.blamejared.crafttweaker.api.tag.manager.factory;

import com.blamejared.crafttweaker.api.tag.manager.type.EntityTypeTagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityTypeTagFactory implements TagManagerFactory<EntityType<Entity>, EntityTypeTagManager> {
    
    @Override
    public EntityTypeTagManager apply(ResourceKey<? extends Registry<EntityType<Entity>>> resourceKey, Class<EntityType<Entity>> entityTypeClass) {
        
        return new EntityTypeTagManager(resourceKey, entityTypeClass);
    }
    
}
