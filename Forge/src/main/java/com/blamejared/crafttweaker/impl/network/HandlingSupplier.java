package com.blamejared.crafttweaker.impl.network;


import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface HandlingSupplier<MSG> extends BiConsumer<MSG, Supplier<CustomPayloadEvent.Context>> {
    
    @Override
    default void accept(MSG msg, Supplier<CustomPayloadEvent.Context> contextSupplier) {
    
    }
    
}
