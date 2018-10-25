package crafttweaker.zenscript;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.socket.SingleError;
import stanhebben.zenscript.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

import java.lang.reflect.Type;
import java.util.*;

public class CrTGlobalEnvironment implements IEnvironmentGlobal {
    
    private final Map<String, byte[]> classes;
    private final Map<String, IZenSymbol> symbols;
    private final ClassNameGenerator generator;
    private List<SingleError> errors = new ArrayList<>();
    
    public CrTGlobalEnvironment(Map<String, byte[]> classes) {
        this(classes, "");
    }
    
    public CrTGlobalEnvironment(Map<String, byte[]> classes, String loaderMainName) {
        this.classes = classes;
        symbols = new HashMap<>();
        generator = new ClassNameGenerator("ZenClass" + loaderMainName);
    }
    
    @Override
    public IZenCompileEnvironment getEnvironment() {
        return GlobalRegistry.getEnvironment();
    }
    
    @Override
    public TypeExpansion getExpansion(String name) {
        return GlobalRegistry.getExpansions().get(name);
    }
    
    @Override
    public String makeClassName() {
        return generator.generate();
    }
    
    @Override
    public String makeClassNameWithMiddleName(String middleName) {
        return generator.generateWithMiddleName(middleName);
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
    public IPartialExpression getValue(String name, ZenPosition position) {
        if(symbols.containsKey(name)) {
            return symbols.get(name).instance(position);
        } else if(GlobalRegistry.getGlobals().containsKey(name)) {
            return GlobalRegistry.getGlobals().get(name).instance(position);
        } else {
            IZenSymbol pkg = GlobalRegistry.getRoot().get(name);
            if(pkg == null) {
                return null;
            } else {
                return pkg.instance(position);
            }
        }
    }
    
    @Override
    public void putValue(String name, IZenSymbol value, ZenPosition position) {
        if(symbols.containsKey(name)) {
            error(position, "Value already defined in this scope: " + name);
        } else {
            symbols.put(name, value);
        }
    }
    
    @Override
    public ZenType getType(Type type) {
        return GlobalRegistry.getTypes().getType(type);
    }
    
    @Override
    public void error(ZenPosition position, String message) {
        CraftTweakerAPI.logError(position.toString() + " > " + message);
        errors.add(new SingleError(position.getFile().getFileName(), position.getLine(), position.getLineOffset(), message, SingleError.Level.ERROR));
    }
    
    @Override
    public void warning(ZenPosition position, String message) {
        CraftTweakerAPI.logWarning(position.toString() + " > " + message);
        errors.add(new SingleError(position.getFile().getFileName(), position.getLine(), position.getLineOffset(), message, SingleError.Level.WARN));
    
    }
    
    @Override
    public void info(ZenPosition position, String message) {
        CraftTweakerAPI.logInfo(position.toString() + " > " + message);
        errors.add(new SingleError(position.getFile().getFileName(), position.getLine(), position.getLineOffset(), message, SingleError.Level.INFO));
    
    }
    
    @Override
    public Set<String> getClassNames() {
        return classes.keySet();
    }
    
    @Override
    public byte[] getClass(String name) {
        return classes.get(name);
    }
    
    @Override
    public void error(String message) {
        CraftTweakerAPI.logError(message);
    }
    
    @Override
    public void error(String message, Throwable e) {
        CraftTweakerAPI.logError(message, e);
    }
    
    @Override
    public void warning(String message) {
        CraftTweakerAPI.logWarning(message);
    }
    
    @Override
    public void info(String message) {
        CraftTweakerAPI.logInfo(message);
    }
    
    // Stuff for storing errors to retrieve later
    public void clear() {
        errors.clear();
    }
    
    public List<SingleError> getErrors() {
        return errors;
    }
    
}