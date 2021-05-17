package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this itemEntity
 */
@ZenRegister
@Document("vanilla/api/entity/MCItemEntity")
@NativeTypeRegistration(value = ItemEntity.class, zenCodeName = "crafttweaker.api.entity.MCItemEntity", constructors = {
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = World.class, name = "world"),
                @NativeConstructor.ConstructorParameter(type = double.class, name = "x"),
                @NativeConstructor.ConstructorParameter(type = double.class, name = "y"),
                @NativeConstructor.ConstructorParameter(type = double.class, name = "z"),
                @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "stack")
        })
})
public class ExpandItemEntity {
    
    /**
     * Gets the IItemStack inside this ItemEntity.
     *
     * @return The IItemStack inside this ItemEntity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("item")
    public static IItemStack getItem(ItemEntity itemEntity) {
        
        return new MCItemStack(itemEntity.getItem());
    }
    
    /**
     * Sets the IItemStack inside this ItemEntity.
     *
     * @param stack The new IItemStack.
     *
     * @docParam stack <item:minecraft:diamond>
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("item")
    public static void setItem(ItemEntity itemEntity, IItemStack stack) {
        
        itemEntity.setItem(stack.getInternal());
    }
    
}
