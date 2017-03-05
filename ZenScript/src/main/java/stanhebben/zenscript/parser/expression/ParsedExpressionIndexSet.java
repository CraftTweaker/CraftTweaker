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
public class ParsedExpressionIndexSet extends ParsedExpression {
    
    private final ParsedExpression value;
    private final ParsedExpression index;
    private final ParsedExpression setValue;
    
    public ParsedExpressionIndexSet(ZenPosition position, ParsedExpression value, ParsedExpression index, ParsedExpression setValue) {
        super(position);
        
        this.value = value;
        this.index = index;
        this.setValue = setValue;
    }
    
    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        // TODO: improve prediction in this expression
        Expression cValue = value.compile(environment, null).eval(environment);
        Expression cIndex = index.compile(environment, null).eval(environment);
        Expression cSetValue = setValue.compile(environment, null).eval(environment);
        return cValue.getType().trinary(getPosition(), environment, cValue, cIndex, cSetValue, OperatorType.INDEXSET);
    }
}
