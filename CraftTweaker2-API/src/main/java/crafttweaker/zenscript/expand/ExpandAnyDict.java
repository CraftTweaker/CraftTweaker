package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

import java.util.Map;

/**
 * @author Stanneke
 */
@ZenExpansion("any[any]")
@ZenRegister
public class ExpandAnyDict {
    
    @ZenCaster
    public static IData asData(Map<String, IData> values) {
        return new DataMap(values, true);
    }
}
