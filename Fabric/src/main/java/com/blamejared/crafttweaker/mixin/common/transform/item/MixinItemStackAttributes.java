package com.blamejared.crafttweaker.mixin.common.transform.item;

import com.blamejared.crafttweaker.api.item.modifier.FabricItemAttributeModifier;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ItemStack.class, priority = 1001)
public class MixinItemStackAttributes {
    
    @ModifyVariable(method = "getAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    public Multimap<Attribute, AttributeModifier> ct$getAttributeModifiers$modifyAttributes(Multimap<Attribute, AttributeModifier> multimap, EquipmentSlot slot) {
        
        FabricItemAttributeModifier modifierBase = new FabricItemAttributeModifier((ItemStack) (Object) this, slot, multimap);
        Services.EVENT.applyAttributeModifiers(modifierBase);
        return modifierBase.getModifiers();
    }
    
}
