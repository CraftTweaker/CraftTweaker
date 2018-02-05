package crafttweaker.zenscript;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.runtime.GlobalFunctions;
import javafx.util.Pair;
import stanhebben.zenscript.*;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.ZenTypeNative;
import stanhebben.zenscript.type.natives.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.*;

/**
 * @author Stan
 */
public class GlobalRegistry {
    
    private static final Map<String, IZenSymbol> globals = new HashMap<>();
    private static final Set<Pair<Integer, IBracketHandler>> bracketHandlers = new TreeSet<>(new Comparator<Pair<Integer, IBracketHandler>>() {
        @Override
        public int compare(Pair<Integer, IBracketHandler> o1, Pair<Integer, IBracketHandler> o2) {
            return o1.getKey().compareTo(o2.getKey());
        }
    });
    private static final TypeRegistry types = new TypeRegistry();
    private static final SymbolPackage root = new SymbolPackage("<root>");
    private static final IZenErrorLogger errors = new CrTErrorLogger();
    private static final IZenCompileEnvironment environment = new CrTCompileEnvironment();
    private static final Map<String, TypeExpansion> expansions = new HashMap<>();
    
    static {
        registerGlobal("print", getStaticFunction(GlobalFunctions.class, "print", String.class));
        registerGlobal("totalActions", getStaticFunction(GlobalFunctions.class, "totalActions"));
        registerGlobal("enableDebug", getStaticFunction(GlobalFunctions.class, "enableDebug"));
        registerGlobal("isNull", getStaticFunction(GlobalFunctions.class, "isNull", Object.class));
        registerGlobal("max", getStaticFunction(Math.class, "max", int.class, int.class));
        registerGlobal("min", getStaticFunction(Math.class, "min", int.class, int.class));
        registerGlobal("pow", getStaticFunction(Math.class, "pow", double.class, double.class));
    }
    
    private GlobalRegistry() {
    }
    
    public static void registerGlobal(String name, IZenSymbol symbol) {
        if(globals.containsKey(name)) {
            throw new IllegalArgumentException("symbol already exists: " + name);
        }
        
        globals.put(name, symbol);
    }
    
    public static void registerExpansion(Class<?> cls) {
        try {
            for(Annotation annotation : cls.getAnnotations()) {
                if(annotation instanceof ZenExpansion) {
                    ZenExpansion eAnnotation = (ZenExpansion) annotation;
                    if(!expansions.containsKey(eAnnotation.value())) {
                        expansions.put(eAnnotation.value(), new TypeExpansion(eAnnotation.value()));
                    }
                    expansions.get(eAnnotation.value()).expand(cls, types);
                }
            }
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void registerBracketHandler(IBracketHandler handler) {
        int prio = 10;
        if(handler.getClass().getAnnotation(BracketHandler.class) != null) {
            prio = handler.getClass().getAnnotation(BracketHandler.class).priority();
        }else{
            CraftTweakerAPI.logError(handler.getClass().getName() + " is missing a BracketHandler annotation, setting the priority to " + prio);
        }
        bracketHandlers.add(new Pair<>(prio, handler));
    }
    
    public static void removeBracketHandler(IBracketHandler handler) {
        Pair<Integer, IBracketHandler> prioPair = null;
        for(Pair<Integer, IBracketHandler> pair : bracketHandlers) {
            if(pair.getValue().equals(handler)) {
                prioPair = pair;
            }
        }
        bracketHandlers.remove(prioPair);
    }
    
    public static void registerNativeClass(Class<?> cls) {
        try {
            ZenTypeNative type = new ZenTypeNative(cls);
            type.complete(types);
            root.put(type.getName(), new SymbolType(type), errors);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
    }
    
    public static IZenSymbol resolveBracket(IEnvironmentGlobal environment, List<Token> tokens) {
        for(Pair<Integer, IBracketHandler> pair : bracketHandlers) {
            IZenSymbol symbol = pair.getValue().resolve(environment, tokens);
            if(symbol != null) {
                return symbol;
            }
        }
        
        return null;
    }
    
    public static IZenSymbol getStaticFunction(Class cls, String name, Class... arguments) {
        IJavaMethod method = JavaMethod.get(types, cls, name, arguments);
        return new SymbolJavaStaticMethod(method);
    }
    
    public static IZenSymbol getStaticField(Class cls, String name) {
        try {
            Field field = cls.getDeclaredField(name);
            return new SymbolJavaStaticField(cls, field, types);
        } catch(NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(GlobalRegistry.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static IEnvironmentGlobal makeGlobalEnvironment(Map<String, byte[]> classes) {
        return new CrTGlobalEnvironment(classes);
    }
    
    public static Map<String, IZenSymbol> getGlobals() {
        return globals;
    }
    
    public static Set<Pair<Integer, IBracketHandler>> getBracketHandlers() {
        return bracketHandlers;
    }
    
    public static TypeRegistry getTypes() {
        return types;
    }
    
    public static SymbolPackage getRoot() {
        return root;
    }
    
    public static IZenErrorLogger getErrors() {
        return errors;
    }
    
    public static IZenCompileEnvironment getEnvironment() {
        return environment;
    }
    
    public static Map<String, TypeExpansion> getExpansions() {
        return expansions;
    }
}
