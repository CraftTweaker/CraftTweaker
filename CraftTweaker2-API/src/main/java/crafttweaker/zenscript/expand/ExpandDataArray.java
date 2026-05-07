package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataList;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

import java.util.Arrays;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("ZenTypeNative: crafttweaker.data.IData[]")
public class ExpandDataArray {
    @ZenCaster
    public static IData asData(IData[] data) {
        return new DataList(Arrays.asList(data), true);
    }
}
