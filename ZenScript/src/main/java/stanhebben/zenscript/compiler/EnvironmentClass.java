/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.ClassWriter;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class EnvironmentClass implements IEnvironmentClass {
	private final ClassWriter output;
	private final IEnvironmentGlobal global;
	private final Map<String, IZenSymbol> local;

	public EnvironmentClass(ClassWriter output, IEnvironmentGlobal global) {
		this.output = output;
		this.global = global;
		this.local = new HashMap<String, IZenSymbol>();
	}

	@Override
	public ClassWriter getClassOutput() {
		return output;
	}

	@Override
	public ZenType getType(Type type) {
		return global.getType(type);
	}

	@Override
	public IZenCompileEnvironment getEnvironment() {
		return global.getEnvironment();
	}

	@Override
	public TypeExpansion getExpansion(String name) {
		return global.getExpansion(name);
	}

	@Override
	public String makeClassName() {
		return global.makeClassName();
	}

	@Override
	public boolean containsClass(String name) {
		return global.containsClass(name);
	}

	@Override
	public void putClass(String name, byte[] data) {
		global.putClass(name, data);
	}

	@Override
	public IPartialExpression getValue(String name, ZenPosition position) {
		if (local.containsKey(name)) {
			return local.get(name).instance(position);
		} else {
			return global.getValue(name, position);
		}
	}

	@Override
	public void putValue(String name, IZenSymbol value, ZenPosition position) {
		if (local.containsKey(name)) {
			error(position, "Value already defined in this scope: " + name);
		} else {
			local.put(name, value);
		}
	}

	@Override
	public void error(ZenPosition position, String message) {
		global.error(position, message);
	}

	@Override
	public void warning(ZenPosition position, String message) {
		global.warning(position, message);
	}

	@Override
	public Set<String> getClassNames() {
		return global.getClassNames();
	}

	@Override
	public byte[] getClass(String name) {
		return global.getClass(name);
	}
}
