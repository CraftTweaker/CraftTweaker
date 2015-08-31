/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.entity;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.entity.IEntityDefinition")
public interface IEntityDefinition {
	@ZenGetter("id")
	public String getId();

	@ZenGetter("name")
	public String getName();
}
