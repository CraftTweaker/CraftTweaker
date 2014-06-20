/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.compiler;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.ClassVisitor;
import stanhebben.zenscript.TypeExpansion;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.IZenCompileEnvironment;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.MethodOutput;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class EnvironmentMethod implements IEnvironmentMethod {
	private final MethodOutput output;
	private final HashMap<SymbolLocal, Integer> locals;
	private final IEnvironmentClass environment;
	private final Map<String, IZenSymbol> local;
	
	public EnvironmentMethod(MethodOutput output, IEnvironmentClass environment) {
		this.output = output;
		this.locals = new HashMap<SymbolLocal, Integer>();
		this.environment = environment;
		this.local = new HashMap<String, IZenSymbol>();
	}
	
	@Override
	public MethodOutput getOutput() {
		return output;
	}
	
	@Override
	public ClassVisitor getClassOutput() {
		return environment.getClassOutput();
	}
	
	@Override
	public int getLocal(SymbolLocal variable) {
		if (!locals.containsKey(variable)) {
			locals.put(variable, output.local(variable.getType().toASMType()));
		}
		return locals.get(variable);
	}
	
	@Override
	public ZenType getType(Type type) {
		return environment.getType(type);
	}

	@Override
	public void error(ZenPosition position, String message) {
		environment.error(position, message);
	}

	@Override
	public void warning(ZenPosition position, String message) {
		environment.warning(position, message);
	}

	@Override
	public IZenCompileEnvironment getEnvironment() {
		return environment.getEnvironment();
	}

	@Override
	public TypeExpansion getExpansion(String name) {
		return environment.getExpansion(name);
	}
	
	@Override
	public String makeClassName() {
		return environment.makeClassName();
	}

	@Override
	public void putClass(String name, byte[] data) {
		environment.putClass(name, data);
	}

	@Override
	public boolean containsClass(String name) {
		return environment.containsClass(name);
	}

	@Override
	public IPartialExpression getValue(String name, ZenPosition position) {
		if (local.containsKey(name)) {
			return local.get(name).instance(position);
		} else {
			return environment.getValue(name, position);
		}
	}

	@Override
	public void putValue(String name, IZenSymbol value) {
		local.put(name, value);
	}
}
