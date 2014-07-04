/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker;

import minetweaker.api.chat.IChatMessage;
import minetweaker.api.item.IItemDefinition;

/**
 *
 * @author Stan
 */
public interface IPlatformFunctions {
	public IChatMessage getMessage(String message);
	
	public void distributeScripts(byte[] data);
	
	public IItemDefinition getItemDefinition(int id);
}
