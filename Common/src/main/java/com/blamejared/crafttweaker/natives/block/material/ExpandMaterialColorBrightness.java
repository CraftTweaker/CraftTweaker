package com.blamejared.crafttweaker.natives.block.material;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.material.MaterialColor;

@ZenRegister
@Document("vanilla/api/block/material/ColorBrightness")
@NativeTypeRegistration(value = MaterialColor.Brightness.class, zenCodeName = "crafttweaker.api.block.material.ColorBrightness")
@BracketEnum("minecraft:material/color/brightness")
public class ExpandMaterialColorBrightness {

}
