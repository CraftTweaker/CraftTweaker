/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.symbols.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class EnvironmentGlobal implements IEnvironmentGlobal {
	private final IZenCompileEnvironment environment;
	private final IZenErrorLogger errors;
	private final Map<String, byte[]> classes;
	private final Map<String, IZenSymbol> local;
	private final ClassNameGenerator nameGen;
	private final TypeRegistry types;
	
	public EnvironmentGlobal(
			IZenCompileEnvironment environment,
			Map<String, byte[]> classes,
			ClassNameGenerator nameGen) {
		this.environment = environment;
		this.errors = environment.getErrorLogger();
		this.classes = classes;
		this.nameGen = nameGen;
		this.types = environment.getTypeRegistry();
		this.local = new HashMap<String, IZenSymbol>();
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
	public IZenCompileEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public IPartialExpression getValue(String name, ZenPosition position) {
		if (local.containsKey(name)) {
			return local.get(name).instance(position);
		} else {
			IZenSymbol symbol = environment.getGlobal(name);
			return symbol == null ? null : symbol.instance(position);
		}
	}

	@Override
	public void putValue(String name, IZenSymbol value) {
		if (local.containsKey(name)) {
			throw new RuntimeException("already exists");
		} else {
			local.put(name, value);
		}
	}
}
