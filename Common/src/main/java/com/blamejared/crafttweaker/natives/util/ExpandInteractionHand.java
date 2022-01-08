package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionHand;

@ZenRegister
@Document("vanilla/api/util/InteractionHand")
@NativeTypeRegistration(value = InteractionHand.class, zenCodeName = "crafttweaker.api.util.InteractionHand")
@BracketEnum("minecraft:interactionhand")
public class ExpandInteractionHand {
    
}
