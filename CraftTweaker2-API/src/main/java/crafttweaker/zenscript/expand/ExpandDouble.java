package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataDouble;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

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
