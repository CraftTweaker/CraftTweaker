package com.blamejared.crafttweaker.api.tag.manager.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tag/manager/type/EntityTypeTagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.type.EntityTypeTagManager")
public class EntityTypeTagManager extends KnownTagManager<EntityType<Entity>> {
    
    public EntityTypeTagManager(ResourceKey<? extends Registry<EntityType<Entity>>> resourceKey, Class<EntityType<Entity>> elementClass) {
        
        super(resourceKey, elementClass);
    }
    
}
