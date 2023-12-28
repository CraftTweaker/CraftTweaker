package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.CommonLevelAccessor;

@ZenRegister
@Document("vanilla/api/world/CommonLevelAccessor")
@NativeTypeRegistration(value = CommonLevelAccessor.class, zenCodeName = "crafttweaker.api.world.CommonLevelAccessor")
public class ExpandCommonLevelAccessor {

}
