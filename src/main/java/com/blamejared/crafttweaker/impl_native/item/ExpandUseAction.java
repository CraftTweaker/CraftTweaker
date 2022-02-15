package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.UseAction;

@ZenRegister
@Document("vanilla/api/item/UseAction")
@NativeTypeRegistration(value = UseAction.class, zenCodeName = "crafttweaker.api.item.UseAction")
public class ExpandUseAction {

}
