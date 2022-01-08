package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.BaseComponent;

@ZenRegister
@Document("vanilla/api/text/BaseComponent")
@NativeTypeRegistration(value = BaseComponent.class, zenCodeName = "crafttweaker.api.text.BaseComponent")
public class ExpandBaseComponent {

}
