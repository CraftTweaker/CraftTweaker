package com.blamejared.crafttweaker.impl.network;

import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface HandlingSupplier<MSG> extends BiConsumer<MSG, Supplier<NetworkEvent.Context>>{
    
    @Override
    default void accept(MSG msg, Supplier<NetworkEvent.Context> contextSupplier) {
    
    }
    
}
