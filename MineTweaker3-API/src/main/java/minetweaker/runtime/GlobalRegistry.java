/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import minetweaker.IBracketHandler;
import minetweaker.IRecipeRemover;
import minetweaker.MineTweakerAPI;
import minetweaker.expand.ExpandAnyArray;
import minetweaker.expand.ExpandAnyDict;
import minetweaker.expand.ExpandBool;
import minetweaker.expand.ExpandByte;
import minetweaker.expand.ExpandDouble;
import minetweaker.expand.ExpandFloat;
import minetweaker.expand.ExpandInt;
import minetweaker.expand.ExpandLong;
import minetweaker.expand.ExpandShort;
import minetweaker.expand.ExpandString;
import minetweaker.api.data.IData;
import minetweaker.api.item.Condition;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemCondition;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IItemTransformer;
import minetweaker.api.item.Transform;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDict;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.player.IPlayer;
import minetweaker.api.recipes.ICraftingInventory;
import minetweaker.api.recipes.IFurnaceManager;
import minetweaker.api.recipes.IRecipeFunction;
import minetweaker.api.recipes.IRecipeManager;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.compiler.ClassNameGenerator;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.TypeRegistry;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolJavaStaticField;
import stanhebben.zenscript.symbols.SymbolJavaStaticMethod;
import stanhebben.zenscript.symbols.SymbolPackage;
import stanhebben.zenscript.symbols.SymbolType;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeNative;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class GlobalRegistry {
	private static final Map<String, IZenSymbol> globals = new HashMap<String, IZenSymbol>();
	private static final List<IRecipeRemover> removers = new ArrayList<IRecipeRemover>();
	private static final List<IBracketHandler> bracketHandlers = new ArrayList<IBracketHandler>();
	private static final TypeRegistry types = new TypeRegistry();
	private static final SymbolPackage root = new SymbolPackage("<root>");
	private static final IZenErrorLogger errors = new MyErrorLogger();
	private static final IZenCompileEnvironment environment = new MyCompileEnvironment();
	private static final Map<String, TypeExpansion> expansions = new HashMap<String, TypeExpansion>();
	
	static {
		registerGlobal("print", getStaticFunction(GlobalFunctions.class, "print", String.class));
		registerGlobal("max", getStaticFunction(Math.class, "max", int.class, int.class));
		registerGlobal("min", getStaticFunction(Math.class, "min", int.class, int.class));
		
		registerGlobal("logger", getStaticField(MineTweakerAPI.class, "logger"));
		registerGlobal("minetweaker", getStaticField(MineTweakerAPI.class, "tweaker"));
		registerGlobal("recipes", getStaticField(MineTweakerAPI.class, "recipes"));
		registerGlobal("furnace", getStaticField(MineTweakerAPI.class, "furnace"));
		registerGlobal("oreDict", getStaticField(MineTweakerAPI.class, "oreDict"));
		
		registerExpansion(ExpandAnyArray.class);
		registerExpansion(ExpandAnyDict.class);
		registerExpansion(ExpandBool.class);
		registerExpansion(ExpandByte.class);
		registerExpansion(ExpandDouble.class);
		registerExpansion(ExpandFloat.class);
		registerExpansion(ExpandInt.class);
		registerExpansion(ExpandLong.class);
		registerExpansion(ExpandShort.class);
		registerExpansion(ExpandString.class);
		
		// minetweaker.api.data
		registerNativeClass(IData.class);
		
		// minetweaker.api.item
		registerNativeClass(IIngredient.class);
		registerNativeClass(IItemCondition.class);
		registerNativeClass(IItemDefinition.class);
		registerNativeClass(IItemStack.class);
		registerNativeClass(IItemTransformer.class);
		registerNativeClass(Condition.class);
		registerNativeClass(Transform.class);
		
		// minetweaker.api.liquid
		registerNativeClass(ILiquidDefinition.class);
		registerNativeClass(ILiquidStack.class);
		
		// minetweaker.api.oredict
		registerNativeClass(IOreDict.class);
		registerNativeClass(IOreDictEntry.class);
		
		// minetweaker.api.player
		registerNativeClass(IPlayer.class);
		
		// minetweaker.api.recipes
		registerNativeClass(ICraftingInventory.class);
		registerNativeClass(IFurnaceManager.class);
		registerNativeClass(IRecipeFunction.class);
		registerNativeClass(IRecipeManager.class);
	}
	
	private GlobalRegistry() {}
	
	public static void registerGlobal(String name, IZenSymbol symbol) {
		if (globals.containsKey(name)) {
			throw new IllegalArgumentException("symbol already exists: " + name);
		}
		
		globals.put(name, symbol);
	}
	
	public static void registerExpansion(Class cls) {
		for (Annotation annotation : cls.getAnnotationsByType(ZenExpansion.class)) {
			ZenExpansion eAnnotation = (ZenExpansion) annotation;
			if (!expansions.containsKey(eAnnotation.value())) {
				expansions.put(eAnnotation.value(), new TypeExpansion());
			}
			expansions.get(eAnnotation.value()).expand(cls, types);
		}
	}
	
	public static void registerRemover(IRecipeRemover remover) {
		removers.add(remover);
	}
	
	public static void registerBracketHandler(IBracketHandler handler) {
		bracketHandlers.add(handler);
	}
	
	public static void registerNativeClass(Class cls) {
		ZenTypeNative type = new ZenTypeNative(cls);
		type.complete(types);
		
		root.put(type.getName(), new SymbolType(type), errors);
	}
	
	public static TypeRegistry getTypeRegistry() {
		return types;
	}
	
	public static void remove(IIngredient ingredient) {
		for (IRecipeRemover remover : removers) {
			remover.remove(ingredient);
		}
	}
	
	public static IZenSymbol resolveBracket(List<Token> tokens) {
		for (IBracketHandler handler : bracketHandlers) {
			IZenSymbol symbol = handler.resolve(tokens);
			if (symbol != null) {
				return symbol;
			}
		}
		
		return null;
	}
	
	public static IZenSymbol getStaticFunction(Class cls, String name, Class... arguments) {
		JavaMethod method = JavaMethod.get(types, cls, name, arguments);
		if (method == null) return null;
		return new SymbolJavaStaticMethod(method);
	}
	
	public static IZenSymbol getStaticField(Class cls, String name) {
		try {
			Field field = cls.getDeclaredField(name);
			return new SymbolJavaStaticField(cls, field, types);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(GlobalRegistry.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} catch (SecurityException ex) {
			Logger.getLogger(GlobalRegistry.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public static IEnvironmentGlobal makeGlobalEnvironment(Map<String, byte[]> classes) {
		return new MyGlobalEnvironment(classes);
	}
	
	private static class MyErrorLogger implements IZenErrorLogger {
		@Override
		public void error(ZenPosition position, String message) {
			MineTweakerAPI.logger.logError(position.toString() + ": " + message);
		}

		@Override
		public void warning(ZenPosition position, String message) {
			MineTweakerAPI.logger.logWarning(position.toString() + ": " + message);
		}
	}
	
	private static class MyCompileEnvironment implements IZenCompileEnvironment {
		@Override
		public IZenErrorLogger getErrorLogger() {
			return errors;
		}

		@Override
		public IZenSymbol getGlobal(String name) {
			if (globals.containsKey(name)) {
				return globals.get(name);
			} else {
				return root.get(name);
			}
		}

		@Override
		public IZenSymbol getDollar(String name) {
			return null;
		}

		@Override
		public IZenSymbol getBracketed(List<Token> tokens) {
			return resolveBracket(tokens);
		}

		@Override
		public TypeRegistry getTypeRegistry() {
			return types;
		}

		@Override
		public TypeExpansion getExpansion(String type) {
			return expansions.get(type);
		}
	}
	
	private static class MyGlobalEnvironment implements IEnvironmentGlobal {
		private final Map<String, byte[]> classes;
		private final Map<String, IZenSymbol> symbols;
		private final ClassNameGenerator generator;
		
		public MyGlobalEnvironment(Map<String, byte[]> classes) {
			this.classes = classes;
			symbols = new HashMap<String, IZenSymbol>();
			generator = new ClassNameGenerator();
		}
		
		@Override
		public IZenCompileEnvironment getEnvironment() {
			return environment;
		}

		@Override
		public TypeExpansion getExpansion(String name) {
			return expansions.get(name);
		}

		@Override
		public String makeClassName() {
			return generator.generate();
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
			if (symbols.containsKey(name)) {
				return symbols.get(name).instance(position);
			} else if (globals.containsKey(name)) {
				return globals.get(name).instance(position);
			} else {
				IZenSymbol pkg = root.get(name);
				if (pkg == null) {
					return null;
				} else {
					return pkg.instance(position);
				}
			}
		}

		@Override
		public void putValue(String name, IZenSymbol value) {
			symbols.put(name, value);
		}

		@Override
		public ZenType getType(Type type) {
			return types.getType(type);
		}

		@Override
		public void error(ZenPosition position, String message) {
			MineTweakerAPI.logger.logError(position.toString() + " > " + message);
		}

		@Override
		public void warning(ZenPosition position, String message) {
			MineTweakerAPI.logger.logWarning(position.toString() + " > " + message);
		}
	}
}
