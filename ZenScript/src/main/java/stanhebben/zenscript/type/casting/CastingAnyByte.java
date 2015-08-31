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
public class CastingAnyByte implements ICastingRule {
	public static final CastingAnyByte INSTANCE = new CastingAnyByte();

	private CastingAnyByte() {
	}

	@Override
	public void compile(IEnvironmentMethod method) {
		method.getOutput().invokeInterface(IAny.class, "asByte", byte.class);
	}

	@Override
	public ZenType getInputType() {
		return ZenType.ANY;
	}

	@Override
	public ZenType getResultingType() {
		return ZenType.BYTE;
	}
}
