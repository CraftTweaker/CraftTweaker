package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Mob;

@ZenRegister
@Document("vanilla/api/entity/type/misc/Mob")
@NativeTypeRegistration(value = Mob.class, zenCodeName = "crafttweaker.api.entity.type.misc.Mob")
public class ExpandMob {
    //TODO expose methods here
}
