package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

/**
 * @author Stanneke
 */
public class ExpressionFloat extends Expression {

    private final double value;
    private final ZenType type;

    public ExpressionFloat(ZenPosition position, double value, ZenType type) {
        super(position);

        this.value = value;
        this.type = type;
    }

    @Override
    public ZenType getType() {
        return type;
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(!result)
            return;

        if(type == ZenTypeFloat.INSTANCE) {
            environment.getOutput().constant((float) value);
        } else if(type == ZenTypeDouble.INSTANCE) {
            environment.getOutput().constant(value);
        } else {
            throw new RuntimeException("Internal compiler error: source type is not a floating point type");
        }
    }
}
