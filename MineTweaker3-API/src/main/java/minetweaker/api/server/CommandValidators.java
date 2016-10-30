/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.server;

import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.server.CommandValidators")
public class CommandValidators {
	public static final ICommandValidator ISOP = player -> MineTweakerAPI.server.isOp(player);

	@ZenGetter
	public static ICommandValidator isOp() {
		return ISOP;
	}
}
