package com.blamejared.crafttweaker.natives.event.entity.living.target;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

@ZenRegister
@Document("neoforge/api/event/entity/living/target/ILivingTargetType")
@NativeTypeRegistration(value = LivingChangeTargetEvent.ILivingTargetType.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.target.ILivingTargetType")
public class ExpandILivingTargetType {

}
