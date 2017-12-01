package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.symbols.SymbolGlobalValue;
import stanhebben.zenscript.type.ZenType;

public class ExpressionGlobalGet extends Expression {

	private final SymbolGlobalValue value;

	public ExpressionGlobalGet(SymbolGlobalValue value, IEnvironmentGlobal environment) {
		super(value.getPosition());
		this.value = value;
		
	}

	@Override
	public ZenType getType() {
		return value.getType();
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		environment.getOutput().getStaticField(getOwner(), getName(), getDescriptor());

	}
	
	public String getOwner() {
		return value.getOwner();
	}
	
	public String getName() {
		return value.getName();
	}
	
	public String getDescriptor() {
		return getType().toASMType().getDescriptor();
	}

}
