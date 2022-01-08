package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.FormattedText;

@ZenRegister
@Document("vanilla/api/text/FormattedText")
@NativeTypeRegistration(value = FormattedText.class, zenCodeName = "crafttweaker.api.text.FormattedText")
public class ExpandFormattedText {

}
