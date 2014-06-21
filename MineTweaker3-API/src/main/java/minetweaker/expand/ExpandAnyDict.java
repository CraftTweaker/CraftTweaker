/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import java.util.Map;
import minetweaker.api.data.DataMap;
import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stanneke
 */
@ZenExpansion("any[any]")
public class ExpandAnyDict {
	@ZenCaster
	public static IData asData(Map<String, IData> values) {
		return new DataMap(values, true);
	}
}
