package com.blamejared.crafttweaker.natives.entity.type.projectile;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/ItemSupplier")
@NativeTypeRegistration(value = ItemSupplier.class, zenCodeName = "crafttweaker.api.entity.type.projectile.ItemSupplier")
public class ExpandItemSupplier {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("item")
    public static ItemStack getItem(ItemSupplier internal) {
        
        return internal.getItem();
    }
    
}
