package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is the parent event for {@link LivingConversionEvent.Pre} and {@link LivingConversionEvent.Post}.
 * It is only registered so that these two events work, you should never have to listen to this one directly.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingConversionEvent")
@NativeTypeRegistration(value = LivingConversionEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingConversionEvent")
public class ExpandLivingConversionEvent {}
