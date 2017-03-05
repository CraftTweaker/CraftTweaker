package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.casting.ICastingRule;
import stanhebben.zenscript.util.ZenPosition;

public class ExpressionAs extends Expression {

    private final Expression value;
    private final ICastingRule castingRule;

    public ExpressionAs(ZenPosition position, Expression value, ICastingRule castingRule) {
        super(position);

        this.value = value;
        this.castingRule = castingRule;
    }

	/*
     * @Override public Expression cast(ZenPosition position, IEnvironmentGlobal
	 * environment, ZenType type) { if
	 * (castingRule.getResultingType().equals(type)) { return this; }
	 * 
	 * ICastingRule newCastingRule = type.getCastingRule(type, environment); if
	 * (newCastingRule == null) { environment.error(position, "Cannot cast " +
	 * getType() + " to " + type); return new ExpressionInvalid(position, type);
	 * } else { return new ExpressionAs(position, this, newCastingRule); } }
	 */

    @Override
    public ZenType getType() {
        return castingRule.getResultingType();
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        value.compile(result, environment);

        if(result) {
            castingRule.compile(environment);
        }
    }
}
