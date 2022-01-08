package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.phys.HitResult;

@ZenRegister
@Document("vanilla/api/util/HitResultType")
@NativeTypeRegistration(value = HitResult.Type.class, zenCodeName = "crafttweaker.api.util.HitResultType")
@BracketEnum("minecraft:hitresult/type")
public class ExpandHitResultType {

}
