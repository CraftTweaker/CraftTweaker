/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import org.objectweb.asm.Label;
import stanhebben.zenscript.compiler.EnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.compiler.EnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeBool;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionBool extends Expression {
	private final boolean value;
	
	public ExpressionBool(ZenPosition position, boolean value) {
		super(position);
		
		this.value = value;
	}
	
	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
		if (type == ZenTypeBool.INSTANCE) {
			return this;
		} else if (type.canCastExplicit(type, environment)) {
			return new ExpressionAs(position, this, type);
		} else {
			environment.error(position, "Cannot cast a bool constant to " + type.toString());
			return new ExpressionInvalid(position, type);
		}
	}

	@Override
	public Expression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		environment.error(position, "Bool constants do not have members");
		return new ExpressionInvalid(position, ZenTypeBool.INSTANCE);
	}

	@Override
	public ZenType getType() {
		return ZenTypeBool.INSTANCE;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if (result) {
			if (value) {
				environment.getOutput().iConst1();
			} else {
				environment.getOutput().iConst0();
			}
		}
	}

	@Override
	public void compileIf(Label onElse, IEnvironmentMethod environment) {
		if (!value) {
			environment.getOutput().goTo(onElse);
		}
	}
}
