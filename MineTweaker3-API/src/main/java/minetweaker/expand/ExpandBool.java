package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("bool")
public class ExpandBool {
    
    @ZenCaster
    public static IData asData(boolean value) {
        return new DataBool(value);
    }
}
