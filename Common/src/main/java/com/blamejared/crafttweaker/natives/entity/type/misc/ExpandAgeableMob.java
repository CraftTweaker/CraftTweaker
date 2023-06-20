package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.AgeableMob;

@ZenRegister
@Document("vanilla/api/entity/type/misc/AgeableMob")
@NativeTypeRegistration(value = AgeableMob.class, zenCodeName = "crafttweaker.api.entity.type.misc.AgeableMob")
public class ExpandAgeableMob {
    //TODO expose methods here
}
