package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.*;
import stanhebben.zenscript.util.*;

import java.util.*;

import static stanhebben.zenscript.util.ZenTypeUtil.internal;

public class ExpressionMapEntrySet extends Expression {
    
    private final Expression map;
    private final SetType setType;
    private final ZenTypeArrayBasic type;
    
    public ExpressionMapEntrySet(ZenPosition position, Expression map, String whichSet) {
        super(position);
        this.map = map;
        if(whichSet.contains("value"))
            this.setType = SetType.VALUES;
        else if(whichSet.contains("key"))
            this.setType = SetType.KEYS;
        else
            this.setType = SetType.ENTRIES;
        
        this.type = makeType(map);
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        map.compile(result, environment);
        MethodOutput output = environment.getOutput();
        switch(setType) {
            case KEYS:
                output.invokeInterface(Map.class, "keySet", Set.class);
                break;
            case VALUES:
                output.invokeInterface(Map.class, "values", Collection.class);
                break;
            case ENTRIES:
                output.invokeInterface(Map.class, "entrySet", Set.class);
        }
        output.iConst0();
        output.newArray(type.getBaseType().toASMType());
        output.invokeInterface(Collection.class, "toArray", Object[].class, Object[].class);
        output.checkCast(internal(getType().toJavaClass()));
    }
    
    @Override
    public ZenType getType() {
        return type;
    }
    
    private ZenTypeArrayBasic makeType(Expression map) {
        ZenTypeAssociative mapType = (ZenTypeAssociative) map.getType();
        switch(setType) {
            case KEYS:
                return new ZenTypeArrayBasic(mapType.getKeyType());
            case VALUES:
                return new ZenTypeArrayBasic(mapType.getValueType());
            default:
                return new ZenTypeArrayBasic(new ZenTypeEntry(mapType));
        }
    }
    
    private enum SetType {
        VALUES, KEYS, ENTRIES
    }
}
