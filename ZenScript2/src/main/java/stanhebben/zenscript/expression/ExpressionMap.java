package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.type.casting.ICastingRule;
import stanhebben.zenscript.util.*;

import java.util.*;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

public class ExpressionMap extends Expression {

    private final Expression[] keys;
    private final Expression[] values;
    private final ZenTypeAssociative type;

    public ExpressionMap(ZenPosition position, Expression[] keys, Expression[] values, ZenTypeAssociative type) {
        super(position);

        this.keys = keys;
        this.values = values;

        this.type = type;
    }

    @Override
    public ZenType getType() {
        return type;
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(result) {
            ZenType keyType = type.getKeyType();
            ZenType valueType = type.getValueType();

            MethodOutput output = environment.getOutput();
            output.newObject(HashMap.class);
            output.dup();
            output.invokeSpecial(internal(HashMap.class), "<init>", "()V");

            for(int i = 0; i < keys.length; i++) {
                output.dup();
                keys[i].cast(getPosition(), environment, keyType).compile(true, environment);
                values[i].cast(getPosition(), environment, valueType).compile(true, environment);
                output.invokeInterface(internal(Map.class), "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
                output.pop();
            }
        } else {
            for(Expression key : keys) {
                key.compile(false, environment);
            }
            for(Expression value : values) {
                value.compile(false, environment);
            }
        }
    }

    @Override
    public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
        if(this.type.equals(type)) {
            return this;
        }

        if(type instanceof ZenTypeAssociative) {
            ZenTypeAssociative associativeType = (ZenTypeAssociative) type;
            Expression[] newKeys = new Expression[keys.length];
            Expression[] newValues = new Expression[values.length];

            for(int i = 0; i < keys.length; i++) {
                newKeys[i] = keys[i].cast(position, environment, associativeType.getKeyType());
                newValues[i] = values[i].cast(position, environment, associativeType.getValueType());
            }

            return new ExpressionMap(getPosition(), newKeys, newValues, associativeType);
        } else {
            ICastingRule castingRule = this.type.getCastingRule(type, environment);
            if(castingRule == null) {
                environment.error(position, "cannot cast " + this.type + " to " + type);
                return new ExpressionInvalid(position, type);
            } else {
                return new ExpressionAs(position, this, castingRule);
            }
        }
    }
}
