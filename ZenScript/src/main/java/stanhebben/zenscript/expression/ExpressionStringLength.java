package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

public class ExpressionStringLength extends Expression {

	private final Expression source;
	
	public ExpressionStringLength(ZenPosition position, Expression source){
		super(position);
		
		this.source = source;
	}
	
	@Override
	public ZenType getType() {
		return ZenType.INT;
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		if(result){
			source.compile(result, environment);
			environment.getOutput().invokeVirtual(String.class, "length", int.class);
		}
	}

}
