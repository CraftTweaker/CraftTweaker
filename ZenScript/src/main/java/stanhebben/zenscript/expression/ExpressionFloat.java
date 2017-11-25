package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;
import stanhebben.zenscript.util.ZenTypeUtil;

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

        MethodOutput output = environment.getOutput();
        if(type == ZenTypeFloat.INSTANCE) {
            output.constant((float) value);
        } else if(type == ZenTypeDouble.INSTANCE) {
            output.constant(value);
        } else if(type == ZenTypeFloatObject.INSTANCE) {
        	output.constant((float)value);
        	output.invokeStatic(ZenTypeUtil.internal(Float.class), "valueOf", "(F)Ljava/lang/Float;");
        } else if(type == ZenTypeDoubleObject.INSTANCE) {
        	output.constant(value);
        	output.invokeSpecial(ZenTypeUtil.internal(Double.class), "valueOf", "(D)Ljava/lang/Double;");
        } else {
            throw new RuntimeException("Internal compiler error: source type is not a floating point type");
        }
    }
}
