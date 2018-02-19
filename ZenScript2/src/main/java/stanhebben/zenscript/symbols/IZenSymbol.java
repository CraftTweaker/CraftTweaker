package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public interface IZenSymbol {
    
    IPartialExpression instance(ZenPosition position);
}
