package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("long")
public class ExpandLong {

    @ZenCaster
    public static IData asData(long value) {
        return new DataLong(value);
    }
}
