package com.blamejared.crafttweaker.api.event.bus;

import com.google.common.base.CaseFormat;

public enum EventBusCharacteristic {
    SUPPORTS_CANCELLATION,
    IGNORE_PHASES,
    FORCE_ARRAY_DISPATCHING,
    FORCE_LIST_DISPATCHING;
    
    
    @Override
    public String toString() {
        
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, super.toString());
    }
}
