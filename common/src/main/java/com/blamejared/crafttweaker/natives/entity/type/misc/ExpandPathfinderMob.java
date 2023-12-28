package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.PathfinderMob;

@ZenRegister
@Document("vanilla/api/entity/type/misc/PathfinderMob")
@NativeTypeRegistration(value = PathfinderMob.class, zenCodeName = "crafttweaker.api.entity.type.misc.PathfinderMob")
public class ExpandPathfinderMob {
    //TODO expose methods here
}
