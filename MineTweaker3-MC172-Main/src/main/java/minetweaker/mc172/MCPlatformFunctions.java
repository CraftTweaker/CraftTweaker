/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172;

import minetweaker.IPlatformFunctions;
import minetweaker.api.chat.IChatMessage;
import minetweaker.mc172.chat.MCChatMessage;

/**
 *
 * @author Stan
 */
public class MCPlatformFunctions implements IPlatformFunctions {
	public static final MCPlatformFunctions INSTANCE = new MCPlatformFunctions();
	
	private MCPlatformFunctions() {}
	
	@Override
	public IChatMessage getMessage(String message) {
		return new MCChatMessage(message);
	}
}
