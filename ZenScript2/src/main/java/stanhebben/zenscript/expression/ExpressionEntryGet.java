package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.Map;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

public class ExpressionEntryGet extends Expression {
    
    
    private final Expression entry;
    private final ZenTypeEntry type;
    private final boolean isKey;
    
    public ExpressionEntryGet(ZenPosition position, Expression entry, boolean isKey) {
        super(position);
        this.entry = entry;
        this.type = (ZenTypeEntry) entry.getType();
        this.isKey = isKey;
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        entry.compile(result, environment);
        environment.getOutput().invokeInterface(Map.Entry.class, isKey ? "getKey" : "getValue", Object.class);
        environment.getOutput().checkCast(internal(getType().toJavaClass()));
    }
    
    @Override
    public ZenType getType() {
        return isKey ? type.getKeyType() : type.getValueType();
    }
}
