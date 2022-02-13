package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.platform.sides.DistributionType;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface IDistributionHelper {
    
    default void runOn(DistributionType dist, Supplier<Runnable> runnable) {
        
        if(getDistributionType() == dist) {
            runnable.get().run();
        }
    }
    
    default void runOn(Supplier<Runnable> client, Supplier<Runnable> server) {
        
        if(getDistributionType().isClient()) {
            client.get().run();
        } else {
            server.get().run();
        }
    }
    
    default <T> Optional<T> callOn(DistributionType dist, Supplier<Callable<T>> toCall) {
        
        if(getDistributionType() == dist) {
            try {
                return Optional.of(toCall.get().call());
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }
    
    default <T> Optional<T> callOn(Supplier<Supplier<T>> client, Supplier<Supplier<T>> server) {
        
        return switch(getDistributionType()) {
            case CLIENT -> Optional.of(client.get().get());
            case SERVER -> Optional.of(server.get().get());
        };
    }
    
    DistributionType getDistributionType();
    
    default boolean isClient() {
        
        return getDistributionType().isClient();
    }
    
    default boolean isServer() {
        
        return getDistributionType().isServer();
    }
    
}
