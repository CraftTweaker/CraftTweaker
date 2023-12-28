package com.blamejared.crafttweaker.gametest.framework.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.zencode.util.PositionUtil;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.gametest.framework.GameTestAssertException;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ZenRegister(loaders = CraftTweakerConstants.ALL_LOADERS_MARKER)
@ZenCodeType.Name("crafttweaker.gametest.Globals")
public class GameTestGlobals {
    
    private static final List<IData> DATA_STORE = new LinkedList<>();
    private static final List<IData> DATA_VIEW = Collections.unmodifiableList(DATA_STORE);
    private static final Object2BooleanMap<CodePosition> EXPECTATIONS = new Object2BooleanOpenHashMap<>();
    
    
    @ZenCodeGlobals.Global
    public static void assertThat(boolean actual, boolean expected) {
        
        EXPECTATIONS.put(PositionUtil.getZCScriptPositionFromStackTrace(), actual == expected);
    }
    
    public static void expectationsMet() {
        
        EXPECTATIONS.forEach((position, assertion) -> {
            if(!assertion) {
                throw new GameTestAssertException("Assertion at '%s' failed!".formatted(position.toShortString()));
            }
        });
    }
    
    @ZenCodeGlobals.Global
    public static void storeData(IData data) {
        
        DATA_STORE.add(data);
    }
    
    public static void reset() {
        
        DATA_STORE.clear();
        EXPECTATIONS.clear();
    }
    
    public static List<IData> data() {
        
        return DATA_VIEW;
    }
    
    
}
