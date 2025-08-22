package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTypeTest;

@ZenRegister
//@Document("vanilla/api/entity/EntityTypeTest")
@NativeTypeRegistration(value = EntityTypeTest.class, zenCodeName = "crafttweaker.api.entity.EntityTypeTest")
public class ExpandEntityTypeTest {
    
    public static final EntityTypeTest<Entity, Entity> ANY = new EntityTypeTest<>() {
        public Entity tryCast(Entity entity) {
            
            return entity;
        }
        
        public Class<Entity> getBaseClass() {
            
            return Entity.class;
        }
    };
    
}
