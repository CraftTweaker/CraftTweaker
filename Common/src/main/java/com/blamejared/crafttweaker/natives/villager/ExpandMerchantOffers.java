package com.blamejared.crafttweaker.natives.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.trading.MerchantOffers;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/MerchantOffers")
@NativeTypeRegistration(value = MerchantOffers.class, zenCodeName = "crafttweaker.api.villager.MerchantOffers",
        constructors = {
                @NativeConstructor({})
        })
public class ExpandMerchantOffers {
    
    @ZenCodeType.Method
    public static IData createTag(MerchantOffers internal) {
        
        return TagToDataConverter.convert(internal.createTag());
    }
    
}
