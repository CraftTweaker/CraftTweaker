/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.value.IAny;

/**
 *
 * @author Stan
 */
public class CastingAnyBool implements ICastingRule {
	public static final CastingAnyBool INSTANCE = new CastingAnyBool();
	
	private CastingAnyBool() {}
	
	@Override
	public void compile(IEnvironmentMethod method) {
		method.getOutput().invokeInterface(IAny.class, "asBool", boolean.class);
	}
	
	@Override
	public ZenType getInputType() {
		return ZenType.ANY;
	}

	@Override
	public ZenType getResultingType() {
		return ZenType.BOOL;
	}
}
