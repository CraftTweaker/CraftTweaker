package com.blamejared.crafttweaker.impl.tag;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.NetworkTagManager;

public class CustomNetworkTagManager extends NetworkTagManager {
    
    
    public ITagCollectionSupplier func_242231_a() {
        return new ITagCollectionSupplier() {
            @Override
            public ITagCollection<Block> getBlockTags() {
                return null;
            }
            
            @Override
            public ITagCollection<Item> getItemTags() {
                return null;
            }
            
            @Override
            public ITagCollection<Fluid> getFluidTags() {
                return null;
            }
            
            @Override
            public ITagCollection<EntityType<?>> getEntityTypeTags() {
                return null;
            }
            
        };
    }
    
}
