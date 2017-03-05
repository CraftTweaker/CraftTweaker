package stanhebben.zenscript.symbols;

import stanhebben.zenscript.compiler.ITypeRegistry;
import stanhebben.zenscript.expression.partial.*;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Field;

/**
 * @author Stan
 */
public class SymbolJavaStaticField implements IZenSymbol {
    
    private final Class cls;
    private final Field field;
    private final ITypeRegistry types;
    
    public SymbolJavaStaticField(Class cls, Field field, ITypeRegistry types) {
        this.cls = cls;
        this.field = field;
        this.types = types;
    }
    
    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new ExpressionJavaStaticField(position, cls, field, types);
    }
}
