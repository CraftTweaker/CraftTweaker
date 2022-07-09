package com.blamejared.crafttweaker.api.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.entity.Entity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@FunctionalInterface
@Document("vanilla/api/entity/INameplateFunction")
@ZenCodeType.Name("crafttweaker.api.entity.INameplateFunction")
public interface INameplateFunction {
    
    @ZenCodeType.Method
    void apply(Entity entity, NamePlateResult result);
    
}
