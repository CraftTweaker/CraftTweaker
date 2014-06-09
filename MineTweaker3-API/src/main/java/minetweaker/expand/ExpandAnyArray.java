/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import java.util.Arrays;
import minetweaker.minecraft.data.DataList;
import minetweaker.minecraft.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stanneke
 */
@ZenExpansion("any[]")
public class ExpandAnyArray {
	@ZenCaster
	public IData asData(IData[] values) {
		return new DataList(Arrays.asList(values), true);
	}
}
