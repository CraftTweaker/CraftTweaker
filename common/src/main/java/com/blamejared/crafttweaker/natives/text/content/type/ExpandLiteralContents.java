package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.contents.PlainTextContents;

@ZenRegister
@Document("vanilla/api/text/content/type/LiteralContents")
@NativeTypeRegistration(value = PlainTextContents.LiteralContents.class, zenCodeName = "crafttweaker.api.text.content.type.LiteralContents")
public class ExpandLiteralContents {

}
