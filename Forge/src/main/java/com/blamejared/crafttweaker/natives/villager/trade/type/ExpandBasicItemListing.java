package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;

@ZenRegister
@Document("forge/api/villager/trade/type/BasicItemListing")
@NativeTypeRegistration(value = BasicItemListing.class, zenCodeName = "crafttweaker.api.villager.trade.type.BasicItemListing",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "price", description = "The primary Item that is being given to the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "price2", description = "The secondary item that is being given to the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "forSale", description = "The Item that is being sold by the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxTrades", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "xp", description = "How much experience does this trade reward the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = float.class, name = "priceMult", description = "How much the price is affected by demand, Hero of the Village, and village reputation", examples = "0.05"),
                    
                })
        })
public class ExpandBasicItemListing {

}
