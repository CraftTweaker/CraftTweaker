package com.blamejared.crafttweaker.natives.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;

@ZenRegister
@Document("vanilla/api/villager/MerchantOffers")
@NativeTypeRegistration(value = MerchantOffers.class, zenCodeName = "crafttweaker.api.villager.MerchantOffers",
        constructors = {
                @NativeConstructor({})
        })
public class ExpandMerchantOffers {
    
    @ZenCodeType.Method
    public static MapData createTag(MerchantOffers internal) {
        
        return TagToDataConverter.convertCompound(internal.createTag());
    }
    
}
