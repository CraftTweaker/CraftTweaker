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
	Expression eval(IEnvironmentGlobal environment);

	Expression assign(ZenPosition position, IEnvironmentGlobal environment, Expression other);

	IPartialExpression getMember(ZenPosition position, IEnvironmentGlobal environment, String name);

	Expression call(ZenPosition position, IEnvironmentMethod environment, Expression... values);

	ZenType[] predictCallTypes(int numArguments);

	IZenSymbol toSymbol();

	ZenType getType();

	ZenType toType(IEnvironmentGlobal environment);
}
