package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Map;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 * @author Stanneke
 */
public class ExpressionMapIndexGet extends Expression {

    private final Expression map;
    private final Expression index;

    private final ZenType type;

    public ExpressionMapIndexGet(ZenPosition position, Expression map, Expression index) {
        super(position);

        this.map = map;
        this.index = index;

        type = ((ZenTypeAssociative) map.getType()).getValueType();
    }

    @Override
    public ZenType getType() {
        return type;
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(result) {
            map.compile(result, environment);
            index.cast(getPosition(), environment, ((ZenTypeAssociative) map.getType()).getKeyType()).compile(result, environment);
            environment.getOutput().invokeInterface(internal(Map.class), "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
            environment.getOutput().checkCast(type.toASMType().getInternalName());
        }
    }
}
