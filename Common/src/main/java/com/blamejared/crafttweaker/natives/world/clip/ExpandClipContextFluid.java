package com.blamejared.crafttweaker.natives.world.clip;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.ClipContext;

@ZenRegister
@Document("vanilla/api/world/clip/FluidClipContext")
@NativeTypeRegistration(value = ClipContext.Fluid.class, zenCodeName = "crafttweaker.api.world.FluidClipContext")
@BracketEnum("minecraft:world/clip/fluidcontext")
public class ExpandClipContextFluid {

}
