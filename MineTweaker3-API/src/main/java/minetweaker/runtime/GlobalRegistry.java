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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import minetweaker.IBracketHandler;
import minetweaker.IRecipeRemover;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
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
import stanhebben.zenscript.type.natives.IJavaMethod;
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
	}

	private GlobalRegistry() {
	}

	public static void registerGlobal(String name, IZenSymbol symbol) {
		if (globals.containsKey(name)) {
			throw new IllegalArgumentException("symbol already exists: " + name);
		}

		globals.put(name, symbol);
	}

	public static void registerExpansion(Class<?> cls) {
		try {
			for (Annotation annotation : cls.getAnnotations()) {
				if (annotation instanceof ZenExpansion) {
					ZenExpansion eAnnotation = (ZenExpansion) annotation;
					if (!expansions.containsKey(eAnnotation.value())) {
						expansions.put(eAnnotation.value(), new TypeExpansion(eAnnotation.value()));
					}
					expansions.get(eAnnotation.value()).expand(cls, types);
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public static void registerRemover(IRecipeRemover remover) {
		removers.add(remover);
	}

	public static void registerBracketHandler(IBracketHandler handler) {
		bracketHandlers.add(handler);
	}

	public static void registerNativeClass(Class<?> cls) {
		try {
			ZenTypeNative type = new ZenTypeNative(cls);
			type.complete(types);

			root.put(type.getName(), new SymbolType(type), errors);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public static TypeRegistry getTypeRegistry() {
		return types;
	}

	public static void remove(IIngredient ingredient) {
		for (IRecipeRemover remover : removers) {
			remover.remove(ingredient);
		}
	}

	public static IZenSymbol resolveBracket(IEnvironmentGlobal environment, List<Token> tokens) {
		for (IBracketHandler handler : bracketHandlers) {
			IZenSymbol symbol = handler.resolve(environment, tokens);
			if (symbol != null) {
				return symbol;
			}
		}

		return null;
	}

	public static IZenSymbol getStaticFunction(Class cls, String name, Class... arguments) {
		IJavaMethod method = JavaMethod.get(types, cls, name, arguments);
		if (method == null)
			return null;
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
			if (position == null) {
				MineTweakerAPI.logError("system: " + message);
			} else {
				MineTweakerAPI.logError(position + ": " + message);
			}
		}

		@Override
		public void warning(ZenPosition position, String message) {
			if (position == null) {
				MineTweakerAPI.logWarning("system: " + message);
			} else {
				MineTweakerAPI.logWarning(position + ": " + message);
			}
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
		public IZenSymbol getBracketed(IEnvironmentGlobal environment, List<Token> tokens) {
			return resolveBracket(environment, tokens);
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
		public void putValue(String name, IZenSymbol value, ZenPosition position) {
			if (symbols.containsKey(name)) {
				error(position, "Value already defined in this scope: " + name);
			} else {
				symbols.put(name, value);
			}
		}

		@Override
		public ZenType getType(Type type) {
			return types.getType(type);
		}

		@Override
		public void error(ZenPosition position, String message) {
			MineTweakerAPI.logError(position.toString() + " > " + message);
		}

		@Override
		public void warning(ZenPosition position, String message) {
			MineTweakerAPI.logWarning(position.toString() + " > " + message);
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
}
