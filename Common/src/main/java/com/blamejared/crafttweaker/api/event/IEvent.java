package com.blamejared.crafttweaker.api.event;

import com.google.common.reflect.TypeToken;

public interface IEvent<T extends IEvent<T>> {
    
    TypeToken<T> type();
    
}
