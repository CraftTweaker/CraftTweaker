package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

import java.util.Arrays;

/**
 * Makes arrays convertable to IData.
 * 
 * @author Stan Hebben
 */
@ZenExpansion("any[]")
public class ExpandAnyArray {
	@ZenCaster
	public static IData asData(IData[] values) {
		return new DataList(Arrays.asList(values), true);
	}
}
