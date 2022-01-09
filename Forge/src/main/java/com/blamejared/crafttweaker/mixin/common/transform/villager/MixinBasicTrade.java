package com.blamejared.crafttweaker.mixin.common.transform.villager;

import com.blamejared.crafttweaker.api.villager.IBasicItemListing;
import net.minecraftforge.common.BasicItemListing;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BasicItemListing.class)
public abstract class MixinBasicTrade implements IBasicItemListing {
    

}
