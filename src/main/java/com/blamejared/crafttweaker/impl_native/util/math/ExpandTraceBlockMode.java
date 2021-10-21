package com.blamejared.crafttweaker.impl_native.util.math;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.math.RayTraceContext;

@ZenRegister
@Document("vanilla/api/util/math/RayTraceBlockMode")
@NativeTypeRegistration(value = RayTraceContext.BlockMode.class, zenCodeName = "crafttweaker.api.util.math.RayTraceBlockMode")
public class ExpandTraceBlockMode {
}
