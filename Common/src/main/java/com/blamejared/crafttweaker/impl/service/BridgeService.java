package com.blamejared.crafttweaker.impl.service;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.impl.registry.CraftTweakerRegistry;
import com.blamejared.crafttweaker.platform.services.IBridgeService;

public final class BridgeService implements IBridgeService {
    
    @Override
    public ICraftTweakerRegistry registry() {
        
        return CraftTweakerRegistry.get();
    }
    
}