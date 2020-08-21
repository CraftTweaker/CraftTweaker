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
            public ITagCollection<Block> func_241835_a() {
                return null;
            }
    
            @Override
            public ITagCollection<Item> func_241836_b() {
                return null;
            }
    
            @Override
            public ITagCollection<Fluid> func_241837_c() {
                return null;
            }
    
            @Override
            public ITagCollection<EntityType<?>> func_241838_d() {
                return null;
            }
        };
    }
    
}
