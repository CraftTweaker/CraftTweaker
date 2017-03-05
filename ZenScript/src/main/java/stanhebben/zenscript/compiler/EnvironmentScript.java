package stanhebben.zenscript.compiler;

import stanhebben.zenscript.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Stan
 */
public class EnvironmentScript implements IEnvironmentGlobal {
    
    private final IEnvironmentGlobal parent;
    private final Map<String, IZenSymbol> imports;
    
    public EnvironmentScript(IEnvironmentGlobal parent) {
        this.parent = parent;
        imports = new HashMap<>();
    }
    
    @Override
    public IZenCompileEnvironment getEnvironment() {
        return parent.getEnvironment();
    }
    
    @Override
    public TypeExpansion getExpansion(String name) {
        return parent.getExpansion(name);
    }
    
    @Override
    public String makeClassName() {
        return parent.makeClassName();
    }
    
    @Override
    public boolean containsClass(String name) {
        return parent.containsClass(name);
    }
    
    @Override
    public void putClass(String name, byte[] data) {
        parent.putClass(name, data);
    }
    
    @Override
    public IPartialExpression getValue(String name, ZenPosition position) {
        if(imports.containsKey(name)) {
            IZenSymbol imprt = imports.get(name);
            if(imprt == null)
                throw new RuntimeException("How could this happen?");
            return imprt.instance(position);
        } else {
            return parent.getValue(name, position);
        }
    }
    
    @Override
    public void putValue(String name, IZenSymbol value, ZenPosition position) {
        if(value == null)
            throw new IllegalArgumentException("value cannot be null");
        
        if(imports.containsKey(name)) {
            error(position, "Value already defined in this scope: " + name);
        } else {
            imports.put(name, value);
        }
    }
    
    @Override
    public ZenType getType(Type type) {
        return parent.getType(type);
    }
    
    @Override
    public void error(ZenPosition position, String message) {
        parent.error(position, message);
    }
    
    @Override
    public void warning(ZenPosition position, String message) {
        parent.warning(position, message);
    }
    
    @Override
    public Set<String> getClassNames() {
        return parent.getClassNames();
    }
    
    @Override
    public byte[] getClass(String name) {
        return parent.getClass(name);
    }
}
