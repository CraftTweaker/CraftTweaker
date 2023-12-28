package com.blamejared.crafttweaker.mixin.common.transform.item.attribute;

import com.blamejared.crafttweaker.api.item.attribute.ItemAttributeModifierBase;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemAttributeModifierEvent.class)
public abstract class MixinItemAttributeModifierEvent implements ItemAttributeModifierBase {

}
