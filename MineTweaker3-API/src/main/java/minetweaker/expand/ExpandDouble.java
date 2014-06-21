/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import minetweaker.api.data.DataDouble;
import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stanneke
 */
@ZenExpansion("double")
public class ExpandDouble {
	@ZenCaster
	public static IData asData(double value) {
		return new DataDouble(value);
	}
}
