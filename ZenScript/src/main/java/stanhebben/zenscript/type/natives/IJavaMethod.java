/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.type.natives;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;

/**
 *
 * @author Stan
 */
public interface IJavaMethod {
	public boolean isStatic();

	public boolean accepts(int numArguments);

	public boolean accepts(IEnvironmentGlobal environment, Expression... arguments);

	public int getPriority(IEnvironmentGlobal environment, Expression... arguments);

	public void invokeVirtual(MethodOutput output);

	public void invokeStatic(MethodOutput output);

	public ZenType[] getParameterTypes();

	public ZenType getReturnType();

	public boolean isVarargs();
}
