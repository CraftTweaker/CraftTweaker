/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public interface IPartialExpression {
	public Expression eval(IEnvironmentGlobal environment);
	
	public Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other);
	
	public IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name);
	
	public Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values);
	
	public ZenType[] predictCallTypes(int numArguments);
	
	public IZenSymbol toSymbol();
	
	public ZenType getType();
	
	public ZenType toType(IEnvironmentGlobal environment);
}
