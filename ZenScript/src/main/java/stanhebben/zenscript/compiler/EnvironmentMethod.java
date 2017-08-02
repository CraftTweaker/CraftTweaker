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
 * @author Stanneke
 */
public class EnvironmentMethod implements IEnvironmentMethod {
    
    private final MethodOutput output;
    private final HashMap<SymbolLocal, Integer> locals;
    private final IEnvironmentClass environment;
    private final Map<String, IZenSymbol> local;
    
    public EnvironmentMethod(MethodOutput output, IEnvironmentClass environment) {
        this.output = output;
        this.locals = new HashMap<>();
        this.environment = environment;
        this.local = new HashMap<>();
    }
    
    @Override
    public MethodOutput getOutput() {
        return output;
    }
    
    @Override
    public ClassVisitor getClassOutput() {
        return environment.getClassOutput();
    }
    
    @Override
    public int getLocal(SymbolLocal variable) {
        if(!locals.containsKey(variable)) {
            locals.put(variable, output.local(variable.getType().toASMType()));
        }
        return locals.get(variable);
    }
    
    @Override
    public ZenType getType(Type type) {
        return environment.getType(type);
    }
    
    @Override
    public void error(ZenPosition position, String message) {
        environment.error(position, message);
    }
    
    @Override
    public void warning(ZenPosition position, String message) {
        environment.warning(position, message);
    }
    
    @Override
    public void info(ZenPosition position, String message) {
        environment.info(position, message);
    }
    
    @Override
    public IZenCompileEnvironment getEnvironment() {
        return environment.getEnvironment();
    }
    
    @Override
    public TypeExpansion getExpansion(String name) {
        return environment.getExpansion(name);
    }
    
    @Override
    public String makeClassName() {
        return environment.makeClassName();
    }
    
    @Override
    public void putClass(String name, byte[] data) {
        environment.putClass(name, data);
    }
    
    @Override
    public boolean containsClass(String name) {
        return environment.containsClass(name);
    }
    
    @Override
    public IPartialExpression getValue(String name, ZenPosition position) {
        if(local.containsKey(name)) {
            return local.get(name).instance(position);
        } else {
            return environment.getValue(name, position);
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
    public Set<String> getClassNames() {
        return environment.getClassNames();
    }
    
    @Override
    public byte[] getClass(String name) {
        return environment.getClass(name);
    }
}
