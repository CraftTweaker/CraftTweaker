package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("int")
public class ExpandInt {

    @ZenCaster
    public static IData toData(int value) {
        return new DataInt(value);
    }
}
