package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.entity.IEntityDropFunction")
@ZenRegister
public interface IEntityDropFunction {
	IItemStack handle(IEntity entity, IDamageSource dmgSource);
}
