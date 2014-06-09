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
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionInvalid extends Expression {
	private final ZenType type;
	
	public ExpressionInvalid(ZenPosition position) {
		super(position);
		
		type = ZenTypeAny.INSTANCE;
	}
	
	public ExpressionInvalid(ZenPosition position, ZenType type) {
		super(position);
		
		this.type = type;
	}
	
	@Override
	public Expression getMember(ZenPosition position, IEnvironmentGlobal errors, String name) {
		return new ExpressionInvalid(position);
	}

	@Override
	public Expression cast(ZenPosition position, IEnvironmentGlobal errors, ZenType type) {
		return new ExpressionInvalid(position, type);
	}

	@Override
	public ZenType getType() {
		return type;
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
