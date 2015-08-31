/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.player;

import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.player.IPlayer")
public interface IPlayer {
	@ZenGetter("id")
	public String getId();

	@ZenGetter("name")
	public String getName();

	@ZenGetter("data")
	public IData getData();

	@ZenMethod
	public void update(IData data);

	@ZenMethod
	public void sendChat(IChatMessage message);

	@ZenMethod
	public void sendChat(String message);

	@ZenGetter("hotbarSize")
	public int getHotbarSize();

	@ZenMethod
	public IItemStack getHotbarStack(int i);

	@ZenGetter("inventorySize")
	public int getInventorySize();

	@ZenMethod
	public IItemStack getInventoryStack(int i);

	@ZenGetter("currentItem")
	public IItemStack getCurrentItem();

	@ZenGetter("creative")
	public boolean isCreative();

	@ZenGetter("adventure")
	public boolean isAdventure();

	@ZenMethod
	public void give(IItemStack stack);

	// not an exposed method. risks abuse
	public void openBrowser(String url);

	// not an exposed method, so far. would it be useful?
	public void copyToClipboard(String value);
}
