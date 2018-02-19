package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ParsedExpressionAssign extends ParsedExpression {
    
    private final ParsedExpression left;
    private final ParsedExpression right;
    
    public ParsedExpressionAssign(ZenPosition position, ParsedExpression left, ParsedExpression right) {
        super(position);
        
        this.left = left;
        this.right = right;
    }
    
    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        IPartialExpression cLeft = left.compile(environment, predictedType);
        return cLeft.assign(getPosition(), environment, right.compile(environment, cLeft.getType()).eval(environment));
    }
}
