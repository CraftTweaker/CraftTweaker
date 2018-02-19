package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
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
