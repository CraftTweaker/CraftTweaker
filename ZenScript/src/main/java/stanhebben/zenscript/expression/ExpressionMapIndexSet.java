package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Map;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

/**
 * @author Stanneke
 */
public class ExpressionMapIndexSet extends Expression {

    private final Expression map;
    private final Expression index;
    private final Expression value;

    private final ZenType type;

    public ExpressionMapIndexSet(ZenPosition position, Expression map, Expression index, Expression value) {
        super(position);

        this.map = map;
        this.index = index;
        this.value = value;

        type = ZenTypeVoid.INSTANCE;
    }

    @Override
    public ZenType getType() {
        return type;
    }

    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(result) {
            map.compile(result, environment);
            index.cast(getPosition(), environment, ((ZenTypeAssociative)map.getType()).getKeyType()).compile(result, environment);
            value.cast(getPosition(), environment, ((ZenTypeAssociative)map.getType()).getValueType()).compile(result, environment);
            environment.getOutput().invokeInterface(internal(Map.class), "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        }
    }
}
