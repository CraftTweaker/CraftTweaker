package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("float")
public class ExpandFloat {

    @ZenCaster
    public static IData asData(float value) {
        return new DataFloat(value);
    }
}
