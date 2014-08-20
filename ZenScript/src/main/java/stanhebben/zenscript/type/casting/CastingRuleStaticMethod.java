/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;

/**
 *
 * @author Stan
 */
public class CastingRuleStaticMethod implements ICastingRule {
	private final IJavaMethod method;
	private final ICastingRule base;
	
	public CastingRuleStaticMethod(IJavaMethod method) {
		this.method = method;
		this.base = null;
	}
	
	public CastingRuleStaticMethod(IJavaMethod method, ICastingRule base) {
		this.method = method;
		this.base = base;
	}

	@Override
	public void compile(IEnvironmentMethod method) {
		if (base != null)
			base.compile(method);
		
		this.method.invokeStatic(method.getOutput());
	}

	@Override
	public ZenType getInputType() {
		return method.getParameterTypes()[0];
	}

	@Override
	public ZenType getResultingType() {
		return method.getReturnType();
	}
}
