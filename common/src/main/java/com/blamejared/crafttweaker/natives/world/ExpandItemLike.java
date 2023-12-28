package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/ItemLike")
@NativeTypeRegistration(value = ItemLike.class, zenCodeName = "crafttweaker.api.world.ItemLike")
public class ExpandItemLike {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster
    public static Item asItem(ItemLike internal) {
        
        return internal.asItem();
    }
    
}
