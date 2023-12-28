package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.BlockAndTintGetter;

@ZenRegister
@Document("vanilla/api/world/BlockAndTintGetter")
@NativeTypeRegistration(value = BlockAndTintGetter.class, zenCodeName = "crafttweaker.api.world.BlockAndTintGetter")
public class ExpandBlockAndTintGetter {

}
