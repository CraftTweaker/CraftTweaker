package com.blamejared.crafttweaker.natives.entity.type.animal;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.animal.Animal;

@ZenRegister
@Document("vanilla/api/entity/type/animal/Animal")
@NativeTypeRegistration(value = Animal.class, zenCodeName = "crafttweaker.api.entity.type.animal.Animal")
public class ExpandAnimal {
    //TODO expose methods here
}
