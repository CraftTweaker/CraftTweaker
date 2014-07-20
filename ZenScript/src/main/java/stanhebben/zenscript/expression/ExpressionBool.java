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
		if (type == ZenType.BOOL) {
			return this;
		} else {
			return ZenType.BOOL.cast(position, environment, this, type);
		}
	}

	@Override
	public Expression getMember(ZenPosition position, IEnvironmentGlobal environment, String name) {
		environment.error(position, "Bool constants do not have members");
		return new ExpressionInvalid(position, ZenType.BOOL);
	}

	@Override
	public ZenType getType() {
		return ZenType.BOOL;
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
