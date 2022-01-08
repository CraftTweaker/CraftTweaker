package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.ContextAwareComponent;

@ZenRegister
@Document("vanilla/api/text/ContextAwareComponent")
@NativeTypeRegistration(value = ContextAwareComponent.class, zenCodeName = "crafttweaker.api.text.ContextAwareComponent")
public class ExpandContextAwareComponent {

}
