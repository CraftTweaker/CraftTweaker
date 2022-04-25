package com.blamejared.crafttweaker.natives.block.type.cauldron;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LavaCauldronBlock;

@ZenRegister
@Document("vanilla/api/block/type/cauldron/LavaCauldronBlock")
@NativeTypeRegistration(value = LavaCauldronBlock.class, zenCodeName = "crafttweaker.api.block.type.cauldron.LavaCauldronBlock")
public class ExpandLavaCauldronBlock {

}
