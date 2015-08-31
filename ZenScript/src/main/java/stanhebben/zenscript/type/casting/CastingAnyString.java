/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.casting;

import org.objectweb.asm.Label;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.value.IAny;

/**
 *
 * @author Stan
 */
public class CastingAnyString implements ICastingRule {
	public static final CastingAnyString INSTANCE = new CastingAnyString();

	private CastingAnyString() {
	}

	@Override
	public void compile(IEnvironmentMethod method) {
		MethodOutput output = method.getOutput();
		Label lblNonNull = new Label();
		Label lblAfter = new Label();

		output.dup();
		output.ifNonNull(lblNonNull);
		output.pop();
		output.aConstNull();
		output.goTo(lblAfter);

		output.label(lblNonNull);
		output.invokeInterface(IAny.class, "asString", String.class);

		output.label(lblAfter);
	}

	@Override
	public ZenType getInputType() {
		return ZenType.ANY;
	}

	@Override
	public ZenType getResultingType() {
		return ZenType.STRING;
	}
}
