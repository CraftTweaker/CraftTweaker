package com.blamejared.crafttweaker.natives.item.type.tiered;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Tiers;

@ZenRegister
@Document("vanilla/api/item/tiered/Tiers")
@NativeTypeRegistration(value = Tiers.class, zenCodeName = "crafttweaker.api.item.tiered.Tiers")
@BracketEnum("minecraft:item/tiers")
public class ExpandTiers {

}
