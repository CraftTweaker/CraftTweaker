package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.ExpressionAndAnd;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ParsedExpressionAndAnd extends ParsedExpression {

    private final ParsedExpression left;
    private final ParsedExpression right;

    public ParsedExpressionAndAnd(ZenPosition position, ParsedExpression left, ParsedExpression right) {
        super(position);

        this.left = left;
        this.right = right;
    }

    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        return new ExpressionAndAnd(getPosition(), left.compile(environment, predictedType).eval(environment), right.compile(environment, predictedType).eval(environment));
    }
}
