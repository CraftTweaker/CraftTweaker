package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;

public interface IBridgeService {
    
    ICraftTweakerRegistry registry();
    
    IScriptRunManager scriptRunManager();
    
}
