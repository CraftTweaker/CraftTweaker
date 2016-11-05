package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * Makes int arrays convertable to IData.
 *
 * @author Jared
 */
@ZenExpansion("int[]")
public class ExpandIntArray {
	
	@ZenCaster
	public static IData asData(int[] values) {
		return new DataIntArray(values, true);
	}
}
