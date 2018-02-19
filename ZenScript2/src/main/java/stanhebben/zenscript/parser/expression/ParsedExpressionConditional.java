package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.ExpressionConditional;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ParsedExpressionConditional extends ParsedExpression {
    
    private final ParsedExpression condition;
    private final ParsedExpression ifThen;
    private final ParsedExpression ifElse;
    
    public ParsedExpressionConditional(ZenPosition position, ParsedExpression condition, ParsedExpression ifThen, ParsedExpression ifElse) {
        super(position);
        
        this.condition = condition;
        this.ifThen = ifThen;
        this.ifElse = ifElse;
    }
    
    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        return new ExpressionConditional(getPosition(), condition.compile(environment, ZenType.BOOL).eval(environment), ifThen.compile(environment, predictedType).eval(environment), ifElse.compile(environment, predictedType).eval(environment));
    }
}
