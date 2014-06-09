/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import minetweaker.minecraft.item.IIngredient;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.compiler.TypeRegistry;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolJavaStaticField;
import stanhebben.zenscript.symbols.SymbolJavaStaticMethod;
import stanhebben.zenscript.symbols.SymbolPackage;
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
	private static final SymbolPackage root = new SymbolPackage();
	private static final IZenErrorLogger errors = new MyErrorLogger();
	private static final IZenCompileEnvironment environment = new MyCompileEnvironment();
	private static final Map<String, TypeExpansion> expansions = new HashMap<String, TypeExpansion>();
	
	static {
		register("print", getStaticFunction(GlobalFunctions.class, "print", String.class));
		register("max", getStaticFunction(Math.class, "max", int.class, int.class));
		register("min", getStaticFunction(Math.class, "min", int.class, int.class));
		
		register("logger", getStaticField(MineTweakerAPI.class, "logger"));
		register("minetweaker", getStaticField(MineTweakerAPI.class, "tweaker"));
		register("recipes", getStaticField(MineTweakerAPI.class, "recipes"));
		register("furnace", getStaticField(MineTweakerAPI.class, "furnace"));
		register("oreDict", getStaticField(MineTweakerAPI.class, "oreDict"));
		
		/*registerExpansion(ExpandAnyArray.class);
		registerExpansion(ExpandAnyDict.class);
		registerExpansion(ExpandBool.class);
		registerExpansion(ExpandByte.class);
		registerExpansion(ExpandDouble.class);
		registerExpansion(ExpandFloat.class);
		registerExpansion(ExpandInt.class);
		registerExpansion(ExpandLong.class);
		registerExpansion(ExpandShort.class);
		registerExpansion(ExpandString.class);*/
		
		/*registerNativeClass(IData.class);
		
		registerNativeClass(IIngredient.class);
		registerNativeClass(IItemCondition.class);
		registerNativeClass(IItemDefinition.class);
		registerNativeClass(IItemStack.class);
		registerNativeClass(IItemTransformer.class);
		registerNativeClass(Condition.class);
		registerNativeClass(Transform.class);
		
		registerNativeClass(ILiquidDefinition.class);
		registerNativeClass(ILiquidStack.class);
		
		registerNativeClass(IOreDict.class);
		registerNativeClass(IOreDictEntry.class);
		
		registerNativeClass(ICraftingInventory.class);
		registerNativeClass(IFurnaceManager.class);
		registerNativeClass(IRecipeFunction.class);*/
	}
	
	private GlobalRegistry() {}
	
	public static void register(String name, IZenSymbol symbol) {
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
		
		root.put(type.getName(), root, errors);
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
}
