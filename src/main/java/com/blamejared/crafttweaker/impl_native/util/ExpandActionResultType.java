package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.ActionResultType;

@ZenRegister
@Document("vanilla/api/util/ActionResultType")
@NativeTypeRegistration(value = ActionResultType.class, zenCodeName = "crafttweaker.api.util.ActionResultType")
public class ExpandActionResultType {
}
