package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

import java.util.Map;

/**
 * @author Stanneke
 */
@ZenExpansion("any[any]")
public class ExpandAnyDict {
    
    @ZenCaster
    public static IData asData(Map<String, IData> values) {
        return new DataMap(values, true);
    }
}
