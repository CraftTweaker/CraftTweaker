/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.expand;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ZenExpandCaster {
	private final JavaMethod method;
	
	public ZenExpandCaster(JavaMethod method) {
		this.method = method;
	}
	
	public ZenType getTarget() {
		return method.getReturnType();
	}
	
	public Expression cast(ZenPosition position, IEnvironmentGlobal environment, Expression expression) {
		return method.callStatic(position, environment, expression);
	}
	
	public void compile(IEnvironmentMethod environment) {
		environment.getOutput().invokeStatic(method);
	}
}
