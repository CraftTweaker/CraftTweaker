package minetweaker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import minetweaker.annotations.BracketHandler;
import minetweaker.annotations.ModOnly;
import minetweaker.api.event.IEventManager;
import minetweaker.api.mods.ILoadedMods;
import minetweaker.runtime.IMineTweaker;
import minetweaker.runtime.ILogger;
import minetweaker.runtime.Tweaker;
import minetweaker.api.recipes.IRecipeManager;
import minetweaker.api.oredict.IOreDict;
import minetweaker.api.recipes.IFurnaceManager;
import minetweaker.api.resource.IResourceManager;
import minetweaker.runtime.GlobalRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolJavaStaticField;
import stanhebben.zenscript.symbols.SymbolJavaStaticGetter;
import stanhebben.zenscript.symbols.SymbolJavaStaticMethod;
import stanhebben.zenscript.type.natives.JavaMethod;

/**
 * Provides access to the MineTweaker API.
 * 
 * An implementing platform needs to do the following:
 * - Set a logger
 * - Set the ore dictionary
 * - Set the recipe manager
 * - Set the furnace manager
 * - Set event manager
 * - Set resource manager
 * 
 * - Register additional global symbols to the GlobalRegistry (recipes,
 *    minetweaker, oreDict, logger, as well as the official set of functions)
 * - Register native classes using the GlobalRegistry
 * - Register bracket handlers to resolve block/item/... references using the
 *    bracket syntax
 * 
 * @author Stan Hebben
 */
public class MineTweakerAPI {
	static {
		List<Class> apiClasses = new ArrayList<Class>();
		ClassRegistry.getClasses(apiClasses);
		
		for (Class cls : apiClasses) {
			registerClass(cls);
		}
		
		registerGlobalSymbol("logger", getJavaStaticFieldSymbol(MineTweakerAPI.class, "logger"));
		registerGlobalSymbol("tweaker", getJavaStaticFieldSymbol(MineTweakerAPI.class, "tweaker"));
		registerGlobalSymbol("recipes", getJavaStaticFieldSymbol(MineTweakerAPI.class, "recipes"));
		registerGlobalSymbol("furnace", getJavaStaticFieldSymbol(MineTweakerAPI.class, "furnace"));
		registerGlobalSymbol("oreDict", getJavaStaticFieldSymbol(MineTweakerAPI.class, "oreDict"));
		registerGlobalSymbol("events", getJavaStaticFieldSymbol(MineTweakerAPI.class, "events"));
	}
	
	private MineTweakerAPI() {}
	
	/**
	 * The Tweaker is where you apply undoable actions. Any kind of action that
	 * reloads with the scripts should always be submitted to the tweaker.
	 */
	public static final IMineTweaker tweaker = new Tweaker();
	
	/**
	 * The logger can be used to write logging messages to the client. Error and
	 * warning messages should be relayed to admins for further handling.
	 */
	public static ILogger logger = null;
	
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
	 * Access point to the events manager.
	 */
	public static IEventManager events = null;
	
	/**
	 * Access point to mods list.
	 */
	public static ILoadedMods loadedMods = null;
	
	/**
	 * Access point to the global resource manager. Note that each script group also
	 * has its own local resource manager.
	 */
	public static IResourceManager resources = null;
	
	/**
	 * Register a class registry class. Such class must have (at least) a public
	 * static method called "getClasses" with accepts a List of classes and which
	 * stores its classes into that list.
	 * 
	 * @param registryClass 
	 */
	public static void registerClassRegistry(Class registryClass) {
		try {
			Method method = registryClass.getMethod("getClasses", List.class);
			if ((method.getModifiers() & Modifier.STATIC) == 0) {
				System.out.println("ERROR: getClasses method in " + registryClass.getName() + " isn't static");
			} else {
				List<Class> classes = new ArrayList<Class>();
				method.invoke(null, classes);
				
				outer: for (Class cls : classes) {
					for (Annotation annotation : cls.getAnnotations()) {
						if (annotation instanceof ModOnly) {
							String[] value = ((ModOnly) annotation).value();
							for (String mod : value) {
								if (!loadedMods.contains(mod)) {
									continue outer; // skip this class
								}
							}
						}
					}

					MineTweakerAPI.registerClass(cls);
				}
			}
		} catch (NoSuchMethodException ex) {

		} catch (IllegalAccessException ex) {

		} catch (InvocationTargetException ex) {

		}
	}
	
	public static boolean registerClassRegistry(String className) {
		try {
			registerClassRegistry(Class.forName(className));
			return true;
		} catch (ClassNotFoundException ex) {
			return false;
		}
	}
	
	/**
	 * Registers an annotated class. A class is annotated with either @ZenClass
	 * or @ZenExpansion. Classes not annotated with either of these will be
	 * ignored.
	 * 
	 * @param annotatedClass 
	 */
	public static void registerClass(Class annotatedClass) {
		//System.out.println("Registering " + annotatedClass.getName());
		
		for (Annotation annotation : annotatedClass.getAnnotations()) {
			if (annotation instanceof ZenExpansion) {
				GlobalRegistry.registerExpansion(annotatedClass);
			}
			
			if (annotation instanceof ZenClass) {
				GlobalRegistry.registerNativeClass(annotatedClass);
			}
			
			if ((annotation instanceof BracketHandler) && IBracketHandler.class.isAssignableFrom(annotatedClass)) {
				try {
					IBracketHandler bracketHandler = (IBracketHandler) annotatedClass.newInstance();
					registerBracketHandler(bracketHandler);
				} catch (InstantiationException ex) {
					Logger.getLogger(MineTweakerAPI.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IllegalAccessException ex) {
					Logger.getLogger(MineTweakerAPI.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	
	/**
	 * Registers a global symbol. Global symbols are immediately accessible from
	 * anywhere in the scripts.
	 * 
	 * @param name
	 * @param symbol 
	 */
	public static void registerGlobalSymbol(String name, IZenSymbol symbol) {
		GlobalRegistry.registerGlobal(name, symbol);
	}
	
	/**
	 * Registers a recipe remover. Removers are called when the global
	 * minetweaker.remove() function is called.
	 * 
	 * @param remover recipe remover
	 */
	public static void registerRemover(IRecipeRemover remover) {
		GlobalRegistry.registerRemover(remover);
	}
	
	/**
	 * Registers a bracket handler. Is capable of converting the bracket syntax
	 * to an actual value. This new handler will be added last - it can thus
	 * not intercept values that are already handled by the system.
	 * 
	 * @param handler bracket handler to be added
	 */
	public static void registerBracketHandler(IBracketHandler handler) {
		GlobalRegistry.registerBracketHandler(handler);
	}
	
	/**
	 * Creates a symbol that refers to a java method.
	 * 
	 * @param cls class that contains the method
	 * @param name method name
	 * @param arguments method argument types
	 * @return corresponding symbol
	 */
	public static IZenSymbol getJavaStaticMethodSymbol(Class cls, String name, Class... arguments) {
		JavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(), cls, name, arguments);
		return new SymbolJavaStaticMethod(method);
	}
	
	/**
	 * Creates a symbol that refers to a java getter. The getter must be a method
	 * with no arguments. The given symbol will act as a variable of which the
	 * value can be retrieved but not set.
	 * 
	 * @param cls class that contains the getter method
	 * @param name name of the method
	 * @return corresponding symbol
	 */
	public static IZenSymbol getJavaStaticGetterSymbol(Class cls, String name) {
		JavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(), cls, name);
		return new SymbolJavaStaticGetter(method);
	}
	
	/**
	 * Creates a symbol that refers to a static field. The field must be an
	 * existing public field in the given class. The field will act as a
	 * variable that can be retrieved but not set.
	 * 
	 * @param cls class that contains the field
	 * @param name field name (must be public)
	 * @return corresponding symbol
	 */
	public static IZenSymbol getJavaStaticFieldSymbol(Class cls, String name) {
		try {
			Field field = cls.getField(name);
			return new SymbolJavaStaticField(cls, field, GlobalRegistry.getTypeRegistry());
		} catch (NoSuchFieldException ex) {
			return null;
		} catch (SecurityException ex) {
			return null;
		}
	}
	
	/**
	 * Loads a Java method from an existing class.
	 * 
	 * @param cls method class
	 * @param name method name
	 * @param arguments argument types
	 * @return java method
	 */
	public static JavaMethod getJavaMethod(Class cls, String name, Class... arguments) {
		return JavaMethod.get(GlobalRegistry.getTypeRegistry(), cls, name, arguments);
	}
}
