package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("byte")
public class ExpandByte {

    @ZenCaster
    public static IData asData(byte value) {
        return new DataByte(value);
    }
}
