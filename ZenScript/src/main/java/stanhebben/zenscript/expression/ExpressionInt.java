package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.*;

/**
 * @author Stanneke
 */
public class ExpressionInt extends Expression {

    private final long value;
    private final ZenType type;

    public ExpressionInt(ZenPosition position, long value, ZenType type) {
        super(position);

        this.value = value;
        this.type = type;
    }

    @Override
    public Expression cast(ZenPosition position, IEnvironmentGlobal environment, ZenType type) {
        if(type == this.type)
            return this;

        switch(type.getNumberType()) {
            case ZenType.NUM_BYTE:
            case ZenType.NUM_SHORT:
            case ZenType.NUM_INT:
            case ZenType.NUM_LONG:
                return new ExpressionInt(getPosition(), value, type);
            case ZenType.NUM_FLOAT:
            case ZenType.NUM_DOUBLE:
                return new ExpressionFloat(getPosition(), value, type);
        }

        return super.cast(position, environment, type);
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
        if(type == ZenTypeByte.INSTANCE) {
            output.biPush((byte) value);
        } else if(type == ZenTypeShort.INSTANCE) {
            output.siPush((short) value);
        } else if(type == ZenTypeInt.INSTANCE) {
            output.constant((int) value);
        } else if(type == ZenTypeLong.INSTANCE) {
            output.constant(value);
            
            //Objects
        } else if(type == ZenTypeIntObject.INSTANCE) {
        	output.constant((int)value);
        	output.invokeStatic(ZenTypeUtil.internal(Integer.class), "valueOf", "(I)Ljava/lang/Integer;");
        } else if(type == ZenTypeByteObject.INSTANCE) {
        	output.biPush((byte) value);
        	output.invokeStatic(ZenTypeUtil.internal(Byte.class), "valueOf", "(B)Ljava/lang/Byte;");        	
        } else if(type == ZenTypeLongObject.INSTANCE) {
        	output.constant(value);
        	output.invokeStatic(ZenTypeUtil.internal(Long.class), "valueOf", "(J)Ljava/lang/Long;");
        } else if(type == ZenTypeShortObject.INSTANCE) {
        	output.siPush((short) value);
        	output.invokeStatic(ZenTypeUtil.internal(Short.class), "valueOf", "(S)Ljava/lang/Short;");
        } else {
            throw new RuntimeException("Internal compiler error: int constant type is not an int");
        }
    }
}
