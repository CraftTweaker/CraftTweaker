package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialJavaClass;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Type;

/**
 * @author Stanneke
 */
public class SymbolJavaClass implements IZenSymbol {

    private final Type cls;

    public SymbolJavaClass(Type cls) {
        this.cls = cls;
    }

    @Override
    public IPartialExpression instance(ZenPosition position) {
        return new PartialJavaClass(position, cls);
    }

    @Override
    public String toString() {
        return "SymbolJavaClass: " + cls.toString();
    }
}
