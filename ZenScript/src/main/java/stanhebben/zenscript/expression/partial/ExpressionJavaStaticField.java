package stanhebben.zenscript.expression.partial;

import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Field;

/**
 * @author Stan
 */
public class ExpressionJavaStaticField extends Expression {
    
    private final Class cls;
    private final Field field;
    private final ITypeRegistry types;
    
    public ExpressionJavaStaticField(ZenPosition position, Class cls, Field field, ITypeRegistry types) {
        super(position);
        
        this.cls = cls;
        this.field = field;
        this.types = types;
    }
    
    @Override
    public ZenType getType() {
        return types.getType(field.getGenericType());
    }
    
    @Override
    public void compile(boolean result, IEnvironmentMethod environment) {
        if(result) {
            environment.getOutput().getStaticField(cls, field);
        }
    }
}
