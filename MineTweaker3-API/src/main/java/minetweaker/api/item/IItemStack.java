/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import minetweaker.api.data.IData;
import minetweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.item.IItemStack")
public interface IItemStack extends IIngredient {
	@ZenGetter("definition")
	public IItemDefinition getDefinition();
	
	@ZenGetter("name")
	public String getName();
	
	@ZenGetter("displayName")
	public String getDisplayName();
	
	@ZenGetter("damage")
	public int getDamage();
	
	@ZenGetter("tag")
	public IData getTag();
	
	@ZenGetter("maxDamage")
	public int getMaxDamage();
	
	@ZenGetter("liquid")
	public ILiquidStack getLiquid();
	
	@ZenOperator(OperatorType.MUL)
	@ZenMethod
	public IItemStack amount(int amount);
	
	@ZenMethod
	public IIngredient anyDamage();
	
	@ZenMethod
	public IItemStack withDamage(int damage);
	
	@ZenMethod
	public IItemStack withAmount(int amount);
	
	@ZenMethod
	public IItemStack withTag(IData tag);
	
	@ZenMethod
	public IItemStack updateTag(IData tagUpdate);
}
