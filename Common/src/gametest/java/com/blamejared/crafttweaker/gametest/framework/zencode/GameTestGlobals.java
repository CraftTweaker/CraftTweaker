package com.blamejared.crafttweaker.gametest.framework.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ZenRegister(loaders = CraftTweakerConstants.ALL_LOADERS_MARKER)
@ZenCodeType.Name("crafttweaker.gametest.Globals")
public class GameTestGlobals {
    
    private static final List<IData> DATA_STORE = new LinkedList<>();
    private static final List<IData> DATA_VIEW = Collections.unmodifiableList(DATA_STORE);
    
    @ZenCodeGlobals.Global
    public static void storeData(IData data) {
        
        DATA_STORE.add(data);
    }
    
    public static void clear() {
        
        DATA_STORE.clear();
    }
    
    public static List<IData> data() {
        
        return DATA_VIEW;
    }
    
}
