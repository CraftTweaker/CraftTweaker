package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("int")
@ZenRegister
public class ExpandInt {

    @ZenCaster
    public static IData toData(int value) {
        return new DataInt(value);
    }
}
