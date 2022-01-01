package crafttweaker;

import crafttweaker.annotations.*;
import crafttweaker.api.client.IClient;
import crafttweaker.api.event.IEventManager;
import crafttweaker.api.formatting.IFormatter;
import crafttweaker.api.game.IGame;
import crafttweaker.api.item.IItemUtils;
import crafttweaker.api.mods.ILoadedMods;
import crafttweaker.api.oredict.IOreDict;
import crafttweaker.api.recipes.*;
import crafttweaker.api.server.IServer;
import crafttweaker.api.vanilla.IVanilla;
import crafttweaker.runtime.*;
import crafttweaker.util.SuppressErrorFlag;
import crafttweaker.zenscript.*;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.natives.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.logging.*;

/**
 * Provides access to the CraftTweaker API.
 * <p>
 * An implementing platform needs to do the following: - Set a logger - Set the
 * ore dictionary - Set the recipe manager - Set the furnace manager - Set event
 * manager - Set resource manager
 * <p>
 * - Register additional global symbols to the GlobalRegistry (recipes,
 * crafttweaker, oreDict, logger, as well as the official set of functions) -
 * Register native classes using the GlobalRegistry - Register bracket handlers
 * to resolve block/item/... references using the bracket syntax
 */
public class CraftTweakerAPI {
    
    /**
     * The Tweaker is where you apply undoable actions. Any kind of action that
     * reloads with the scripts should always be submitted to the tweaker.
     */
    public static final ITweaker tweaker = new CrTTweaker();
    
    /**
     * Access point to the events manager.s
     */
    public static final IEventManager events = CrafttweakerImplementationAPI.events;
    /**
     * Access point to the ore dictionary.
     */
    public static IOreDict oreDict = null;
    /**
     * Access point to the recipe manager.
     */
    public static IRecipeManager recipes = null;
    /**
     * Access point to the furnace manager.
     */
    public static IFurnaceManager furnace = null;
    /**
     * Access point to the server, if any.
     */
    public static IServer server = null;
    
    /**
     * Access point to the client, if any.
     */
    public static IClient client = null;
    /**
     * Access point to general game data, such as items.
     */
    public static IGame game = null;
    /**
     * Access point to mods list.
     */
    public static ILoadedMods loadedMods = null;
    /**
     * Access point to the text formatter.
     */
    public static IFormatter format = null;
    /**
     * Access point to the vanilla functions and data.
     */
    public static IVanilla vanilla = null;
    /**
     * Access point to the ItemUtils for performing various useful actions on items.
     */
    public static IItemUtils itemUtils = null;
    /**
     * Access point to the brewing handler
     */
    public static IBrewingManager brewingManager = null;

    /**
     * whether errors and warnings are printed to players' chat or not.
     * @see SuppressErrorFlag
     */
    private static SuppressErrorFlag suppressErrorFlag = SuppressErrorFlag.DEFAULT;
    
    // Here shadows pls use
    public static boolean ENABLE_SEARCH_TREE_RECALCULATION = true;
    
    /**
     * If true, script actions will be profiled and the results will be printed to the log.
     */
    public static boolean profile = false;
    
    static {
        registerGlobalSymbol("logger", getJavaStaticGetterSymbol(CraftTweakerAPI.class, "getLogger"));
        registerGlobalSymbol("recipes", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "recipes"));
        registerGlobalSymbol("furnace", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "furnace"));
        registerGlobalSymbol("oreDict", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "oreDict"));
        registerGlobalSymbol("events", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "events"));
        registerGlobalSymbol("server", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "server"));
        registerGlobalSymbol("client", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "client"));
        registerGlobalSymbol("game", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "game"));
        registerGlobalSymbol("loadedMods", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "loadedMods"));
        registerGlobalSymbol("format", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "format"));
        registerGlobalSymbol("vanilla", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "vanilla"));
        registerGlobalSymbol("itemUtils", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "itemUtils"));
        registerGlobalSymbol("brewing", getJavaStaticFieldSymbol(CraftTweakerAPI.class, "brewingManager"));
    }
    
    private CraftTweakerAPI() {
        
    }
    
    /**
     * The logger can be used to write logging messages to the client. Error and
     * warning messages should be relayed to admins for further handling.
     *
     * @return the logger
     */
    public static ILogger getLogger() {
        return CrafttweakerImplementationAPI.logger;
    }
    
    /**
     * Applies this given action.
     *
     * @param action action object
     */
    public static void apply(IAction action) {
        long time = 0;
        if(profile){
            time = System.nanoTime();
        }
        
        tweaker.apply(action);
        
        if(profile){
            logInfo("Took " + ((System.nanoTime() - time)/1000000d) + " ms");
        }
    }
    
    /**
     * Logs a command message. Commands messages are those generated as output
     * in response to a command.
     *
     * @param message command message
     */
    public static void logCommand(String message) {
        getLogger().logCommand(message);
    }
    
    /**
     * Logs an info message. Info messages have low priority and will only be
     * displayed in the log files, but not directly to players in-game.
     *
     * @param message info message
     */
    public static void logInfo(String message) {
        getLogger().logInfo(message);
    }
    
    /**
     * Logs a warning message. Warning messages are displayed to admins and
     * indicate that there is an issue. However, the issue is not a large
     * problem, and everything should run fine - besides perhaps a few things
     * not entirely working as expected.
     *
     * @param message warning message
     */
    public static void logWarning(String message) {
        getLogger().logWarning(message);
    }
    
    /**
     * Logs an error message. Error messages indicate a real problem and
     * indicate that things won't run properly. The scripting system will still
     * make a best-effort attempt at executing the rest of the scripts, but that
     * might cause additional errors and issues.
     *
     * @param message error message
     */
    public static void logError(String message) {
        getLogger().logError(message);
    }
    
    /**
     * Logs an error message. Error messages indicate a real problem and
     * indicate that things won't run properly. The scripting system will still
     * make a best-effort attempt at executing the rest of the scripts, but that
     * might cause additional errors and issues.
     *
     * @param message   error message
     * @param exception exception that was caught related to the error
     */
    public static void logError(String message, Throwable exception) {
        getLogger().logError(message, exception);
    }
    
    /**
     * Logs an info message, but only if it has not been disabled
     * @param message info message
     */
    public static void logDefault(String message) {
        getLogger().logDefault(message);
    }

    /**
     * Set whether errors and warnings printed to players' chat or not.
     * @param flag to set
     */
    public static void setSuppressErrorFlag(SuppressErrorFlag flag) {
        if (suppressErrorFlag.isForced())
            return;
        suppressErrorFlag = flag;
    }

    /**
     * @return if errors sent to log won't be printed to player's chat
     */
    public static boolean isSuppressingErrors() {
        return suppressErrorFlag.isSuppressingErrors();
    }

    /**
     * @return if warnings sent to log won't be printed to player's chat
     */
    public static boolean isSuppressingWarnings() {
        return suppressErrorFlag.isSuppressingWarnings();
    }
    
    // ###################################
    // ### Plugin registration methods ###
    // ###################################
    
    /**
     * Registers an annotated class. A class is annotated with either @ZenClass
     * or @ZenExpansion. Classes not annotated with either of these will be
     * ignored.
     *
     * @param annotatedClass class that is annotated
     */
    public static void registerClass(Class<?> annotatedClass) {
        boolean registered = false;
        for(Annotation annotation : annotatedClass.getAnnotations()) {
            if(annotation instanceof ZenExpansion) {
                GlobalRegistry.registerExpansion(annotatedClass);
                registered = true;
            }
            
            if(annotation instanceof ZenClass) {
                GlobalRegistry.registerNativeClass(annotatedClass);
                registered = true;
            }
            if((annotation instanceof BracketHandler) && IBracketHandler.class.isAssignableFrom(annotatedClass)) {
                try {
                    IBracketHandler bracketHandler = (IBracketHandler) annotatedClass.newInstance();
                    registerBracketHandler(bracketHandler);
                } catch(InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(CraftTweakerAPI.class.getName()).log(Level.SEVERE, null, ex);
                }
                registered = true;
            }
        }
        if(registered) {
            for(Method method : annotatedClass.getDeclaredMethods()) {
                if(!Modifier.isStatic(method.getModifiers()) || !Modifier.isPublic(method.getModifiers())) {
                    continue;
                }
                if(method.isAnnotationPresent(OnRegister.class)) {
                    try {
                        method.invoke(null);
                    } catch(IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    /**
     * Registers a global symbol. Global symbols are immediately accessible from
     * anywhere in the scripts.
     *
     * @param name   symbol name
     * @param symbol symbol
     */
    public static void registerGlobalSymbol(String name, IZenSymbol symbol) {
        GlobalRegistry.registerGlobal(name, symbol);
    }
    
    /**
     * Registers a bracket handler. Is capable of converting the bracket syntax
     * to an actual value. This new handler will be added last - it can thus not
     * intercept values that are already handled by the system.
     *
     * @param handler bracket handler to be added
     */
    public static void registerBracketHandler(IBracketHandler handler) {
        GlobalRegistry.registerBracketHandler(handler);
    }
    
    /**
     * Creates a symbol that refers to a java method.
     *
     * @param cls       class that contains the method
     * @param name      method name
     * @param arguments method argument types
     *
     * @return corresponding symbol
     */
    public static IZenSymbol getJavaStaticMethodSymbol(Class<?> cls, String name, Class<?>... arguments) {
        IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), cls, name, arguments);
        return new SymbolJavaStaticMethod(method);
    }
    
    /**
     * Creates a symbol that refers to a java getter. The getter must be a
     * method with no arguments. The given symbol will act as a variable of
     * which the value can be retrieved but not set.
     *
     * @param cls  class that contains the getter method
     * @param name name of the method
     *
     * @return corresponding symbol
     */
    public static IZenSymbol getJavaStaticGetterSymbol(Class<? extends CraftTweakerAPI> cls, String name) {
        IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), cls, name);
        return new SymbolJavaStaticGetter(method);
    }
    
    /**
     * Creates a symbol that refers to a static field. The field must be an
     * existing public field in the given class. The field will act as a
     * variable that can be retrieved but not set.
     *
     * @param cls  class that contains the field
     * @param name field name (must be public)
     *
     * @return corresponding symbol
     */
    public static IZenSymbol getJavaStaticFieldSymbol(Class<?> cls, String name) {
        try {
            Field field = cls.getField(name);
            return new SymbolJavaStaticField(cls, field, GlobalRegistry.getTypes());
        } catch(NoSuchFieldException | SecurityException ex) {
            return null;
        }
    }
    
    /**
     * Loads a Java method from an existing class.
     *
     * @param cls       method class
     * @param name      method name
     * @param arguments argument types
     *
     * @return java method
     */
    public static IJavaMethod getJavaMethod(Class<? extends IBracketHandler> cls, String name, Class<?>... arguments) {
        return JavaMethod.get(GlobalRegistry.getTypes(), cls, name, arguments);
    }
    
    /**
     * Gets a string representation of the Script file and line from the current stacktrace.
     * Only works if the current Stacktrace actually has a script within, otherwise returns
     * @return The filename and line number of the originating script.
     */
    public static String getScriptFileAndLine() {
        return Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(s -> s != null && s.getFileName() != null && s.getFileName().endsWith(".zs"))
                .findFirst()
                .map(s -> s.getFileName() + ":" + s.getLineNumber())
                .orElse("<?>");
    }
}
