package com.blamejared.crafttweaker.impl.helper;

import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public final class ThreadingHelper {
    
    public static void runOnMainThread(final LogicalSide currentSide, final Runnable runnable) {
        
        final ThreadTaskExecutor<?> executor = LogicalSidedProvider.WORKQUEUE.get(currentSide);
    
        if (!executor.isOnExecutionThread()) {
        
            executor.deferTask(runnable);
        } else {
        
            runnable.run();
        }
    }
}
