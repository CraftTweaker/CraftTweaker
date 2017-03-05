package stanhebben.zenscript.compiler;

import org.objectweb.asm.ClassVisitor;
import stanhebben.zenscript.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.*;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Stan
 */
public class EnvironmentScope implements IEnvironmentMethod {
    
    private final IEnvironmentMethod outer;
    private final Map<String, IZenSymbol> local;
    private final Map<SymbolLocal, Integer> locals;
    
    public EnvironmentScope(IEnvironmentMethod outer) {
        this.outer = outer;
        this.local = new HashMap<>();
        this.locals = new HashMap<>();
    }
    
    @Override
    public MethodOutput getOutput() {
        return outer.getOutput();
    }
    
    @Override
    public int getLocal(SymbolLocal variable) {
        if(locals.containsKey(variable)) {
            return locals.get(variable);
        } else {
            return outer.getLocal(variable);
        }
    }
    
    @Override
    public ClassVisitor getClassOutput() {
        return outer.getClassOutput();
    }
    
    @Override
    public ZenType getType(Type type) {
        return outer.getType(type);
    }
    
    @Override
    public IZenCompileEnvironment getEnvironment() {
        return outer.getEnvironment();
    }
    
    @Override
    public TypeExpansion getExpansion(String name) {
        return outer.getExpansion(name);
    }
    
    @Override
    public String makeClassName() {
        return outer.makeClassName();
    }
    
    @Override
    public boolean containsClass(String name) {
        return outer.containsClass(name);
    }
    
    @Override
    public void putClass(String name, byte[] data) {
        outer.putClass(name, data);
    }
    
    @Override
    public IPartialExpression getValue(String name, ZenPosition position) {
        if(local.containsKey(name)) {
            return local.get(name).instance(position);
        } else {
            return outer.getValue(name, position);
        }
    }
    
    @Override
    public void putValue(String name, IZenSymbol value, ZenPosition position) {
        if(local.containsKey(name)) {
            error(position, "Value already defined in this scope: " + name);
        } else {
            local.put(name, value);
        }
    }
    
    @Override
    public void error(ZenPosition position, String message) {
        outer.error(position, message);
    }
    
    @Override
    public void warning(ZenPosition position, String message) {
        outer.warning(position, message);
    }
    
    @Override
    public Set<String> getClassNames() {
        return outer.getClassNames();
    }
    
    @Override
    public byte[] getClass(String name) {
        return outer.getClass(name);
    }
}
