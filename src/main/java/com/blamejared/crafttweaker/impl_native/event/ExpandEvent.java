package com.blamejared.crafttweaker.impl_native.event;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

@NativeExpansion(Event.class)
public class ExpandEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("cancelable")
    public static boolean isCancelable(Event internal) {
        return internal.isCancelable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canceled")
    public static boolean isCanceled(Event internal) {
        return internal.isCanceled();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("canceled")
    public static void setCanceled(Event internal, boolean cancel) {
        internal.setCanceled(cancel);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasResult")
    public static boolean hasResult(Event internal) {
        return internal.hasResult();
    }
}
