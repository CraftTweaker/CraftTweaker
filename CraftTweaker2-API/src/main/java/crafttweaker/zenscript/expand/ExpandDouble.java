package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("double")
@ZenRegister
public class ExpandDouble {

    @ZenCaster
    public static IData asData(double value) {
        return new DataDouble(value);
    }
}
