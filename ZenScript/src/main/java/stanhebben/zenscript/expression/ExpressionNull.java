/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import org.objectweb.asm.Label;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeNull;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionNull extends Expression {
	public ExpressionNull(ZenPosition position) {
		super(position);
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
		if (type.isPointer()) {
			return this;
		} else {
			environment.error(position, "Cannot convert null to " + type);
			return new ExpressionInvalid(position);
		}
	}

	@Override
	public ZenType getType() {
		return ZenTypeNull.INSTANCE;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			environment.getOutput().aConstNull();
		}
	}

	@Override
	public void compileIf(Label onElse, IEnvironmentMethod environment) {
		environment.getOutput().goTo(onElse);
	}
}
