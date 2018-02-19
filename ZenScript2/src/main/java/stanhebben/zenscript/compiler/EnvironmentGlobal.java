package stanhebben.zenscript.compiler;

import stanhebben.zenscript.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Stanneke
 */
public class EnvironmentGlobal implements IEnvironmentGlobal {
    
    private final IZenCompileEnvironment environment;
    private final IZenErrorLogger errors;
    private final Map<String, byte[]> classes;
    private final Map<String, IZenSymbol> local;
    private final ClassNameGenerator nameGen;
    private final TypeRegistry types;
    
    public EnvironmentGlobal(IZenCompileEnvironment environment, Map<String, byte[]> classes, ClassNameGenerator nameGen) {
        this.environment = environment;
        this.errors = environment.getErrorLogger();
        this.classes = classes;
        this.nameGen = nameGen;
        this.types = environment.getTypeRegistry();
        this.local = new HashMap<>();
    }
    
    public IZenCompileEnvironment getCompileEnvironment() {
        return environment;
    }
    
    @Override
    public ZenType getType(Type type) {
        return types.getType(type);
    }
    
    @Override
    public boolean containsClass(String name) {
        return classes.containsKey(name);
    }
    
    @Override
    public void putClass(String name, byte[] data) {
        classes.put(name, data);
    }
    
    @Override
    public String makeClassName() {
        return nameGen.generate();
    }
    
    @Override
    public TypeExpansion getExpansion(String type) {
        return environment.getExpansion(type);
    }
    
    @Override
    public void error(ZenPosition position, String message) {
        errors.error(position, message);
    }
    
    @Override
    public void warning(ZenPosition position, String message) {
        errors.warning(position, message);
    }
    
    @Override
    public void info(ZenPosition position, String message) {
        errors.info(position, message);
    }
    
    @Override
    public IZenCompileEnvironment getEnvironment() {
        return environment;
    }
    
    @Override
    public IPartialExpression getValue(String name, ZenPosition position) {
        if(local.containsKey(name)) {
            return local.get(name).instance(position);
        } else {
            IZenSymbol symbol = environment.getGlobal(name);
            return symbol == null ? null : symbol.instance(position);
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
        return classes.keySet();
    }
    
    @Override
    public byte[] getClass(String name) {
        return classes.get(name);
    }
}
