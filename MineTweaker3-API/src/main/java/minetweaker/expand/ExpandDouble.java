package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("double")
public class ExpandDouble {

    @ZenCaster
    public static IData asData(double value) {
        return new DataDouble(value);
    }
}
