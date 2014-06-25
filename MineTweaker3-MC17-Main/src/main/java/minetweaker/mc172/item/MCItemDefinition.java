/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.item;

import minetweaker.api.item.IItemDefinition;
import net.minecraft.item.Item;

/**
 *
 * @author Stan
 */
public class MCItemDefinition implements IItemDefinition {
	private final Item item;
	
	public MCItemDefinition(Item item) {
		this.item = item;
	}
}
