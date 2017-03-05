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
public class ParsedExpressionIndex extends ParsedExpression {

    private final ParsedExpression value;
    private final ParsedExpression index;

    public ParsedExpressionIndex(ZenPosition position, ParsedExpression value, ParsedExpression index) {
        super(position);

        this.value = value;
        this.index = index;
    }

    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        // TODO: improve type prediction for this
        Expression cValue = value.compile(environment, null).eval(environment);
        Expression cIndex = index.compile(environment, null).eval(environment);
        return cValue.getType().binary(getPosition(), environment, cValue, cIndex, OperatorType.INDEXGET);
    }
}
