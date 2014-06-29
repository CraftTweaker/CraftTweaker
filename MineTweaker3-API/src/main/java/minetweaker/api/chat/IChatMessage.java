/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.chat;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.chat.IChatMessage")
public interface IChatMessage {
	@ZenOperator(OperatorType.ADD)
	public IChatMessage add(IChatMessage other);
	
	public Object getInternal();
}
