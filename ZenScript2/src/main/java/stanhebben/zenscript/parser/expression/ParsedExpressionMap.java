package stanhebben.zenscript.parser.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.type.casting.ICastingRule;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

/**
 * @author Stanneke
 */
public class ParsedExpressionMap extends ParsedExpression {

    private final List<ParsedExpression> keys;
    private final List<ParsedExpression> values;

    public ParsedExpressionMap(ZenPosition position, List<ParsedExpression> keys, List<ParsedExpression> values) {
        super(position);

        this.keys = keys;
        this.values = values;
    }

    @Override
    public IPartialExpression compile(IEnvironmentMethod environment, ZenType predictedType) {
        ZenType predictedKeyType = null;
        ZenType predictedValueType = null;
        ICastingRule castingRule = null;
        ZenTypeAssociative mapType = ZenType.ANYMAP;

        if(predictedType instanceof ZenTypeAssociative) {
            ZenTypeAssociative inputType = (ZenTypeAssociative) predictedType;
            predictedKeyType = inputType.getKeyType();
            predictedValueType = inputType.getValueType();
            mapType = inputType;
        } else {
            castingRule = ZenType.ANYMAP.getCastingRule(predictedType, environment);
            if(castingRule != null) {
                if(castingRule.getInputType() instanceof ZenTypeAssociative) {
                    ZenTypeAssociative inputType = (ZenTypeAssociative) castingRule.getInputType();
                    predictedKeyType = inputType.getKeyType();
                    predictedValueType = inputType.getValueType();
                    mapType = inputType;
                } else {
                    environment.error(getPosition(), "Caster found for any[any] but its input is not an associative array");
                }
            }
        }

        Expression[] cKeys = new Expression[keys.size()];
        Expression[] cValues = new Expression[values.size()];

        for(int i = 0; i < keys.size(); i++) {
            cKeys[i] = keys.get(i).compileKey(environment, predictedKeyType);
            cValues[i] = values.get(i).compile(environment, predictedValueType).eval(environment);
        }

        Expression result = new ExpressionMap(getPosition(), cKeys, cValues, mapType);
        if(castingRule != null) {
            return new ExpressionAs(getPosition(), result, castingRule);
        } else {
            return result;
        }
    }
}
