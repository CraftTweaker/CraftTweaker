package com.blamejared.crafttweaker.impl_native.villager;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.MerchantOffers;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/MCMerchantOffers")
@NativeTypeRegistration(value = MerchantOffers.class, zenCodeName = "crafttweaker.api.villager.MCMerchantOffers",
        constructors = {
                @NativeConstructor({})
        })
public class ExpandMerchantOffers {
    
    @ZenCodeType.Method
    public static IData createTag(MerchantOffers internal) {
        
        return new MapData(internal.write());
    }
    
}
