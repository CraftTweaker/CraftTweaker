package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ParsedExpressionOpAssign extends ParsedExpression {
    
    private final ParsedExpression left;
    private final ParsedExpression right;
    private final OperatorType operator;
    
    public ParsedExpressionOpAssign(ZenPosition position, ParsedExpression left, ParsedExpression right, OperatorType operator) {
        super(position);
        
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
    
    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        // TODO: validate if the prediction rules are sound
        Expression cLeft = left.compile(environment, predictedType).eval(environment);
        Expression cRight = right.compile(environment, cLeft.getType()).eval(environment);
        
        Expression value = cLeft.getType().binary(getPosition(), environment, cLeft, cRight, operator);
        
        return left.compile(environment, predictedType).assign(getPosition(), environment, value);
    }
}
