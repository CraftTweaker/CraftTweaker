/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import org.objectweb.asm.Label;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.MethodOutput;

/**
 *
 * @author Stan
 */
public class CastingRuleNullableStaticMethod implements ICastingRule {
	private final IJavaMethod method;
	private final ICastingRule base;

	public CastingRuleNullableStaticMethod(IJavaMethod method) {
		this.method = method;
		base = null;
	}

	public CastingRuleNullableStaticMethod(IJavaMethod method, ICastingRule base) {
		this.method = method;
		this.base = base;
	}

	@Override
	public void compile(IEnvironmentMethod method) {
		MethodOutput output = method.getOutput();

		Label lblNotNull = new Label();
		Label lblAfter = new Label();

		output.dup();
		output.ifNonNull(lblNotNull);
		output.pop();
		output.aConstNull();
		output.goTo(lblAfter);

		output.label(lblNotNull);

		if (base != null) {
			base.compile(method);
		}

		this.method.invokeStatic(method.getOutput());

		output.label(lblAfter);
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
