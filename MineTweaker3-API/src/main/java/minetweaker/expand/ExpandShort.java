package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("short")
public class ExpandShort {

    @ZenCaster
    public static IData asNBT(short value) {
        return new DataShort(value);
    }
}
