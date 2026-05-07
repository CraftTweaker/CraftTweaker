package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataList;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

import java.util.List;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("[ZenTypeNative: crafttweaker.data.IData]")
public class ExpandDataList {

    @ZenCaster
    public static IData asData(List<IData> data) {
        return new DataList(data, true);
    }
}
